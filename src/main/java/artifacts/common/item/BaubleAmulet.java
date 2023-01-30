package artifacts.common.item;

import artifacts.Artifacts;
import artifacts.common.ModConfig;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModLootTables;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class BaubleAmulet extends BaubleBase {

    public BaubleAmulet(String name) {
        super(name, BaubleType.AMULET);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.getItem() == ModItems.SACRIFICIAL_AMULET && stack.getTagCompound() != null && (GuiScreen.isShiftKeyDown() || ModConfig.client.alwaysShowTooltip)) {
            tooltip.add(TextFormatting.DARK_RED + "" +
                    I18n.translateToLocal("tooltip." + Artifacts.MODID + "." + name + ".charge") +
                    ": " +
                    (ModConfig.general.sacrificialVisible ? "" : TextFormatting.OBFUSCATED) +
                    stack.getTagCompound().getInteger("Sacrificial Amulet Charge") + (ModConfig.general.sacrificialChargeShowTotal ? " / " + ModConfig.general.sacrificialCharge : ""));
        }
    }
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(!event.getEntity().world.isRemote && event.getEntity() instanceof EntityPlayer) {
            boolean hasUltimatePendant = BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.ULTIMATE_PENDANT) != -1;

            // flame pendant burning immunity
            if(event.getSource() == DamageSource.ON_FIRE && (hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.FLAME_PENDANT) != -1)) {
                event.setCanceled(true);
                if(event.getEntity().isBurning()) event.getEntity().extinguish();
                return;
            }

            //Don't trigger on tiny hits
            if(event.getAmount() < 1) return;

            // panic necklace
            if(BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.PANIC_NECKLACE) != -1) {
                event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 1, true, false));
            }

            // shock pendant
            if(hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.SHOCK_PENDANT) != -1) {
                if(event.getSource() == DamageSource.LIGHTNING_BOLT) event.setCanceled(true);
                else if(event.getSource().getTrueSource() instanceof EntityLiving && ModConfig.general.shockChance > 0) {
                    if(ModConfig.general.pendantCooldown <= 0 || !((EntityPlayer)event.getEntity()).getCooldownTracker().hasCooldown(ModItems.SHOCK_PENDANT)) {
                        EntityLiving attacker = (EntityLiving)event.getSource().getTrueSource();
                        Random random = ((EntityPlayer) event.getEntity()).getRNG();
                        if(random.nextFloat() < ModConfig.general.shockChance && attacker.world.canSeeSky(attacker.getPosition().up())) {
                            attacker.world.addWeatherEffect(new EntityLightningBolt(attacker.world, attacker.posX, attacker.posY, attacker.posZ, false));
                            if(ModConfig.general.pendantCooldown > 0) ((EntityPlayer)event.getEntity()).getCooldownTracker().setCooldown(ModItems.SHOCK_PENDANT, ModConfig.general.pendantCooldown);
                        }
                    }
                }
            }

            // flame pendant
            if(hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.FLAME_PENDANT) != -1) {
                if(event.getSource().getTrueSource() instanceof EntityLiving && ModConfig.general.flameChance > 0) {
                    if(ModConfig.general.pendantCooldown <= 0 || !((EntityPlayer)event.getEntity()).getCooldownTracker().hasCooldown(ModItems.FLAME_PENDANT)) {
                        EntityLiving attacker = (EntityLiving)event.getSource().getTrueSource();
                        Random random = ((EntityPlayer)event.getEntity()).getRNG();
                        if(!attacker.isImmuneToFire() && attacker.attackable() && random.nextFloat() < ModConfig.general.flameChance) {
                            attacker.setFire(4);
                            attacker.attackEntityFrom(new EntityDamageSource("onFire", event.getEntity()).setFireDamage(), 2);
                            if(ModConfig.general.pendantCooldown > 0) ((EntityPlayer)event.getEntity()).getCooldownTracker().setCooldown(ModItems.FLAME_PENDANT, ModConfig.general.pendantCooldown);
                        }
                    }
                }
            }

            // thorn pendant
            if(hasUltimatePendant || BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntity(), ModItems.THORN_PENDANT) != -1) {
                if(event.getSource().getTrueSource() instanceof EntityLiving && ModConfig.general.thornChance > 0) {
                    if(ModConfig.general.pendantCooldown <= 0 || !((EntityPlayer)event.getEntity()).getCooldownTracker().hasCooldown(ModItems.THORN_PENDANT)) {
                        EntityLiving attacker = (EntityLiving) event.getSource().getTrueSource();
                        Random random = ((EntityPlayer) event.getEntity()).getRNG();
                        if(attacker.attackable() && random.nextFloat() < ModConfig.general.thornChance) {
                            attacker.attackEntityFrom(DamageSource.causeThornsDamage(event.getEntity()), 2 + random.nextInt(3));
                            if(ModConfig.general.pendantCooldown > 0) ((EntityPlayer)event.getEntity()).getCooldownTracker().setCooldown(ModItems.THORN_PENDANT, ModConfig.general.pendantCooldown);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if(!event.getEntityLiving().world.isRemote && event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
            int amuletSlot = 0;
            ItemStack amuletStack = ItemStack.EMPTY;

            for(int slot : BaubleType.AMULET.getValidSlots()) {
                ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
                if(stack.getItem() == ModItems.SACRIFICIAL_AMULET) {
                    amuletSlot = slot;
                    amuletStack = stack;
                    break;
                }
            }
            if(amuletStack == ItemStack.EMPTY) return;

            if(amuletStack.getTagCompound() == null) amuletStack.setTagCompound(new NBTTagCompound());
            int kills = amuletStack.getTagCompound().getInteger("Sacrificial Amulet Charge") + 1;
            if(kills >= ModConfig.general.sacrificialCharge) {
                BaublesApi.getBaublesHandler(player).setStackInSlot(amuletSlot, ItemStack.EMPTY);
                player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 0.5F, 0.8F + player.getRNG().nextFloat() * 0.4F);
                dropSacrificeReward(player);
            }
            else amuletStack.getTagCompound().setInteger("Sacrificial Amulet Charge", kills);
        }
    }

    private static void dropSacrificeReward(EntityPlayer player) {
        ResourceLocation resourcelocation = ModLootTables.SACRIFICIAL_REWARD;

        LootTable loottable = player.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
        LootContext.Builder builder = (new LootContext.Builder((WorldServer)player.world)).withPlayer(player).withLuck(player.getLuck());

        for(ItemStack itemstack : loottable.generateLootForPools(player.getRNG(), builder.build())) {
            if(!itemstack.isEmpty()) {
                EntityItem entityitem = new EntityItem(player.world, player.posX, player.posY + 0.5, player.posZ, itemstack);
                entityitem.setDefaultPickupDelay();
                player.world.spawnEntity(entityitem);
            }
        }
    }
}