package starglas.dsremake.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import starglas.dsremake.common.helpers.ModReference;

public class BlockRendererJokesOnYou extends TileEntitySpecialRenderer{
	
	IModelCustom model;
	ResourceLocation texture = new ResourceLocation(ModReference.MODID,
			"textures/items/testtexture.png");

	public BlockRendererJokesOnYou(){
		this.model = AdvancedModelLoader.loadModel(new ResourceLocation(
				ModReference.MODID, "obj/DADDY.obj"));
	}
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float)x + 0.5F,(float)y + 0F, (float)z + 0.4F);
			GL11.glRotatef(90F, 0F, 0F, 1F);
			GL11.glScalef(0.01F, 0.01F, 0.01F);
			this.bindTexture(texture);
			GL11.glPushMatrix();
				model.renderAll();
			GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}
