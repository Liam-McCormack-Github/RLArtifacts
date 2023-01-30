package artifacts.common.item;

import artifacts.common.ModConfig;
import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BaubleAntidoteVessel extends BaubleBase {

    public BaubleAntidoteVessel(String name) {
        super(name, BaubleType.BELT);
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        Collection<PotionEffect> toRemove = new ArrayList<>();
        for(PotionEffect effect : player.getActivePotionEffects()) {
            if(effect.getPotion().isBadEffect() && !Arrays.asList(ModConfig.general.vesselBlacklist).contains(effect.getPotion().getRegistryName().toString()) &&
                    (effect.getDuration() > ModConfig.general.vesselMaxDuration || effect.getAmplifier() > ModConfig.general.vesselMaxAmplifier)) {
                toRemove.add(effect);
            }
        }
        for(PotionEffect effect : toRemove) {
            player.removeActivePotionEffect(effect.getPotion());
            if(ModConfig.general.vesselMaxDuration > 0) player.addPotionEffect(new PotionEffect(
                    effect.getPotion(),
                    Math.min(effect.getDuration(),
                            ModConfig.general.vesselMaxDuration),
                    Math.min(effect.getAmplifier(),
                            ModConfig.general.vesselMaxAmplifier),
                    effect.getIsAmbient(),
                    effect.doesShowParticles()));
        }
    }
}