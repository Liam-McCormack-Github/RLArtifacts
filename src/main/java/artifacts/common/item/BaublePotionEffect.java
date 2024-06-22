package artifacts.common.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class BaublePotionEffect extends BaubleBase {

    public final Potion effect;
    public final int effectAmplifier;
    public final int duration;

    public BaublePotionEffect(String name, BaubleType type, Potion effect, int effectAmplifier, int duration) {
        super(name, type);
        this.effect = effect;
        this.effectAmplifier = effectAmplifier;
        this.duration = Math.max(duration, 40);
    }

    public BaublePotionEffect(String name, BaubleType type, Potion effect, int effectAmplifier) {
        this(name, type, effect, effectAmplifier, 0);
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase player) {
        if (player.ticksExisted % 39 == 0) {
            player.addPotionEffect(new PotionEffect(effect, duration, effectAmplifier, true, false));
        }
    }
}
