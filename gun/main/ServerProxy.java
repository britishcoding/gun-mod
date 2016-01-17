package Com.gun.main;




import Com.gun.Contetnt.GunList;
import Com.gun.gun.ItemGun;
import Com.gun.gun.ItemPistol;
import Com.gun.gun.ItemSniperRifle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ServerProxy {


	  public void registerClient(GunList content)
	  {}


  public void fireGun(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, ItemGun itemGun) {

		
	}

  public void firePistol(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, ItemPistol gun) {}
  
  public void fireSniper(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, ItemSniperRifle gun) {}
  
  public void reloadGun() {}
  
  public void registerClientPre() {}

  
  public ItemStack getMagazine(EntityPlayer player, Item magazine)
  {
    for (ItemStack is : player.inventory.mainInventory) {
      if ((is != null) && (is.getItem() == magazine)) {
        return is;
      }
    }
    return null;
  }



	
}



	
