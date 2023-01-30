package artifacts.client.model.layer;

import artifacts.Artifacts;
import artifacts.client.model.ModelCloak;
import artifacts.common.init.ModItems;
import artifacts.common.util.RenderHelper;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class LayerCloak extends LayerBauble {
    private static final ResourceLocation CLOAK_NORMAL = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak.png");
    private static final ResourceLocation CLOAK_OVERLAY = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/star_cloak_overlay.png");

    private static final ModelCloak CLOAK_MODEL = new ModelCloak();

    public LayerCloak(RenderPlayer renderPlayer) { super(renderPlayer); }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderChest(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    private void renderChest(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(BaublesApi.isBaubleEquipped(player, ModItems.STAR_CLOAK) == -1 || !RenderHelper.shouldRenderInSlot(player, EntityEquipmentSlot.CHEST)) return;
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.BODY.getValidSlots()[0]);
        if(stack.getItem() != ModItems.STAR_CLOAK || !RenderHelper.shouldItemStackRender(player, stack)) return;
        if(player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        boolean hoodUp = RenderHelper.shouldRenderInSlot(player, EntityEquipmentSlot.HEAD) &&
                (BaublesApi.isBaubleEquipped(player, ModItems.DRINKING_HAT) == -1 ||
                !RenderHelper.shouldItemStackRender(player, BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0])));

        Minecraft.getMinecraft().getTextureManager().bindTexture(CLOAK_NORMAL);
        renderBody(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);
        renderHead(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);

        float lastLightmapX = OpenGlHelper.lastBrightnessX;
        float lastLightmapY = OpenGlHelper.lastBrightnessY;
        int light = 15728880;
        int lightmapX = light % 65536;
        int lightmapY = light / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightmapX, lightmapY);

        Minecraft.getMinecraft().getTextureManager().bindTexture(CLOAK_OVERLAY);
        renderBody(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);
        renderHead(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastLightmapX, lastLightmapY);
    }

    private void renderBody(EntityPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, boolean hoodUp) {
        GlStateManager.pushMatrix();
        modelPlayer.bipedBody.postRender(scale);
        CLOAK_MODEL.renderCloak(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);
        GlStateManager.popMatrix();
    }

    private void renderHead(EntityPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, boolean hoodUp) {
        GlStateManager.pushMatrix();
        modelPlayer.bipedHead.postRender(scale);
        CLOAK_MODEL.renderHood(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, hoodUp);
        GlStateManager.popMatrix();
    }
}