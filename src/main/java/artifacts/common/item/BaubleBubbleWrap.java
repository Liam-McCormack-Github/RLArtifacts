package artifacts.common.item;

import artifacts.common.init.ModItems;
import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public abstract class BaubleBubbleWrap {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getSource() == DamageSource.FLY_INTO_WALL && event.getEntityLiving() instanceof EntityPlayer) {
            if(BaublesApi.isBaubleEquipped((EntityPlayer)event.getEntityLiving(), ModItems.BUBBLE_WRAP) != -1) {
                event.setCanceled(true);
            }
        }
    }
}