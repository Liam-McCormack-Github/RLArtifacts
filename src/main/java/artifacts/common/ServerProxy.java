package artifacts.common;

import artifacts.common.item.BaubleAmulet;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class ServerProxy implements IProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(new BaubleAmulet("sacrificial_amulet"));
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {

    }
}
