package artifacts;

import artifacts.common.IProxy;
import artifacts.common.init.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Artifacts.MODID, name = Artifacts.MODNAME, version = Artifacts.VERSION, dependencies = "required-after:baubles")
public class Artifacts {

    public static final String MODID = "artifacts";
    public static final String MODNAME = "RLArtifacts";
    public static final String VERSION = "1.1.2";

    public static final CreativeTabs ARTIFACTS_TAB = new ArtifactTab();

    @Mod.Instance(MODID)
    public static Artifacts instance;

    @SidedProxy(serverSide = "artifacts.common.ServerProxy", clientSide = "artifacts.client.ClientProxy")
    public static IProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
        ModEntities.init();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init();
        ModNetworkHandler.init();
        ModLootTables.init();
        ModWorldGen.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            ModSoundEvents.registerSoundEvents(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.registerItems(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            ModRecipes.registerRecipes(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItemModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }
    }

    public static class ArtifactTab extends CreativeTabs {

        public ArtifactTab() {
            super(MODID + ".creativetab");
        }

        @Override
        public ItemStack createIcon() { return new ItemStack(ModItems.PANIC_NECKLACE); }
    }
}
