package artifacts.client.model;

import artifacts.Artifacts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class ModelDrinkingHat extends ModelBase {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/drinking_hat.png");
    private static final ResourceLocation SPECIAL_TEXTURES = new ResourceLocation(Artifacts.MODID, "textures/entity/layer/drinking_hat_special.png");

    protected ModelRenderer hat;
    protected ModelRenderer canLeft;
    protected ModelRenderer canRight;
    protected ModelRenderer strawLeft;
    protected ModelRenderer strawRight;
    protected ModelRenderer strawMiddle;
    protected ModelRenderer hatShade;

    public ModelDrinkingHat() {
        textureWidth = 64;
        textureHeight = 64;

        hat = new ModelRenderer(this, 26, 41);
        hat.addBox(-4, -8, -4, 8, 8, 8, 1);

        hatShade = new ModelRenderer(this, 0, 52);
        hatShade.addBox(-4, -6, -8, 8, 1, 4, 0);
        hat.addChild(hatShade);

        canLeft = new ModelRenderer(this, 0, 41);
        canLeft.addBox(4, -11, -1, 3, 6, 3);
        hat.addChild(canLeft);

        canRight = new ModelRenderer(this, 12, 41);
        canRight.addBox(-7, -11, -1, 3, 6, 3);
        hat.addChild(canRight);

        strawLeft = new ModelRenderer(this, 0, 32);
        strawLeft.addBox(5, -4, -3, 1, 1, 8);
        strawLeft.rotateAngleX = 0.7853F;
        hat.addChild(strawLeft);

        strawRight = new ModelRenderer(this, 17, 32);
        strawRight.addBox(-6, -4, -3, 1, 1, 8);
        strawRight.rotateAngleX = 0.7853F;
        hat.addChild(strawRight);

        strawMiddle = new ModelRenderer(this, 0, 50);
        strawMiddle.addBox(-6, -1, -5, 12, 1, 1);
        hat.addChild(strawMiddle);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)entity;

        if(player.getName().equals("wouterke")) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(SPECIAL_TEXTURES);
            hatShade.showModel = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty();
        }
        else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);
            hatShade.showModel = false;
        }

        hat.render(scale);
    }
}