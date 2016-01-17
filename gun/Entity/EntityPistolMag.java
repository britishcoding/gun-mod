package Com.gun.Entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;




public class EntityPistolMag extends EntityBullet
{
	  public EntityPistolMag(World par1World)
	  {
	    super(par1World);
	  }
	  
	  public EntityPistolMag(World par1World, EntityLivingBase elb)
	  {
	    super(par1World, elb);
	  }
	  
	  public float getDamage()
	  {
	    return 6.0F;
	  }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO Auto-generated method stub
		
	}
	  
	

		
	}
	
