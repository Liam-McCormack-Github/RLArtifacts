package artifacts.client.model.entity;

import artifacts.common.entity.EntityMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelMimic extends ModelBase {

    public ModelRenderer chestLid;
    public ModelRenderer chestBelow;
    public ModelRenderer chestKnob;

    /*
    public ModelMimic() {
        //textureWidth = 128;
        textureWidth = 64;
        textureHeight = 64;

        chestBelow = new ModelRenderer(this, 0, 19);
        //chestBelowTeeth = new ModelRenderer(this, 56, 13);
        chestLid = new ModelRenderer(this, 0, 0);
        //chestLidTeeth = new ModelRenderer(this, 56, 0);
        chestKnob = new ModelRenderer(this, 0, 0);

        chestBelow.setRotationPoint(0, 14, 7);
        //chestBelowTeeth.setRotationPoint(0, 14, 7);
        chestLid.setRotationPoint(0, 15, 7);
        //chestLidTeeth.setRotationPoint(0, 15, 7);
        chestKnob.setRotationPoint(0, 15, 7);

        chestBelow.addBox(-7, 0, -14, 14, 10, 14);
        //chestBelowTeeth.addBox(-6, -1, -13, 12, 1, 12);
        chestLid.addBox(-7, -5, -14, 14, 5, 14);
        //chestLidTeeth.addBox(-6, 0, -13, 12, 1, 12);
        chestKnob.addBox(-1, -2, -15, 2, 4, 1);
    }
    */

    public ModelMimic() {
        textureWidth = 64;
        textureHeight = 64;

        chestLid = new ModelRenderer(this);
        chestLid.setRotationPoint(0.0F, 15.0F, 7.0F);
        chestLid.cubeList.add(new ModelBox(chestLid, 0, 0, -7.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F, false));

        chestBelow = new ModelRenderer(this);
        chestBelow.setRotationPoint(0.0F, 14.0F, 7.0F);
        chestBelow.cubeList.add(new ModelBox(chestBelow, 0, 19, -7.0F, 0.0F, -14.0F, 14, 10, 14, 0.0F, false));

        chestKnob = new ModelRenderer(this);
        chestKnob.setRotationPoint(0.0F, 15.0F, 7.0F);
        chestKnob.cubeList.add(new ModelBox(chestKnob, 0, 0, -1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        chestLid.render(scale);
        chestBelow.render(scale);
        chestKnob.render(scale);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);

        if(((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.min(60, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * 6);
            chestLid.rotateAngleX = chestKnob.rotateAngleX = -angle * 0.0174533F;
        }
        else {
            chestLid.rotateAngleX = chestKnob.rotateAngleX = 0;
        }

        if(((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.max(-30, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * -3F);
            chestBelow.rotateAngleX = -angle * 0.0174533F;
        }
        else {
            chestBelow.rotateAngleX = 0;
        }
    }
}
