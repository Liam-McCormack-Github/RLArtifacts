package artifacts.common.init;

import artifacts.common.item.*;
import baubles.api.BaubleType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class ModItems {

    public static final BaubleBase SHOCK_PENDANT = new BaubleAmulet("shock_pendant").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase FLAME_PENDANT = new BaubleAmulet("flame_pendant").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase THORN_PENDANT = new BaubleAmulet("thorn_pendant").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase PANIC_NECKLACE = new BaubleAmulet("panic_necklace").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase ULTIMATE_PENDANT = new BaubleAmulet("ultimate_pendant").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase WHOOPIE_CUSHION = new BaubleWhoopieCushion().setEquipSound(ModSoundEvents.FART);
    public static final BaubleBase BOTTLED_CLOUD = new BaubleBottledCloud("bottled_cloud", false).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH);
    public static final BaubleBase BOTTLED_FART = new BaubleBottledCloud("bottled_fart", true).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH);
    public static final BaubleBase MAGMA_STONE = new BaubleBase("magma_stone", BaubleType.TRINKET).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase FERAL_CLAWS = new AttributeModifierBauble("feral_claws", BaubleType.TRINKET, new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("529a8894-1739-4d9b-834c-06a178bd9235"), "Feral Claws Speed", SharedMonsterAttributes.ATTACK_SPEED)).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC);
    public static final BaubleBase POWER_GLOVE = new AttributeModifierBauble("power_glove", BaubleType.TRINKET, new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("cd3a3d56-09ef-475b-b673-5587e3d7806d"), "Power Glove Damage", SharedMonsterAttributes.ATTACK_DAMAGE)).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase MECHANICAL_GLOVE = new AttributeModifierBauble("mechanical_glove", BaubleType.TRINKET, new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("66132757-820e-41f2-ab1c-4bf13173736e"), "Mechanical Glove Speed", SharedMonsterAttributes.ATTACK_SPEED), new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("a82c0055-bafd-46fc-92bc-e7e2e06e0dfb"), "Mechanical Glove Damage", SharedMonsterAttributes.ATTACK_DAMAGE)).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
    public static final BaubleBase FIRE_GAUNTLET = new AttributeModifierBauble("fire_gauntlet", BaubleType.TRINKET, new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("0e2a81e5-8b5a-47a8-883c-7d4d971147ac"), "Fire Gauntlet Speed", SharedMonsterAttributes.ATTACK_SPEED), new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("715e2422-d2e9-473c-aaf9-2d194b284818"), "Fire Gauntlet Damage", SharedMonsterAttributes.ATTACK_DAMAGE)).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
    public static final BaubleBase DRINKING_HAT = new BaubleBase("drinking_hat", BaubleType.TRINKET).setEquipSound(SoundEvents.ITEM_BOTTLE_FILL);
    public static final BaubleBase STAR_CLOAK = new BaubleStarCloak().setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase POCKET_PISTON = new BaubleBase("pocket_piston", BaubleType.TRINKET).setEquipSound(SoundEvents.BLOCK_PISTON_EXTEND, SoundEvents.BLOCK_PISTON_CONTRACT);
    public static final BaubleBase ANTIDOTE_VESSEL = new BaubleAntidoteVessel("antidote_vessel").setEquipSound(SoundEvents.ITEM_BOTTLE_FILL, SoundEvents.ITEM_BOTTLE_EMPTY);
    public static final BaubleBase BUBBLE_WRAP = new BaubleBubbleWrap().setEquipSound(ModSoundEvents.BUBBLE_WRAP);
    public static final BaubleBase SACRIFICIAL_AMULET = new BaubleAmulet("sacrificial_amulet").setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_CHAIN);
    public static final BaubleBase LUCKY_CLOVER = new AttributeModifierBauble("lucky_clover", BaubleType.TRINKET, new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("aebc0384-0d15-44f7-8ebc-c41f251f84dd"), "Lucky Clover Luck", SharedMonsterAttributes.LUCK));

    public static final BaubleBase SHINY_RED_BALLOON = new BaublePotionEffect("shiny_red_balloon", BaubleType.TRINKET, MobEffects.JUMP_BOOST, 1).setEquipSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.5F);
    public static final BaubleBase SNORKEL = new BaublePotionEffect("snorkel", BaubleType.TRINKET, MobEffects.WATER_BREATHING, 0).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);
    public static final BaubleBase LUCKY_HORSESHOE = new BaubleBase("lucky_horseshoe", BaubleType.TRINKET).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_GOLD);
    public static final BaubleBase COBALT_SHIELD = new BaubleBase("cobalt_shield", BaubleType.TRINKET).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON);
    public static final BaubleBase OBSIDIAN_SKULL = new BaubleObsidianSkull();
    public static final BaubleBase NIGHT_VISION_GOGGLES = new BaublePotionEffect("night_vision_goggles", BaubleType.TRINKET, MobEffects.NIGHT_VISION, 0, 300).setEquipSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);

    public static final BaubleBase FORBIDDEN_FRUIT = new BaubleStatusEffectShield("forbidden_fruit", BaubleType.TRINKET, Arrays.asList("minecraft:nausea", "minecraft:hunger"));
    public static final BaubleBase VITAMINS = new BaubleStatusEffectShield("vitamins", BaubleType.TRINKET, Arrays.asList("minecraft:mining_fatigue", "minecraft:weakness"));
    public static final BaubleBase SUNGLASSES = new BaubleStatusEffectShield("sunglasses", BaubleType.TRINKET, Collections.singletonList("minecraft:blindness"));
    public static final BaubleBase SHULKER_HEART = new BaubleStatusEffectShield("shulker_heart", BaubleType.TRINKET, Collections.singletonList("minecraft:levitation"));
    public static final BaubleBase BLACK_DRAGON_SCALE = new BaubleStatusEffectShield("black_dragon_scale", BaubleType.TRINKET, Collections.singletonList("minecraft:wither"));
    public static final BaubleBase BEZOAR = new BaubleStatusEffectShield("bezoar", BaubleType.TRINKET, Collections.singletonList("minecraft:poison"));
    public static final BaubleBase MIXED_COLOUR_DRAGON_SCALE = new BaubleStatusEffectShield("mixed_colour_dragon_scale", BaubleType.TRINKET, Arrays.asList("minecraft:wither", "minecraft:poison"));

    public static final BaubleBase OVERCLOKING_RING = new BaubleOverclockingRing("overcloking_ring", BaubleType.TRINKET, Collections.singletonList("minecraft:slowness"), new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549"), "Overclocking Ring Speed", SharedMonsterAttributes.MOVEMENT_SPEED));
    public static final BaubleBase RING_OF_FREE_ACTION = new BaubleOverclockingRing("ring_of_free_action", BaubleType.TRINKET, Arrays.asList("minecraft:slowness", "minecraft:levitation"), new AttributeModifierBauble.ExtendedAttributeModifier(UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549"), "Free Action Ring Speed", SharedMonsterAttributes.MOVEMENT_SPEED));

    /*
    sunglasses
    shulker_heart
    mixed_colour_dragon_scale
    ring_of_free_action
    overcloking_ring
    forbidden_fruit
    vitamins
    black_dragon_scale
    bezoar
     */


    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(SHOCK_PENDANT, FLAME_PENDANT, THORN_PENDANT, ULTIMATE_PENDANT, PANIC_NECKLACE, WHOOPIE_CUSHION, BOTTLED_CLOUD, BOTTLED_FART, MAGMA_STONE, FERAL_CLAWS, POWER_GLOVE, MECHANICAL_GLOVE, FIRE_GAUNTLET, DRINKING_HAT, STAR_CLOAK, POCKET_PISTON, ANTIDOTE_VESSEL, BUBBLE_WRAP, SACRIFICIAL_AMULET, LUCKY_CLOVER, SHINY_RED_BALLOON, SNORKEL, LUCKY_HORSESHOE, COBALT_SHIELD, OBSIDIAN_SKULL, NIGHT_VISION_GOGGLES,

                FORBIDDEN_FRUIT, VITAMINS, SUNGLASSES, SHULKER_HEART, BLACK_DRAGON_SCALE, BEZOAR, MIXED_COLOUR_DRAGON_SCALE,

                OVERCLOKING_RING, RING_OF_FREE_ACTION

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
        ANTIDOTE_VESSEL.registerModel();
        BUBBLE_WRAP.registerModel();
        SACRIFICIAL_AMULET.registerModel();
        LUCKY_CLOVER.registerModel();

        SHINY_RED_BALLOON.registerModel();
        SNORKEL.registerModel();
        LUCKY_HORSESHOE.registerModel();
        COBALT_SHIELD.registerModel();
        OBSIDIAN_SKULL.registerModel();
        NIGHT_VISION_GOGGLES.registerModel();

        FORBIDDEN_FRUIT.registerModel();
        VITAMINS.registerModel();
        SUNGLASSES.registerModel();
        SHULKER_HEART.registerModel();
        BLACK_DRAGON_SCALE.registerModel();
        BEZOAR.registerModel();
        MIXED_COLOUR_DRAGON_SCALE.registerModel();

        OVERCLOKING_RING.registerModel();
        RING_OF_FREE_ACTION.registerModel();
    }
}
