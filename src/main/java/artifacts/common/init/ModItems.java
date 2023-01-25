package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.client.model.ModelPanicNecklace;
import artifacts.client.model.ModelUltimatePendant;
import artifacts.common.item.*;
import baubles.api.BaubleType;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static final BaubleBase SHOCK_PENDANT = new BaubleAmulet("shock_pendant", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/shock_pendant.png")).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase FLAME_PENDANT = new BaubleAmulet("flame_pendant", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/flame_pendant.png")).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase THORN_PENDANT = new BaubleAmulet("thorn_pendant", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/thorn_pendant.png")).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase PANIC_NECKLACE = new BaubleAmulet("panic_necklace", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/panic_necklace.png")).setModel(new ModelPanicNecklace()).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase ULTIMATE_PENDANT = new BaubleAmulet("ultimate_pendant", new ResourceLocation(Artifacts.MODID, "textures/entity/layer/ultimate_pendant.png")).setModel(new ModelUltimatePendant()).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase WHOOPIE_CUSHION = new BaubleWhoopieCushion().setEquipSound(ModSoundEvents.FART);
    public static final BaubleBase BOTTLED_CLOUD = new BaubleBottledCloud("bottled_cloud", false).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH);
    public static final BaubleBase BOTTLED_FART = new BaubleBottledCloud("bottled_fart", true).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH);
    public static final BaubleBase MAGMA_STONE = new BaubleBase("magma_stone", BaubleType.RING).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase FERAL_CLAWS = new AttributeModifierBauble("feral_claws", BaubleType.RING, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_SPEED).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    public static final BaubleBase POWER_GLOVE = new AttributeModifierBauble("power_glove", BaubleType.RING, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_DAMAGE).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase MECHANICAL_GLOVE = new AttributeModifierBauble("mechanical_glove", BaubleType.RING, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_SPEED, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_DAMAGE).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
    public static final BaubleBase FIRE_GAUNTLET = new AttributeModifierBauble("fire_gauntlet", BaubleType.RING, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_SPEED, AttributeModifierBauble.ExtendedAttributeModifier.ATTACK_DAMAGE).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
    public static final BaubleBase DRINKING_HAT = new BaubleBase("drinking_hat", BaubleType.HEAD).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL);
    public static final BaubleBase STAR_CLOAK = new BaubleStarCloak().setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase POCKET_PISTON = new BaubleBase("pocket_piston", BaubleType.RING).setEquipSound(SoundEvents.BLOCK_PISTON_EXTEND, SoundEvents.BLOCK_PISTON_CONTRACT);

    public static void registerItems(IForgeRegistry<Item> registry) {

        registry.registerAll(
                SHOCK_PENDANT,
                FLAME_PENDANT,
                THORN_PENDANT,
                ULTIMATE_PENDANT,
                PANIC_NECKLACE,
                WHOOPIE_CUSHION,
                BOTTLED_CLOUD,
                BOTTLED_FART,
                MAGMA_STONE,
                FERAL_CLAWS,
                POWER_GLOVE,
                MECHANICAL_GLOVE,
                FIRE_GAUNTLET,
                DRINKING_HAT,
                STAR_CLOAK,
                POCKET_PISTON
        );
    }

    public static void registerModels() {
        SHOCK_PENDANT.registerModel();
        FLAME_PENDANT.registerModel();
        THORN_PENDANT.registerModel();
        ULTIMATE_PENDANT.registerModel();
        PANIC_NECKLACE.registerModel();
        WHOOPIE_CUSHION.registerModel();
        BOTTLED_CLOUD.registerModel();
        BOTTLED_FART.registerModel();
        MAGMA_STONE.registerModel();
        FERAL_CLAWS.registerModel();
        POWER_GLOVE.registerModel();
        MECHANICAL_GLOVE.registerModel();
        FIRE_GAUNTLET.registerModel();
        DRINKING_HAT.registerModel();
        STAR_CLOAK.registerModel();
        POCKET_PISTON.registerModel();
    }
}
