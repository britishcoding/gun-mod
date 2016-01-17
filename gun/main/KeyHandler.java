package Com.gun.main;




import Com.gun.gun.ItemGun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(value = Side.CLIENT)
public class KeyHandler
{


	public static KeyBinding zoom = new KeyBinding("battlefield.key.zoom", 44, "keys.catagory.battlefield");
	  public static KeyBinding attach = new KeyBinding("battlefield.key.attach", 13, "keys.catagory.battlefield");
	  public static KeyBinding reload = new KeyBinding("battlefield.key.reload", 19, "keys.catagory.battlefield");
    
	  @SubscribeEvent
	  @SideOnly(Side.CLIENT)
	  public void keyPressed(InputEvent.KeyInputEvent event)
	  {
	    if (Minecraft.getMinecraft().currentScreen == null) {
	      if (zoom.isPressed())
	      {
	        if ((Minecraft.getMinecraft().theWorld != null) && (Minecraft.getMinecraft().thePlayer != null))
	        {
	          EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
	          if ((player.getHeldItem() != null) && (player.getHeldItem().getItem() != null) && ((player.getHeldItem().getItem() instanceof ItemGun)))
	          {
	            ItemGun item = (ItemGun)player.getHeldItem().getItem();
	            if (item.canUseScope(player.getHeldItem())) {
	              ClientProxy.tickHandler.isZoomed = (!ClientProxy.tickHandler.isZoomed);
	            }
	          }
	        }
	      }
	      else if (attach.isPressed())
	      {
	        if ((Minecraft.getMinecraft().thePlayer == null) || (Minecraft.getMinecraft().theWorld == null)) {
	          return;
	        }
	        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
	        if ((player.getHeldItem() != null) && ((player.getHeldItem().getItem() instanceof ItemGun))) {
	          
	        } else {
	        
	        }
	      }
	      else if (reload.isPressed())
	      {
	        MainRegistry.proxy.reloadGun();
	      }
	    }
	  }
	  
	  public static void registerKeys()
	  {
	    ClientRegistry.registerKeyBinding(zoom);
	    ClientRegistry.registerKeyBinding(attach);
	    ClientRegistry.registerKeyBinding(reload);
	  }
	}

	