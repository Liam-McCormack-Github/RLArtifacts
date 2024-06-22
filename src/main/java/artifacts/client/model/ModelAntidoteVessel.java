package artifacts.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ModelAntidoteVessel extends ModelBase {

    public ModelRenderer belt;

    public ModelRenderer jar;
    public ModelRenderer lid;

    public ModelAntidoteVessel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        belt = new ModelRenderer(this, 0, 0);
        belt.addBox(-4, -1, -2, 8, 12, 4);

        jar = new ModelRenderer(this, 0, 16);
        jar.addBox(2, 0, 2, 4, 6, 4);

        lid = new ModelRenderer(this, 0, 26);
        lid.addBox(3, -1, 3, 2, 1, 2);
    }

    @Override
    public void render(Entity entity, float partialticks, float f1, float f2, float f3, float f4, float scale) {
        boolean hasPants = !((EntityPlayer) entity).getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty();
        //scale = 1/16F;//Only rendered with static scale before, so, /shrug

        GlStateManager.scale(7 / 6F, 7 / 6F, 7 / 6F);

        GlStateManager.scale(1, 1, hasPants ? 1.2F : 1.1F);
        belt.render(scale);
        GlStateManager.scale(1, 1, hasPants ? 1 / 1.2F : 1 / 1.1F);

        GlStateManager.scale(1 / 2F, 1 / 2F, 1 / 2F);
        GlStateManager.translate(0, 1, -2 / 3F);
        GlStateManager.translate(1 / 5F, 0, 0);
        GlStateManager.rotate(-15, 0, 1, 0);

        jar.render(scale);
        lid.render(scale);
    }
}