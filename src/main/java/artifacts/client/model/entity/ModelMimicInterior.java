package artifacts.client.model.entity;

import artifacts.common.entity.EntityMimic;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelMimicInterior extends ModelBase {

    public ModelRenderer chestLidTeeth;
    public ModelRenderer chestBelowTeeth;
    public ModelRenderer chestLidMouth;
    public ModelRenderer chestBelowMouth;

    public ModelMimicInterior() {
        textureWidth = 64;
        textureHeight = 64;

        chestLidTeeth = new ModelRenderer(this);
        chestLidTeeth.setRotationPoint(0.0F, 15.0F, 7.0F);
        chestLidTeeth.cubeList.add(new ModelBox(chestLidTeeth, 14, 27, -6.0F, 0.0F, -13.0F, 12, 2, 12, 0.0F, false));

        chestBelowTeeth = new ModelRenderer(this);
        chestBelowTeeth.setRotationPoint(0.0F, 14.0F, 7.0F);
        chestBelowTeeth.cubeList.add(new ModelBox(chestBelowTeeth, 14, 42, -6.0F, -3.0F, -13.0F, 12, 3, 12, 0.0F, false));

        chestLidMouth = new ModelRenderer(this);
        chestLidMouth.setRotationPoint(0.0F, 15.0F, 7.0F);
        chestLidMouth.cubeList.add(new ModelBox(chestLidMouth, 2, 14, -6.0F, 0.05F, -13.0F, 12, 0, 12, 0.0F, false));

        chestBelowMouth = new ModelRenderer(this);
        chestBelowMouth.setRotationPoint(0.0F, 14.0F, 7.0F);
        chestBelowMouth.cubeList.add(new ModelBox(chestBelowMouth, 14, 14, -6.0F, -0.05F, -13.0F, 12, 0, 12, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        chestLidTeeth.render(scale);
        chestBelowTeeth.render(scale);
        chestLidMouth.render(scale);
        chestBelowMouth.render(scale);
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTickTime);

        if(((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.min(60, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * 6);
            chestLidTeeth.rotateAngleX = chestLidMouth.rotateAngleX = -angle * 0.0174533F;
        }
        else {
            chestLidTeeth.rotateAngleX = chestLidMouth.rotateAngleX = 0;
        }

        if(((EntityMimic) entity).ticksInAir > 0) {
            float angle = Math.max(-30, (((EntityMimic) entity).ticksInAir - 1 + partialTickTime) * -3F);
            chestBelowTeeth.rotateAngleX = chestBelowMouth.rotateAngleX = -angle * 0.0174533F;
        }
        else {
            chestBelowTeeth.rotateAngleX = chestBelowMouth.rotateAngleX = 0;
        }
    }
}