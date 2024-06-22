package artifacts.client.model.layer;

import artifacts.client.model.ModelNightVisionGoggles;
import artifacts.common.init.ModItems;
import artifacts.common.util.RenderHelper;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class LayerNightVisionGoggles extends LayerBauble {

    private static final ModelNightVisionGoggles goggles = new ModelNightVisionGoggles();

    public LayerNightVisionGoggles(RenderPlayer renderPlayer) {
        super(renderPlayer);
    }

    @Override
    protected void renderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (BaublesApi.isBaubleEquipped(player, ModItems.NIGHT_VISION_GOGGLES) == -1 || !RenderHelper.shouldRenderInSlot(player, EntityEquipmentSlot.HEAD))
            return;
        ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.HEAD.getValidSlots()[0]);
        if (stack.getItem() != ModItems.NIGHT_VISION_GOGGLES || !RenderHelper.shouldItemStackRender(player, stack))
            return;

        if (player.isSneaking()) GlStateManager.translate(0, 0.2F, 0);
        modelPlayer.bipedHead.postRender(scale);
        goggles.render(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
