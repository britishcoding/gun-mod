package Com.gun.gun;

import java.io.Serializable;




import Com.gun.main.MainRegistry;
import net.minecraft.item.Item;

public abstract class  ItemAttachment
extends Item
implements Serializable
{
private String name;

public ItemAttachment(String name)
{
  this.name = name;
  
  setMaxStackSize(1);
  setTextureName("daystomine:attachment_" + name);
  setUnlocalizedName("daystomine.attach." + name);
  
  MainRegistry.content().addAttachments(this);
}

public String getName()
{
  return this.name;
}

public abstract ItemGun[] getApprovedGunList();

public abstract boolean canBeUsedWithGun(ItemGun paramItemGun, ItemAttachment[] paramArrayOfItemAttachment);
}
