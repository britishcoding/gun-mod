package Com.gun.main; 

import Com.gun.Contetnt.GunList;
import Com.gun.Packet.GunFirePacket;
import Com.gun.Packet.ReloadGunPacket;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;






@Mod(modid = RefStrings.MODID , name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry {
	@Mod.Instance(RefStrings.MODID)
	public static MainRegistry instance;;
	  public static SimpleNetworkWrapper networkHandler;
	public GunList content = new GunList();

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE , serverSide = RefStrings.SERVERSIDE)
public static ServerProxy proxy;
	

@EventHandler
public  void load(FMLInitializationEvent event){
    proxy.registerClientPre();


	GunList.mainRegistry();

    networkHandler = NetworkRegistry.INSTANCE.newSimpleChannel("gunmod");
    networkHandler.registerMessage(GunFirePacket.Handler.class, GunFirePacket.class, 2, Side.SERVER);

    networkHandler.registerMessage(ReloadGunPacket.Handler.class, ReloadGunPacket.class, 4, Side.SERVER);
	FMLCommonHandler.instance().bus().register(new KeyHandler());


}

	    
	  @Mod.EventHandler
	  public void serverStart(FMLServerStartingEvent event){
	  
	  }

public static GunList content()
{
  return instance.content;
}
}

