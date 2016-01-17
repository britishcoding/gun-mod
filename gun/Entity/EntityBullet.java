package Com.gun.Entity;



import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityBullet
  extends EntityThrowable
{
  public boolean collided = false;
  public int age = 0;
  public int maxAge = 1000;
  public static final double speed = 2.0D;
  private boolean isSniper = false;
  
  public EntityBullet(World par1World)
  {
    super(par1World);
    
    this.motionX *= 2.0D;
    this.motionY *= 2.0D;
    this.motionZ *= 2.0D;
  }
  
  public EntityBullet setSniper()
  {
    this.isSniper = true;
    
    return this;
  }
  
  public boolean isSniper()
  {
    return this.isSniper;
  }
  
  public EntityBullet(World par1World, EntityLivingBase par2EntityLivingBase)
  {
    super(par1World, par2EntityLivingBase);
    
    this.motionX *= 2.0D;
    this.motionY *= 2.0D;
    this.motionZ *= 2.0D;
  }
  
  protected float getGravityVelocity()
  {
    return 1.0E-5F;
  }
  
  public abstract float getDamage();
  
  public void onUpdate()
  {
    super.onUpdate();
    
    this.age += 1;
    if (this.age >= this.maxAge) {
      setDead();
    }
  }
  
  public void writeToNBT(NBTTagCompound nbt)
  {
    super.writeToNBT(nbt);
    
    nbt.setBoolean("hasCollided", this.collided);
    nbt.setBoolean("isSniper", this.isSniper);
  }
  
  public void readFromNBT(NBTTagCompound nbt)
  {
    super.readFromNBT(nbt);
    
    this.collided = nbt.getBoolean("hasCollided");
    this.isSniper = nbt.getBoolean("isSniper");
  }
  
  public boolean shouldUseParticles()
  {
    return true;
  }
  

  
  private void spawnParticle(String particle, double mx, double my, double mz)
  {
    if (!shouldUseParticles()) {
      return;
    }
    this.worldObj.spawnParticle(particle, this.posX, this.posY, this.posZ, mx, my, mz);
  }
}
