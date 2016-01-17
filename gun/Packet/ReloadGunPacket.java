package Com.gun.Packet;


import Com.gun.gun.ItemGun;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ReloadGunPacket
  implements IMessage
{
  private String username;
  private int slot;
  private ItemGun gun;
  
  public ReloadGunPacket() {}
  
  public ReloadGunPacket(String username, int slot, ItemGun gun)
  {
    this.username = username;
    this.slot = slot;
    this.gun = gun;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public int getSlot()
  {
    return this.slot;
  }
  
  public ItemGun getGun()
  {
    return this.gun;
  }
  
  public void fromBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = ByteBufUtils.readTag(buf);
    
    this.username = nbt.getString("username");
    this.slot = nbt.getInteger("slot");
    this.gun = ((ItemGun)Item.getItemById(nbt.getInteger("gun")));
  }
  
  public void toBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = new NBTTagCompound();
    
    nbt.setString("username", getUsername());
    nbt.setInteger("slot", getSlot());
    nbt.setInteger("gun", Item.getIdFromItem(getGun()));
    
    ByteBufUtils.writeTag(buf, nbt);
  }
  
  public static class Handler
    implements IMessageHandler<ReloadGunPacket, IMessage>
  {
    public IMessage onMessage(ReloadGunPacket message, MessageContext ctx)
    {
      EntityPlayer player = PacketUtil.getPlayer(message.getUsername());
      ItemStack stack = player.inventory.getStackInSlot(message.getSlot());
      if ((stack != null) && (stack.getItem() == message.getGun()))
      {
        ItemGun
        gun = 
        		(ItemGun)
        		stack
        		.getAttributeModifiers();
        Item magazine = gun.getMagazineItem();
        NBTTagCompound nbt = stack.stackTagCompound != null ? stack.stackTagCompound : new NBTTagCompound();
        if (player.inventory.consumeInventoryItem(gun.getMagazineItem()))
        {
          nbt.setInteger("remainingAmmo", magazine.getMaxDamage());
          if (!player.capabilities.isCreativeMode) {
            player.inventory.consumeInventoryItem(gun.getMagazineItem());
          }
        }
        stack.stackTagCompound = nbt;
      }
      return null;
    }
  }
}
