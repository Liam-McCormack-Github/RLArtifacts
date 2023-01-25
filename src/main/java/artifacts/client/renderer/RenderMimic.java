package artifacts.client.renderer;

import artifacts.Artifacts;
import artifacts.client.model.entity.ModelMimic;
import artifacts.client.model.entity.ModelMimicInterior;
import artifacts.common.entity.EntityMimic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RenderMimic extends RenderLiving<EntityMimic> {

    public static final Factory FACTORY = new Factory();

    private static final ResourceLocation MIMIC_TEXTURE = new ResourceLocation(Artifacts.MODID, "textures/entity/mimic/mimic.png");
    private static final ResourceLocation VANILLA_CHEST = new ResourceLocation("textures/entity/chest/normal.png");

    private ModelBase secondModel;

    protected RenderMimic(RenderManager renderManager) {
        super(renderManager, new ModelMimic(), 0.45F);
        this.secondModel = new ModelMimicInterior();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMimic entity) {
        return VANILLA_CHEST;
    }

    protected ResourceLocation getSecondaryEntityTexture(EntityMimic entity) {
        return MIMIC_TEXTURE;
    }

    @Override
    public void doRender(EntityMimic entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<EntityMimic>(entity, this, partialTicks, x, y, z))) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.secondModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isRiding = this.secondModel.isRiding = shouldSit;
        this.mainModel.isChild = this.secondModel.isChild = entity.isChild();

        try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if(shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, f, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            if(!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F)
                {
                    f5 = 1.0F;
                }
                f2 = f1 - f; // Forge: Fix MC-1207
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.secondModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            this.secondModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

            if(this.renderOutlines) {
                boolean flag1 = this.setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entity));

                if(!this.renderMarker) {
                    this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                }

                this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);

                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if(flag1) {
                    this.unsetScoreTeamColor();
                }
            }
            else {
                boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f6, f5, f8, f2, f7, f4);

                if(flag) {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);

                this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
            }
            GlStateManager.disableRescaleNormal();
        }
        catch(Exception ignored) { }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        if(!this.renderOutlines) {
            this.renderName(entity, x, y, z);
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<EntityMimic>(entity, this, partialTicks, x, y, z));
    }

    @Override
    protected void renderModel(EntityMimic entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);

        if(flag || flag1) {
            if(!this.bindEntityTexture(entitylivingbaseIn)) return;

            if(flag1) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
            else {
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }

            if(!this.bindSecondaryTexture(entitylivingbaseIn)) return;

            if(flag1) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
                this.secondModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
            else {
                this.secondModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }
        }
    }

    private boolean bindSecondaryTexture(EntityMimic entity) {
        ResourceLocation resourcelocation = this.getSecondaryEntityTexture(entity);

        if(resourcelocation == null) return false;
        else {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

    public static class Factory implements IRenderFactory<EntityMimic> {

        @Override
        public Render<? super EntityMimic> createRenderFor(RenderManager manager) {
            return new RenderMimic(manager);
        }
    }
}