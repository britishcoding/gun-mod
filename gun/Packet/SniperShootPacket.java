package Com.gun.Packet;


import Com.gun.gun.ItemSniperRifle;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SniperShootPacket
  implements IMessage
{
  private String username;
  private ItemSniperRifle gun;
  private int gunIndex;
  private int scopeTime;
  private float x;
  private float y;
  private float z;
  
  public SniperShootPacket() {}
  
  public SniperShootPacket(String username, ItemSniperRifle gun, int gunIndex, int scopeTime, float x, float y, float z)
  {
    this.username = username;
    this.gun = gun;
    this.gunIndex = gunIndex;
    this.scopeTime = scopeTime;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public ItemSniperRifle getGun()
  {
    return this.gun;
  }
  
  public int getGunIndex()
  {
    return this.gunIndex;
  }
  
  public int getScopeTime()
  {
    return this.scopeTime;
  }
  
  public float getX()
  {
    return this.x;
  }
  
  public float getY()
  {
    return this.y;
  }
  
  public float getZ()
  {
    return this.z;
  }
  
  public void fromBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = ByteBufUtils.readTag(buf);
    
    this.username = nbt.getString("username");
    this.gun = ((ItemSniperRifle)Item.getItemById(nbt.getInteger("gun")));
    this.gunIndex = nbt.getInteger("slot");
    this.scopeTime = nbt.getInteger("scopeTime");
    this.x = nbt.getFloat("x");
    this.y = nbt.getFloat("y");
    this.z = nbt.getFloat("z");
  }
  
  public void toBytes(ByteBuf buf)
  {
    NBTTagCompound nbt = new NBTTagCompound();
    
    nbt.setString("username", getUsername());
    nbt.setInteger("gun", Item.getIdFromItem(getGun()));
    nbt.setInteger("slot", getGunIndex());
    nbt.setInteger("scopeTime", getScopeTime());
    nbt.setFloat("x", getX());
    nbt.setFloat("y", getY());
    nbt.setFloat("z", getZ());
    
    ByteBufUtils.writeTag(buf, nbt);
  }
  
  public static class Handler
    implements IMessageHandler<SniperShootPacket, IMessage>
  {
    public IMessage onMessage(SniperShootPacket message, MessageContext ctx)
    {
      EntityPlayer player = PacketUtil.getPlayer(message.getUsername());
      message.getGun().fireGun(player, player.inventory.mainInventory[message.getGunIndex()], player.worldObj);
      player.worldObj.spawnEntityInWorld(message.getGun().getSniperBullet(message, player));
      
      return null;
    }
  }
}
