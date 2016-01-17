package Com.gun.Contetnt;



import Com.gun.Entity.EntityPistolMag;
import Com.gun.Model.glock17Model;
import Com.gun.gun.EnumGunType;
import Com.gun.gun.ItemPistol;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class Itemg17   extends ItemPistol
{
	
	public void onGunFired(ItemStack itemstack, World world, EntityPlayer player)
	  {
	    if (!world.isRemote)
	    {
	      EntityPistolMag theBullet = new EntityPistolMag(world, player);
	      world.spawnEntityInWorld(theBullet);
	    }
	  }
	  
	  public String getRendererTexture()
	  {
	    return "dastomine:textures/model/glock22.png";
	  }
	  
	  public String getFireSound()
	  {
	    return "dastomine:gun_g17_fire";
	  }
	  
	  @SideOnly(Side.CLIENT)
	  public ModelBase getRendererModel()
	  {
	    return new glock17Model();
	  }
	  
	  public String getSilencedSound()
	  {
	    return "battlefield:gun_g17_silent";
	  }
	  
	  public EnumGunType getGunType()
	  {
	    return EnumGunType.PISTOL;
	  }
	}
