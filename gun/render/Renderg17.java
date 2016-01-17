package Com.gun.render;

import org.lwjgl.opengl.GL11;








import Com.gun.Model.glock17Model;
import Com.gun.main.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class Renderg17 implements IItemRenderer
{ 
	  protected glock17Model model;
	  
	  public Renderg17()
	  {
	    this.model = new glock17Model();
	  }
	  
	  public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
	  {
	    switch (type)
	    {
	    case ENTITY:
	    case EQUIPPED: 
	    case EQUIPPED_FIRST_PERSON: 
	      return true;
		default:
			break;
	    }
	    return false;
	  }
	  
	  public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
	  {
	    return false;
	  }
	  
	  public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
	  {
	    AbstractClientPlayer player = Minecraft.getMinecraft().thePlayer;
	    switch (type)
	    {
	    case EQUIPPED: 
	    case EQUIPPED_FIRST_PERSON: 
	    	   GL11.glPushMatrix();
	    	      
	    	      boolean isFirtsPerson = false;
	    	      if ((data[1] != null) && ((data[1] instanceof EntityPlayer))) {
	    	        if (((EntityPlayer)data[1] != Minecraft.getMinecraft().renderViewEntity) || (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) || ((((Minecraft.getMinecraft().currentScreen instanceof GuiInventory)) || ((Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative))) && (RenderManager.instance.playerViewY == 180.0F)))
	    	        {
	    	            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/glock22.png")); 
	    	            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	    	            GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
	    	            GL11.glRotatef(5.0F, 0.0F, 1.0F, 0.0F);
	    	            
	    	            GL11.glTranslatef(0.65F, 0.0F, 0.05F);
	    	            
	    	            double scale = 0.5D;
	    	            GL11.glScaled(scale, scale, scale);
	    	    	      this.model.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	    	        }
	    	        else
	    	        {
	    	        	
	    	        	isFirtsPerson = true;
	    	      Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/glock22.png")); 
	    	        	GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	    	            GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
	    	            GL11.glRotatef(-5.0F, 0.0F, 1.0F, 0.0F);
	    	            
	    	            GL11.glTranslatef(0.6F, -0.1F, 0.1F);
	    	            
	    	            double scale = 0.6D;
	    	            GL11.glScaled(scale, scale, scale);
	    	    	      this.model.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	    	        }
	    	      }
	  
	    	      
	    	      GL11.glPopMatrix();
	        
	   
		default:
			break;
	     
	    }
	  }
	}


