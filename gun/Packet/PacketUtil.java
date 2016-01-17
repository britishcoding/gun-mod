package Com.gun.Packet;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class PacketUtil
{
  public static EntityPlayer getPlayer(String username)
  {
    for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
      if ((obj instanceof EntityPlayer))
      {
        EntityPlayer player = (EntityPlayer)obj;
        if (player.getCommandSenderName().equals(username)) {
          return player;
        }
      }
    }
    return null;
  }
}
