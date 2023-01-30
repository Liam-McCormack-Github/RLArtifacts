package artifacts.common.init;

import artifacts.common.ModConfig;
import artifacts.common.worldgen.WorldGenUndergroundChest;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class ModWorldGen {

    public static void init() {
        GameRegistry.registerWorldGenerator(new WorldGenUndergroundChest(Arrays.asList(ModConfig.general.undergroundChestDimensions)), 0);
    }
}