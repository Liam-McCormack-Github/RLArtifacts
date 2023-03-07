package artifacts.client.model;

import artifacts.Artifacts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ModelNightVisionGoggles extends ModelBase {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/night_vision_goggles.png");

    protected ModelRenderer headBand;
    protected ModelRenderer goggleBase;
    protected ModelRenderer eyeLeft;
    protected ModelRenderer eyeRight;
    protected ModelRenderer eyeLeftOverlay;
    protected ModelRenderer eyeRightOverlay;

    public ModelNightVisionGoggles() {
        textureWidth = 64;
        textureHeight = 64;

        headBand = new ModelRenderer(this, 0, 32);
        headBand.addBox(-4, -8, -4, 8, 8, 8, 0.9F);

        goggleBase = new ModelRenderer(this, 0, 53);
        goggleBase.addBox(-4, -6, -5 + 0.05F, 8, 4, 1);
        headBand.addChild(goggleBase);

        eyeLeft = new ModelRenderer(this, 0, 48);
        eyeLeft.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        headBand.addChild(eyeLeft);

        eyeRight = new ModelRenderer(this, 10, 48);
        eyeRight.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        headBand.addChild(eyeRight);

        eyeLeftOverlay = new ModelRenderer(this, 20, 48);
        eyeLeftOverlay.addBox(1 + 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        headBand.addChild(eyeLeftOverlay);

        eyeRightOverlay = new ModelRenderer(this, 30, 48);
        eyeRightOverlay.addBox(-3 - 0.5F, -5, -8 + 0.05F, 2, 2, 3);
        headBand.addChild(eyeRightOverlay);

        headBand.showModel = false;
        goggleBase.showModel = false;
        eyeLeft.showModel = false;
        eyeRight.showModel = false;
        eyeLeftOverlay.showModel = false;
        eyeRightOverlay.showModel = false;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)entity;

        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);

        headBand.showModel = true;
        goggleBase.showModel = true;
        eyeLeft.showModel = true;
        eyeRight.showModel = true;

        headBand.render(scale);

        goggleBase.showModel = false;
        eyeLeft.showModel = false;
        eyeRight.showModel = false;
        headBand.showModel = false;

        float lastLightmapX = OpenGlHelper.lastBrightnessX;
        float lastLightmapY = OpenGlHelper.lastBrightnessY;

        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;

        eyeLeftOverlay.showModel = true;
        eyeRightOverlay.showModel = true;

        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);

        eyeLeftOverlay.render(scale);
        eyeRightOverlay.render(scale);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
        GlStateManager.enableLighting();

        eyeLeftOverlay.showModel = false;
        eyeRightOverlay.showModel = false;

    }
}