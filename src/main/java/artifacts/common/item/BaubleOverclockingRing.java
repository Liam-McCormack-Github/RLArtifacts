package artifacts.common.item;

import artifacts.common.util.BaubleHelper;
import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class BaubleOverclockingRing extends AttributeModifierBauble {
    public List<String> statusEffects;


    public BaubleOverclockingRing(String name, BaubleType type, List<String> statusEffects, ExtendedAttributeModifier... attributeModifiers) {
        super(name, type, attributeModifiers);
        this.statusEffects = statusEffects;
    }

    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        BaubleHelper.negatePotion(player, statusEffects);
    }


}