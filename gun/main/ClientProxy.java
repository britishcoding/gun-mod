package Com.gun.main;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import Com.gun.Contetnt.GunList;
import Com.gun.Entity.EntityBullet;
import Com.gun.Packet.GunFirePacket;
import Com.gun.Packet.ReloadGunPacket;
import Com.gun.Packet.SniperShootPacket;
import Com.gun.gun.ItemGun;
import Com.gun.gun.ItemMagazine;
import Com.gun.gun.ItemSniperRifle;
import Com.gun.render.Renderg17;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy   extends ServerProxy
{
	  public static KeyHandler keyHandler;
	  public static ClientTickHandler tickHandler;

	    

	  public void registerClient(GunList content)
	  
	    {
	    
	
	    	MinecraftForgeClient.registerItemRenderer(GunList.g17, new Renderg17());
	      RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderArrow());
	    }
	    
	    public void fireGun(ItemStack par1ItemStack, World par2World, Entity par3Entity, ItemGun gun)
	    {
	      if (((par3Entity instanceof EntityPlayer)) && (((EntityPlayer)par3Entity).getCommandSenderName().equals(Minecraft.getMinecraft().thePlayer.getCommandSenderName())))
	      {
	        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
	        if ((player.getItemInUse() != null) && (player.getItemInUse().getItem() == gun))
	        {
	          if ((!gun.hasAmmo(par1ItemStack)) && (!player.capabilities.isCreativeMode)) {
	            return;
	          }
	          IMessage packet = new GunFirePacket(player.getCommandSenderName(), gun, player.inventory.currentItem);
	          if ((gun instanceof ItemSniperRifle)) {
	            packet = new SniperShootPacket(player.getCommandSenderName(), (ItemSniperRifle)gun, player.inventory.currentItem, tickHandler.scopeTime, (float)player.posX, (float)player.posY, (float)player.posZ);
	          }
	          MainRegistry.networkHandler.sendToServer(packet);
	          gun.performRecoilAnimation(par1ItemStack, player);
	          
	          NBTTagCompound tag = par1ItemStack.stackTagCompound != null ? par1ItemStack.stackTagCompound : new NBTTagCompound();
	          
	          gun.consumeAmmo(player, tag);
	          
	          tag.setInteger("fireCooldown", gun.getFireRate());
	          par1ItemStack.stackTagCompound = tag;
	        }
	      }
	    }
	    
	
	    
	    public void reloadGun()
	    {
	      EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
	      ItemStack stack = player.getHeldItem();
	      if ((stack != null) && ((stack.getItem() instanceof ItemGun)))
	      {
	        ItemGun gun = (ItemGun)stack.getItem();
	        NBTTagCompound nbt = stack.stackTagCompound != null ? stack.stackTagCompound : new NBTTagCompound();
	        if (player.inventory.consumeInventoryItem(gun.getMagazineItem()))
	        {
	          ItemMagazine magazine = (ItemMagazine)gun.getMagazineItem();
	          if (nbt.getInteger("remainingAmmo") >= magazine.getMaxDamage() / 2) {
	            return;
	          }
	          nbt.setInteger("remainingAmmo", magazine.getMaxDamage());
	          if (!player.capabilities.isCreativeMode) {
	            player.inventory.consumeInventoryItem(gun.getMagazineItem());
	          }
	        }
	        stack.stackTagCompound = nbt;
	        MainRegistry.networkHandler.sendToServer(new ReloadGunPacket(player.getCommandSenderName(), player.inventory.currentItem, gun));
	      }
	    }
	    
	    public void registerClientPre()
	    {
	      FMLCommonHandler.instance().bus().register(keyHandler = new KeyHandler());
	      MinecraftForge.EVENT_BUS.register(tickHandler = new ClientTickHandler());
	      
	      keyHandler.registerKeys();
	    }
	    

	
	  }