package Com.gun.gun;

import Com.gun.main.MainRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;




import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class ItemGun extends Item
{
	  public ItemGun()
	  {
	    this.setMaxStackSize(1);
	    
	    MainRegistry.content().addGun(this);
	  }
	  


	


	public abstract void onGunFired(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer);
	  
	  public abstract String getRendererTexture();
	  
	  public abstract String getFireSound();
	  
	  public abstract String getSilencedSound();
	  
	  public abstract Item getMagazineItem();
	  
	  public abstract EnumGunType getGunType();
	  
	  public abstract int getFireRate();
	  
	  public String getGunTypeDescription()
	  {
	    return StatCollector.translateToLocal("gun.type." + getGunType().name().toLowerCase());
	  }
	  
	  public void addAttachment(ItemStack stack, ItemAttachment attachment)
	  {
	    NBTTagCompound nbt = stack.stackTagCompound != null ? stack.stackTagCompound : new NBTTagCompound();
	    
	    ItemAttachment[] attachments = getAttachments(stack);
	    ItemAttachment[] newAttachments = new ItemAttachment[attachments.length + 1];
	    for (ItemAttachment att : attachments) {
	      if (att.getName().equals(attachment)) {
	        return;
	      }
	    }
	    for (int i = 0; i < attachments.length; i++) {
	      newAttachments[i] = attachments[i];
	    }
	    if (attachment.canBeUsedWithGun(this, attachments)) {
	      newAttachments[(newAttachments.length - 1)] = attachment;
	    }
	    NBTTagList list = new NBTTagList();
	    for (ItemAttachment attach : newAttachments)
	    {
	      NBTTagString tag = new NBTTagString(attach.getName());
	      list.appendTag(tag);
	    }
	    nbt.setTag("Attachments", list);
	    stack.stackTagCompound = nbt;
	  }
	  
	  public void removeAttachment(String name, ItemStack stack)
	  {
	    NBTTagCompound nbt = stack.stackTagCompound;
	    if (nbt == null) {
	      return;
	    }
	    ArrayList<ItemAttachment> arr = new ArrayList();
	    for (ItemAttachment attach : getAttachments(stack)) {
	      if (!attach.getName().equals(name)) {
	        arr.add(attach);
	      }
	    }
	    NBTTagList list = new NBTTagList();
	    for (ItemAttachment attach : (ItemAttachment[])arr.toArray(new ItemAttachment[0])) {
	      if (attach != null)
	      {
	        NBTTagString tag = new NBTTagString(attach.getName());
	        list.appendTag(tag);
	      }
	    }
	    nbt.setTag("Attachments", list);
	    stack.stackTagCompound = nbt;
	  }
	  
	  public void fireRaycastBullet(EntityLivingBase living, float spread)
	  {
	    float f = spread;
	    double motionX = -MathHelper.sin(living.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1415927F) * f;
	    double motionZ = MathHelper.cos(living.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(living.rotationPitch / 180.0F * 3.1415927F) * f;
	    double motionY = -MathHelper.sin(living.rotationPitch / 180.0F * 3.1415927F) * f;
	    

	      }
	 
	  
	  
	  public boolean hasAttachment(ItemStack stack, String name)
	  {
	    for (ItemAttachment attach : getAttachments(stack)) {
	      if (attach.getName().equals(name)) {
	        return true;
	      }
	    }
	    return false;
	  }
	  
	  public ItemAttachment[] getAttachments(ItemStack stack)
	  {
	    ArrayList<ItemAttachment> arr = new ArrayList();
	    if ((stack != null) && (stack.stackTagCompound != null))
	    {
	      NBTTagList list = stack.stackTagCompound.getTagList("Attachments", 8);
	      for (int i = 0; i < list.tagCount(); i++)
	      {
	        String att = list.getStringTagAt(i);
	        if (att != null)
	        {
	          ItemAttachment attach = MainRegistry.content().getAttachmentFromName(att);
	          if (attach != null) {
	            arr.add(attach);
	          }
	        }
	      }
	    }
	    return (ItemAttachment[])arr.toArray(new ItemAttachment[0]);
	  }
	  
	  public boolean hasAmmo(ItemStack stack)
	  {
	    NBTTagCompound nbt = stack.stackTagCompound != null ? stack.stackTagCompound : new NBTTagCompound();
	    if (!nbt.hasKey("remainingAmmo"))
	    {
	      nbt.setInteger("remainingAmmo", 0);
	      stack.stackTagCompound = nbt;
	    }
	    return stack.stackTagCompound.getInteger("remainingAmmo") > 0;
	  }
	  
	 
	  
	  @SideOnly(Side.CLIENT)
	  public abstract ModelBase getRendererModel();
	  
	  public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	  {
	    if ((!par3EntityPlayer.capabilities.isCreativeMode) && (par1ItemStack.stackTagCompound != null) && (par1ItemStack.stackTagCompound.getInteger("remainingAmmo") <= 0) && (!par3EntityPlayer.inventory.consumeInventoryItem(getMagazineItem()))) {
	      return par1ItemStack;
	    }
	    par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
	    
	    return par1ItemStack;
	  }
	  
	  public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	  {
	    if (par1ItemStack.stackTagCompound != null)
	    {
	      NBTTagCompound nbt = par1ItemStack.stackTagCompound;
	      if (nbt.getInteger("fireCooldown") > 0)
	      {
	        nbt.setInteger("fireCooldown", nbt.getInteger("fireCooldown") - 1);
	        par1ItemStack.stackTagCompound = nbt;
	        
	        return;
	      }
	    }
	    MainRegistry.proxy.fireGun(par1ItemStack, par2World, par3Entity, this);
	  }
	  
	  public void fireGun(EntityPlayer player, ItemStack stack, World world)
	  {
	    if ((!world.isRemote) && (stack != null))
	    {
	      boolean silencerAttached = hasAttachment(stack, MainRegistry.content().silencer.getName());
	      String sound = silencerAttached ? getSilencedSound() : getFireSound();
	      if (sound != null) {
	        world.playSoundAtEntity(player, sound, silencerAttached ? 0.5F : 1.0F, 1.0F);
	      }
	      onGunFired(stack, world, player);
	      performRecoilAnimation(stack, player);
	      
	      NBTTagCompound nbt = stack.stackTagCompound != null ? stack.stackTagCompound : new NBTTagCompound();
	      nbt.setInteger("fireCooldown", getFireRate());
	      
	      consumeAmmo(player, nbt);
	      
	      stack.stackTagCompound = nbt;
	    }
	  }
	  
	  public void consumeAmmo(EntityPlayer player, NBTTagCompound nbt)
	  {
	    if (!player.capabilities.isCreativeMode) {
	      nbt.setInteger("remainingAmmo", nbt.getInteger("remainingAmmo") - 1);
	    }
	    if (nbt.getInteger("remainingAmmo") < 0) {
	      nbt.setInteger("remainingAmmo", 0);
	    }
	  }
	  
	  public void performRecoilAnimation(ItemStack stack, EntityPlayer player)
	  {
	    if ((stack != null) && (stack.getItem() == MainRegistry.content().crossbow)) {
	      return;
	    }
	    float power = getRecoilPower();
	    if (player.rotationPitch > -90.0F) {
	      player.rotationPitch -= power;
	    }
	  }
	  
	  public boolean canUseScope(ItemStack stack)
	  {
	    return false;
	  }
	  
	  public boolean canContinueFiring(EntityPlayer player)
	  {
	    if ((player.capabilities.isCreativeMode) || (hasAmmo(player.getHeldItem()))) {
	      return true;
	    }
	    return false;
	  }
	  
	  public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	  {
	    return is;
	  }
	  
	  public int getMaxItemUseDuration(ItemStack is)
	  {
	    return 7200000;
	  }
	  
	  @SideOnly(Side.CLIENT)
	  public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	  {
	    par3List.add(StatCollector.translateToLocalFormatted("gun.info.type", new Object[] { getGunTypeDescription() }));
	    if (getMagazineItem() != null) {
	      par3List.add(StatCollector.translateToLocalFormatted("gun.info.ammo", new Object[] { StatCollector.translateToLocal(getMagazineItem().getUnlocalizedName() + ".name") }));
	    }
	    ItemAttachment[] attachments = getAttachments(par1ItemStack);
	    if (attachments.length > 0)
	    {
	      par3List.add(StatCollector.translateToLocal("item.daystomine.gun.attachlist"));
	      for (ItemAttachment attach : attachments) {
	        par3List.add(StatCollector.translateToLocal(attach.getUnlocalizedName() + ".name"));
	      }
	    }
	  }
	  

	  
	  public float getRecoilPower()
	  {
	    return 0.7F;
	  }











		
	}
	
