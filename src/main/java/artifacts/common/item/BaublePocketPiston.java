package artifacts.common.item;

import artifacts.common.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.util.BaubleHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public abstract class BaublePocketPiston {

    @SubscribeEvent
    public static void onLivingKnockback(LivingKnockBackEvent event) {
        if(event.getAttacker() instanceof EntityPlayer) {
            int amount = BaubleHelper.getAmountBaubleEquipped((EntityPlayer)event.getAttacker(), ModItems.POCKET_PISTON);
            if(amount > 0) event.setStrength(event.getStrength() * (float)ModConfig.general.pistonKnockback * (float)amount);
        }
    }
}
