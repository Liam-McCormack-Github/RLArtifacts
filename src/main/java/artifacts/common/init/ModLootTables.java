package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.loot.functions.AddRandomEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;

public class ModLootTables {

    public static void init() {
        LootFunctionManager.registerFunction(new AddRandomEffect.Serializer());
    }

    public static final ResourceLocation MIMIC_UNDERGROUND = new ResourceLocation(Artifacts.MODID, "mimic_underground");
    public static final ResourceLocation CHEST_UNDERGROUND = new ResourceLocation(Artifacts.MODID, "chest_underground");
    public static final ResourceLocation SACRIFICIAL_REWARD = new ResourceLocation(Artifacts.MODID, "sacrificial_reward");

}