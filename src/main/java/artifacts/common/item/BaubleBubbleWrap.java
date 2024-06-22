package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModSoundEvents;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BaubleBubbleWrap extends BaubleBase {

    public BaubleBubbleWrap() {
        super("bubble_wrap", BaubleType.TRINKET);
        setMaxDamage(0);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource() == DamageSource.FLY_INTO_WALL && event.getEntityLiving() instanceof EntityPlayer) {
            if (BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), ModItems.BUBBLE_WRAP) != -1) {
                event.setCanceled(true);
            }
        }
    }

    @Override
    public void registerModel() {
        super.registerModel();
        Artifacts.proxy.registerItemRenderer(this, 1, name);
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        if (stack.getMetadata() == 1 && !player.isSneaking()) stack.setItemDamage(0);
        else if (stack.getMetadata() == 0 && player.isSneaking()) {
            stack.setItemDamage(1);
            if (player.getRNG().nextInt(3) == 0) {
                player.playSound(ModSoundEvents.BUBBLE_WRAP, 1, 0.9F + player.getRNG().nextFloat() * 0.2F);
            }
        }
    }
}