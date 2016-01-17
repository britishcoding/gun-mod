package Com.gun.Contetnt;

import java.util.ArrayList;

import Com.gun.gun.EnumGunType;
import Com.gun.gun.ItemAttachment;
import Com.gun.gun.ItemGun;
import Com.gun.gun.ItemMagazine;


import Com.gun.gun.ItemSniperRifle;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GunList 
{
	  public static void mainRegistry()
	  {
	    initializeitem();
	    registeritem();
	  }
	  
	  private ArrayList<ItemGun> guns = new ArrayList();
	  private ArrayList<ItemAttachment> attachments = new ArrayList();

	  public Item crossbow;
	  public ItemAttachment silencer;
	  public ItemAttachment ironSights;
	public ItemSniperRifle enfieldscope;
	public ItemSniperRifle m110;
	public Item rifleMagazine;
	public Object explosiveRounds;
	public static Item pistolMagazine;
      public static Item g17;









	private static void initializeitem() {
		 g17 = new Itemg17().setCreativeTab(CreativeTabs.tabCombat).setTextureName("daystomine:gun_glock17").setUnlocalizedName("g17");
		 pistolMagazine = new ItemMagazine(10).setCreativeTab(CreativeTabs.tabCombat).setTextureName("daystomine:pistolammo").setUnlocalizedName("pistolMagazine");
	}










	private static void registeritem() {
		GameRegistry.registerItem(pistolMagazine, pistolMagazine.getUnlocalizedName());
		GameRegistry.registerItem(g17, g17.getUnlocalizedName());
	}

































	public ItemGun[] getAllGunsExcept(ItemGun... blacklist)
	  {
	    ArrayList<ItemGun> arr = new ArrayList();
	    for (ItemGun gun : this.guns) {
	      for (ItemGun g : blacklist) {
	        if (gun != g) {
	          arr.add(gun);
	        }
	      }
	    }
	    return (ItemGun[])arr.toArray(new ItemGun[0]);
	  }
	  
	  public ItemGun[] getAllGunsExcept(EnumGunType... types)
	  {
	    ArrayList<ItemGun> arr = new ArrayList();
	    for (ItemGun gun : this.guns)
	    {
	      boolean skip = false;
	      for (EnumGunType type : types) {
	        if (gun.getGunType() == type) {
	          skip = true;
	        }
	      }
	      if (!skip) {
	        arr.add(gun);
	      }
	    }
	    return (ItemGun[])arr.toArray(new ItemGun[0]);
	  }
	  
	  public ItemGun[] getAllGuns()
	  {
	    return (ItemGun[])this.guns.toArray(new ItemGun[0]);
	  }
	  
	  public ItemAttachment[] getAllAttachments()
	  {
	    return (ItemAttachment[])this.attachments.toArray(new ItemAttachment[0]);
	  }
	  
	  public ItemAttachment getAttachmentFromName(String name)
	  {
	    for (ItemAttachment attachment : getAllAttachments()) {
	      if ((attachment != null) && (attachment.getName().equals(name))) {
	        return attachment;
	      }
	    }
	    return null;
	  }
	  
	  public void addGun(ItemGun gun)
	  {
	    if (!this.guns.contains(gun)) {
	      this.guns.add(gun);
	    }
	  }
	  
	  public void addAttachments(ItemAttachment attachment)
	  {
	    if (!this.attachments.contains(attachment)) {
	      this.attachments.add(attachment);
	    }
	  }
	}

