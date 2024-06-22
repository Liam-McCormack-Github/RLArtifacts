package artifacts.common.item;

import artifacts.common.init.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public abstract class BaubleLuckyHorseshoe {

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.LUCKY_HORSESHOE) != -1) {
                if (event.getDistance() > 5) {
                    PotionEffect potioneffect = event.getEntityLiving().getActivePotionEffect(MobEffects.JUMP_BOOST);
                    float f = potioneffect == null ? 0.0F : (float) (potioneffect.getAmplifier() + 1);
                    int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
                    if (i > 0) {
                        event.getEntity().playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1, 1);
                    }
                }
                event.setCanceled(true);
            }
        }
    }
}