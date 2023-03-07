package artifacts.client.model;

import artifacts.Artifacts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelSnorkel extends ModelBase {

    public static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/snorkel.png");
    protected ModelRenderer snorkelMouthPiece;
    protected ModelRenderer snorkelTubeThing;
    protected ModelRenderer snorkelGoggles;
    protected ModelRenderer snorkelGogglesOverlay;

    public ModelSnorkel() {
        textureWidth = 64;
        textureHeight = 64;

        snorkelMouthPiece = new ModelRenderer(this, 0, 46);
        snorkelMouthPiece.addBox(-2, -1.5F, -6, 8, 2, 2, 0);

        snorkelGoggles = new ModelRenderer(this, 28, 32);
        snorkelGoggles.addBox(-4, -7, -4, 8, 8, 8, 1);
        snorkelMouthPiece.addChild(snorkelGoggles);

        snorkelGogglesOverlay = new ModelRenderer(this, 28, 48);
        snorkelGogglesOverlay.addBox(-4, -7, -4, 8, 8, 8, 1);
        snorkelMouthPiece.addChild(snorkelGogglesOverlay);

        snorkelTubeThing = new ModelRenderer(this, 0, 32);
        snorkelTubeThing.addBox(4, -5, -3, 2, 2, 12, 0);
        snorkelTubeThing.rotateAngleX = 0.7853F;
        snorkelMouthPiece.addChild(snorkelTubeThing);

        snorkelMouthPiece.showModel = false;
        snorkelTubeThing.showModel = false;
        snorkelGoggles.showModel = false;
        snorkelGogglesOverlay.showModel = false;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, true);
    }

    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, boolean renderFull) {
        if(!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)entity;

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        snorkelMouthPiece.showModel = true;
        snorkelTubeThing.showModel = true;
        snorkelGoggles.showModel = renderFull;

        snorkelMouthPiece.render(scale);

        snorkelGoggles.showModel = false;
        snorkelMouthPiece.showModel = false;
        snorkelTubeThing.showModel = false;

        if(renderFull) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.color(1, 1, 1, 0.3F);

            snorkelGogglesOverlay.showModel = true;
            snorkelMouthPiece.render(scale);
            snorkelGogglesOverlay.showModel = false;

            GlStateManager.disableBlend();
        }
    }
}