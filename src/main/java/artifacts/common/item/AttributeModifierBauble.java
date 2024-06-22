package artifacts.common.item;

import artifacts.common.ModConfig;
import artifacts.common.util.BaubleHelper;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AttributeModifierBauble extends BaubleBase {

    private final Set<ExtendedAttributeModifier> attributeModifiers;

    public AttributeModifierBauble(String name, BaubleType type, ExtendedAttributeModifier... attributeModifiers) {
        super(name, type);
        this.attributeModifiers = new HashSet<>(Arrays.asList(attributeModifiers));
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        super.onEquipped(stack, player);
        if (player instanceof EntityPlayer) {
            applyModifiers(null, (EntityPlayer) player);
        }
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        super.onUnequipped(stack, player);
        if (player instanceof EntityPlayer) {
            applyModifiers(stack, (EntityPlayer) player);
        }
    }

    private void applyModifiers(@Nullable ItemStack excludedStack, EntityPlayer player) {
        IBaublesItemHandler baublesHandler = BaublesApi.getBaublesHandler(player);
        Set<ExtendedAttributeModifier> modifiers = new HashSet<>();

        for (int slot : BaubleType.TRINKET.getValidSlots()) {
            ItemStack stack = baublesHandler.getStackInSlot(slot);
            if (stack.getItem() instanceof AttributeModifierBauble && stack != excludedStack) {
                modifiers.addAll(((AttributeModifierBauble) stack.getItem()).attributeModifiers);
            }
        }

        modifiers.retainAll(attributeModifiers);

        for (ExtendedAttributeModifier modifier : attributeModifiers) {
            IAttributeInstance instance = player.getAttributeMap().getAttributeInstance(modifier.affectedAttribute);
            if (instance.getModifier(modifier.id) != null) {
                instance.removeModifier(modifier.id);
            }
        }

        //Allow stacking the same type of baubles, since you can stack different types
        int amount = BaubleHelper.getAmountBaubleEquipped(player, this);

        for (ExtendedAttributeModifier modifier : modifiers) {
            player.getAttributeMap().getAttributeInstance(modifier.affectedAttribute).applyModifier(new AttributeModifier(modifier.id, modifier.name, modifier.getValue() * amount, modifier.getOperation()));
        }
    }

    public static class ExtendedAttributeModifier {

        public final UUID id;
        public final String name;
        public final IAttribute affectedAttribute;

        public ExtendedAttributeModifier(UUID id, String nameIn, IAttribute affectedAttribute) {
            this.id = id;
            this.name = nameIn;
            this.affectedAttribute = affectedAttribute;
        }

        //Yea, not as simply expandable as before, but atleast it will actually use the config values
        public double getValue() {
            if (this.affectedAttribute == SharedMonsterAttributes.ATTACK_DAMAGE)
                return ModConfig.general.attackDamageBoost;
            if (this.affectedAttribute == SharedMonsterAttributes.ATTACK_SPEED)
                return ModConfig.general.attackSpeedBoost;
            if (this.affectedAttribute == SharedMonsterAttributes.LUCK) return ModConfig.general.luckBoost;
            if (this.affectedAttribute == SharedMonsterAttributes.MOVEMENT_SPEED) return ModConfig.general.speedBoost;
            return 0;
        }

        public int getOperation() {
            if (this.affectedAttribute == SharedMonsterAttributes.ATTACK_DAMAGE)
                return ModConfig.general.attackDamageOperation;
            if (this.affectedAttribute == SharedMonsterAttributes.ATTACK_SPEED)
                return ModConfig.general.attackSpeedOperation;
            if (this.affectedAttribute == SharedMonsterAttributes.LUCK) return ModConfig.general.luckOperation;
            if (this.affectedAttribute == SharedMonsterAttributes.MOVEMENT_SPEED)
                return ModConfig.general.speedOperation;
            return 0;
        }
    }
}