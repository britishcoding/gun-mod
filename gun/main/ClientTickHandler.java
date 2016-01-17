package Com.gun.main;





import Com.gun.gun.EnumGunType;
import Com.gun.gun.ItemGun;
import Com.gun.gun.ItemMagazine;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import org.lwjgl.opengl.GL11;



public class ClientTickHandler
{
  public boolean isZoomed = false;
  private ResourceLocation m22Crosshair;
  private ResourceLocation ironCrosshair;
  private ResourceLocation ammo;
  private int lastPerspective = -1;
  public int flashTimeout = 0;
  public int scopeTime = -1;
  
  public ClientTickHandler()
  {
    this.m22Crosshair = new ResourceLocation("battlefield:textures/gui/crosshair_m22.png");
    this.ironCrosshair = new ResourceLocation("battlefield:textures/gui/crosshair_iron.png");
    this.ammo = new ResourceLocation("battlefield:textures/gui/ammo.png");
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void renderOverlay(RenderGameOverlayEvent event)
  {
    GL11.glPushMatrix();
    
    ScaledResolution res = event.resolution;
    EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
    ItemStack stack = player.getHeldItem();
    ItemGun item = (stack != null) && ((stack.getItem() instanceof ItemGun)) ? (ItemGun)stack.getItem() : null;
    FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    if (item != null)
    {
      if (this.isZoomed)
      {
        boolean isIronsight = item.hasAttachment(stack, MainRegistry.content().ironSights.getName());
        ResourceLocation texture = isIronsight ? this.ironCrosshair : this.m22Crosshair;
        
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, res.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(res.getScaledWidth(), res.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(res.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }
      NBTTagCompound nbt = stack.stackTagCompound;
      ItemMagazine mag = (ItemMagazine)item.getMagazineItem();
      String text = (nbt != null ? nbt.getInteger("remainingAmmo") : 0) + "/" + String.valueOf(mag != null ? mag.getMaxDamage() : 0);
      fr.drawStringWithShadow(text, res.getScaledWidth() - fr.getStringWidth(text) - 10, res.getScaledHeight() - 20, 16777215);
      String reloadText = null;
      if (item.hasAmmo(stack))
      {
        if (nbt.getInteger("remainingAmmo") < (item.getGunType() == EnumGunType.PISTOL ? 3 : 10)) {
          if (nbt.getInteger("remainingAmmo") > 0) {
            if (item.getGunType() != EnumGunType.MISC) {
              reloadText = I18n.format("gui.overlay.low", new Object[0]);
            }
          }
        }
      }
      else {
        reloadText = I18n.format("gui.overlay.reload", new Object[] { GameSettings.getKeyDisplayString(KeyHandler.reload.getKeyCode()) });
      }
      if ((reloadText != null) && (!player.capabilities.isCreativeMode)) {
        drawCenteredString(fr, reloadText, res.getScaledWidth() / 2, res.getScaledHeight() / 2 + fr.FONT_HEIGHT, 16777215);
      }
    }
    GL11.glPopMatrix();
    
    GL11.glColor3f(1.0F, 1.0F, 1.0F);
    Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
  }
  
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void fovUpdate(FOVUpdateEvent event)
  {
    float fov = event.fov;
    ItemStack stack = event.entity.getHeldItem();
    if ((stack != null) 
    		&& ((stack.getItem() instanceof ItemGun)) 
    		&& (((ItemGun)stack
    				.getItem())
    				.canUseScope
    				(stack)) 
    		&& (this.isZoomed))
    {
      this.scopeTime += 1;
      if (this.lastPerspective == -1) {
        this.lastPerspective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
      }
      if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
      }
      float multiplier = 4.0F;
      boolean isIronsight = ((ItemGun)stack.getItem()).hasAttachment(stack, MainRegistry.content().ironSights.getName());
      
      multiplier *= multiplier;
      if (isIronsight) {
        multiplier *= 0.05F;
      }
      fov *= (1.0F - multiplier * 0.8F);
    }
    else
    {
      this.isZoomed = false;
      this.scopeTime = -1;
      if (this.lastPerspective != -1)
      {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = this.lastPerspective;
        this.lastPerspective = -1;
      }
    }
    event.newfov = fov;
  }
  
  @Deprecated
  public float tickScope(EntityPlayerSP player, ItemStack stack, ItemGun item, float fov)
  {
    if (this.isZoomed)
    {
      this.scopeTime += 1;
      if (this.lastPerspective == -1) {
        this.lastPerspective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
      }
      if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
        Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
      }
      if ((stack == null) || (item == null) || (!item.canUseScope(stack)))
      {
        this.isZoomed = false;
        return fov;
      }
      float f = fov;
      boolean isAcog = item.hasAttachment(stack, MainRegistry.content().ironSights.getName());
      if (player.capabilities.isFlying) {
        f *= 1.1F;
      }
      f *= (player.moveForward * player.moveForward + 1.0F) / 2.0F;
      if ((player.isUsingItem()) && (player.getItemInUse().isItemEqual(stack)))
      {
        int i = stack.getItem().getMaxItemUseDuration(stack);
        float f1 = i / 20.0F;
        if (f1 > 1.0F) {
          f1 = 1.0F;
        } else {
          f1 *= f1;
        }
        f *= (1.0F - f1 * 0.15F);
      }
      float fovModifierHand = fov;
      fovModifierHand += (f - fovModifierHand) * 0.5F;
      if (fovModifierHand > 1.5F) {
        fovModifierHand = 1.5F;
      }
      if (fovModifierHand < 0.1F) {
        fovModifierHand = 0.1F;
      }
      if (isAcog) {
        fovModifierHand *= 4.0F;
      }
      return fovModifierHand;
    }
    if (this.lastPerspective != -1)
    {
      Minecraft.getMinecraft().gameSettings.thirdPersonView = this.lastPerspective;
      this.lastPerspective = -1;
    }
    if (this.scopeTime > 0) {
      this.scopeTime = -1;
    }
    return fov;
  }
  
  public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
  {
    par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
  }
}
