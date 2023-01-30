package artifacts.client.model.layer;

import artifacts.Artifacts;
import artifacts.client.model.ModelAntidoteVessel;
import artifacts.client.model.ModelBottledCloud;
import artifacts.client.model.ModelBubbleWrap;
import artifacts.common.init.ModItems;
import artifacts.common.util.RenderHelper;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LayerBelt extends LayerBauble {
    private static final ResourceLocation BOTTLED_CLOUD = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/bottled_cloud.png");
    private static final ResourceLocation BOTTLED_FART = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/bottled_fart.png");
    private static final ResourceLocation ANTIDOTE_VESSEL = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/antidote_vessel.png");
    private static final ResourceLocation BUBBLE_WRAP = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/bubble_wrap.png");

    private static final ModelBase BOTTLE_MODEL = new ModelBottledCloud();
    private static final ModelBase ANTIDOTE_MODEL = new ModelAntidoteVessel();
    private static final ModelBase BUBBLE_MODEL = new ModelBubbleWrap();

    public LayerBelt(RenderPlayer renderPlayer) {
        super(renderPlayer);
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        renderBelt(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    private void renderBelt(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ModelBase belt = setTexturesGetModel(player);
        if(belt == null) return;

        if(player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        modelPlayer.bipedBody.postRender(scale);
        belt.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

    private ModelBase setTexturesGetModel(EntityPlayer player) {
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.BELT.getValidSlots()[0]);
        if(!RenderHelper.shouldItemStackRender(player, stack)) return null;
        ResourceLocation textures = getTextures(stack);
        if(textures != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(textures);
            if(stack.getItem() == ModItems.ANTIDOTE_VESSEL) return ANTIDOTE_MODEL;
            else if(stack.getItem() == ModItems.BUBBLE_WRAP) return BUBBLE_MODEL;
            else return BOTTLE_MODEL;
        }
        return null;
    }

    private @Nullable ResourceLocation getTextures(ItemStack stack) {
        if(stack.getItem() == ModItems.BOTTLED_CLOUD) return BOTTLED_CLOUD;
        else if(stack.getItem() == ModItems.BOTTLED_FART) return BOTTLED_FART;
        else if(stack.getItem() == ModItems.ANTIDOTE_VESSEL) return ANTIDOTE_VESSEL;
        else if(stack.getItem() == ModItems.BUBBLE_WRAP) return BUBBLE_WRAP;
        else return null;
    }
}