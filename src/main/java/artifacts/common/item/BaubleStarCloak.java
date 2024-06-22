package artifacts.common.item;

import artifacts.common.ModConfig;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.init.ModItems;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class BaubleStarCloak extends BaubleBase {

    public BaubleStarCloak() {
        super("star_cloak", BaubleType.TRINKET);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), ModItems.STAR_CLOAK) != -1) {
            if (ModConfig.general.starCloakCooldown > 0 && ((EntityPlayer) event.getEntityLiving()).getCooldownTracker().hasCooldown(ModItems.STAR_CLOAK))
                return;
            if (event.getSource().getTrueSource() instanceof EntityLiving &&
                    (ModConfig.general.starCloakIndirect ||
                            (!event.getSource().isProjectile() && !(event.getSource() instanceof EntityDamageSourceIndirect)) &&
                                    event.getEntityLiving().world.canSeeSky(event.getEntityLiving().getPosition().up()))) {
                int stars = ModConfig.general.starCloakStarsMin;
                if (ModConfig.general.starCloakStarsMax > ModConfig.general.starCloakStarsMin) {
                    stars += event.getEntityLiving().getRNG().nextInt(ModConfig.general.starCloakStarsMax - ModConfig.general.starCloakStarsMin + 1);
                }
                for (int i = 0; i < stars; i++) {
                    event.getEntityLiving().world.spawnEntity(new EntityHallowStar(event.getEntityLiving().world, event.getEntityLiving()));
                }
                if (stars > 0)
                    ((EntityPlayer) event.getEntityLiving()).getCooldownTracker().setCooldown(ModItems.STAR_CLOAK, ModConfig.general.starCloakCooldown);
            }
        }
    }
}
