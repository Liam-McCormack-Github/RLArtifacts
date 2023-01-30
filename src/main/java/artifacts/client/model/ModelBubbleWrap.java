package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.lwjgl.opengl.GL11;

public class ModelBubbleWrap extends ModelBase {

    public ModelRenderer belt;

    public ModelBubbleWrap() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        belt = new ModelRenderer(this, 0, 0);
        //belt.addBox(-5, -1, -3, 10, 12, 6);
        belt.addBox(-5, 3, -3, 10, 8, 6);
    }

    @Override
    public void render(Entity entity, float partialticks, float f1, float f2, float f3, float f4, float scale) {
        boolean hasPants = !((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.scale(7 / 6F, 7 / 6F, 7 / 6F);
        GlStateManager.scale(1, 1, hasPants ? 1.2F : 1.1F);

        GlStateManager.pushMatrix();
        belt.render(scale);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
    }
}