package Com.gun.gun;



import Com.gun.main.MainRegistry;
import net.minecraft.item.Item;

public abstract class ItemPistol
extends ItemGun
{
public Item getMagazineItem()
{
  return MainRegistry.content().pistolMagazine;
}

public EnumGunType getGunType()
{
  return EnumGunType.PISTOL;
}

public int getFireRate()
{
  return 9;
}
}
