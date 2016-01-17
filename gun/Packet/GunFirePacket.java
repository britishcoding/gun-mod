package Com.gun.Packet;


import Com.gun.gun.ItemGun;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public class GunFirePacket
  implements IMessage
{
  private String username;
  private ItemGun gun;
  private int gunIndex;
  
  public GunFirePacket() {}
  
  public GunFirePacket(String username, ItemGun gun, int gunIndex)
  {
    this.username = username;
    this.gun = gun;
    this.gunIndex = gunIndex;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public ItemGun getGun()
  {
    return this.gun;
  }
  
  public int getGunIndex()
  {
    return this.gunIndex;
  }
  
  public void fromBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = ByteBufUtils.readTag(buf);
    
    this.username = nbt.getString("username");
    this.gun = ((ItemGun)Item.getItemById(nbt.getInteger("gun")));
    this.gunIndex = nbt.getInteger("slot");
  }
  
  public void toBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = new NBTTagCompound();
    
    nbt.setString("username", getUsername());
    nbt.setInteger("gun", Item.getIdFromItem(getGun()));
    nbt.setInteger("slot", getGunIndex());
    
    ByteBufUtils.writeTag(buf, nbt);
  }
  
  public static class Handler
    implements IMessageHandler<GunFirePacket, IMessage>
  {
    public IMessage onMessage(GunFirePacket message, MessageContext ctx)
    {
      EntityPlayer player = PacketUtil.getPlayer(message.getUsername());

      
      return null;
    }
  }
}
