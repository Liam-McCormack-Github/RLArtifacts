package artifacts.common.network;

import artifacts.common.init.ModSoundEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBottledCloudJump implements IMessage {

    boolean isFart;

    @SuppressWarnings("unused")
    public PacketBottledCloudJump() { }

    public PacketBottledCloudJump(boolean isFart) {
        this.isFart = isFart;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        isFart = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(isFart);
    }

    public static class PacketHandler implements IMessageHandler<PacketBottledCloudJump, IMessage> {

        @Override
        public IMessage onMessage(PacketBottledCloudJump message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                player.fallDistance = 0;
                player.jump();
                for(int x = -1; x <= 1; x++) {
                    for(int z = -1; z <= 1; z++) {
                        if(x != 0 && z != 0) player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX + ((double)x)/1.5, player.posY, player.posZ + ((double)z)/1.5, 0, 0, 0);
                        else player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX + x, player.posY, player.posZ + z, 0, 0, 0);
                    }
                }
                if (message.isFart) {
                    player.playSound(ModSoundEvents.FART, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                } else {
                    player.playSound(SoundEvents.BLOCK_CLOTH_FALL, 1.3F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                }
            });

            return null;
        }
    }
}
