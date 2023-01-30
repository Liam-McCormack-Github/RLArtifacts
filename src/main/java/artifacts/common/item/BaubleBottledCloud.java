package artifacts.common.item;

import artifacts.common.init.ModNetworkHandler;
import artifacts.common.init.ModSoundEvents;
import artifacts.common.network.PacketBottledCloudJump;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class BaubleBottledCloud extends BaubleBase {

    public final boolean isFart;

    public BaubleBottledCloud(String name, boolean isFart) {
        super(name, BaubleType.BELT);
        this.isFart = isFart;
    }

    @SideOnly(Side.CLIENT)
    private static boolean canDoubleJump;

    @SideOnly(Side.CLIENT)
    private static boolean hasReleasedJumpKey;

    @SubscribeEvent
    @SuppressWarnings("unused")
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;

            if (player != null) {
                if ((player.onGround || player.isOnLadder()) && !player.isInWater()) {
                    hasReleasedJumpKey = false;
                    canDoubleJump = true;
                } else {
                    if (!player.movementInput.jump) {
                        hasReleasedJumpKey = true;
                    } else {
                        if (!player.capabilities.isFlying && canDoubleJump && hasReleasedJumpKey) {
                            canDoubleJump = false;
                            ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(BaubleType.BELT.getValidSlots()[0]);
                            if (stack.getItem() instanceof BaubleBottledCloud) {
                                ModNetworkHandler.NETWORK_HANDLER_INSTANCE.sendToServer(new PacketBottledCloudJump(((BaubleBottledCloud) stack.getItem()).isFart));
                                player.jump();
                                player.fallDistance = 0;
                                if (((BaubleBottledCloud) stack.getItem()).isFart) {
                                    player.playSound(ModSoundEvents.FART, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                                } else {
                                    player.playSound(SoundEvents.BLOCK_CLOTH_FALL, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
