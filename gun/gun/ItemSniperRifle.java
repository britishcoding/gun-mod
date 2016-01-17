package Com.gun.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import Com.gun.Contetnt.GunList;
import Com.gun.Entity.EntityBullet;
import Com.gun.Entity.EntitySniperBullet;
import Com.gun.Packet.SniperShootPacket;
import Com.gun.main.ClientProxy;
import Com.gun.main.MainRegistry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSniperRifle
extends ItemPistol
{
public void onGunFired(ItemStack itemstack, World world, EntityPlayer player) {}

public EntityBullet getSniperBullet(SniperShootPacket packet, EntityPlayer player)
{
  EntitySniperBullet bullet = new EntitySniperBullet();


}

public boolean canUseScope(ItemStack stack)
{
  return true;
}

public Item getMagazineItem()
{
  return MainRegistry.content().rifleMagazine;
}

public String getFireSound()
{
  return "battlefield:gun_m22_fire";
}

public String getRendererTexture()
{
  if (this == MainRegistry.content().enfieldscope) {
    return "battlefield:textures/model/model_enfieldscope.png";
  }
  if (this == MainRegistry.content().m110) {
    return "battlefield:textures/model/model_m110.png";
  }
  return null;
}

@SideOnly(Side.CLIENT)
public ModelBase getRendererModel()
{
  if (this == MainRegistry.content().enfieldscope) {
    return new ModelBiped();
  }
  if (this == MainRegistry.content().m110) {
    return new ModelBiped();
  }
  return null;
}

@SideOnly(Side.CLIENT)
public static boolean isSniperScoped()
{
  return ClientProxy.tickHandler.isZoomed;
}

public String getSilencedSound()
{
  return "battlefield:gun_pyphon_silent";
}

public int getFireRate()
{
  return 11;
}

public EnumGunType getGunType()
{
  return EnumGunType.SNIPER;
}


}
