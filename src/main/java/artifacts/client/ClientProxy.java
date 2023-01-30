package artifacts.client;

import artifacts.Artifacts;
import artifacts.client.model.layer.*;
import artifacts.client.renderer.RenderHallowStar;
import artifacts.client.renderer.RenderMimic;
import artifacts.common.IProxy;
import artifacts.common.entity.EntityHallowStar;
import artifacts.common.entity.EntityMimic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Map;

@SuppressWarnings("unused")
public class ClientProxy implements IProxy {

    @Override
    public void preInit() {
        RenderingRegistry.registerEntityRenderingHandler(EntityMimic.class, RenderMimic.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityHallowStar.class, RenderHallowStar.FACTORY);
    }

    @Override
    public void init() {
        addRenderLayers();
    }

    @Override
    public void postInit() {

    }

    @Override
    public void registerItemRenderer(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Artifacts.MODID + ":" + name, "inventory"));
    }

    private static void addRenderLayers() {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        addLayersToSkin(skinMap.get("default"), false);
        addLayersToSkin(skinMap.get("slim"), true);
    }

    private static void addLayersToSkin(RenderPlayer renderPlayer, boolean slim) {
        renderPlayer.addLayer(new LayerGloves(slim, renderPlayer));
        renderPlayer.addLayer(new LayerDrinkingHat(renderPlayer));
        renderPlayer.addLayer(new LayerAmulet(renderPlayer));
        renderPlayer.addLayer(new LayerBelt(renderPlayer));
        renderPlayer.addLayer(new LayerCloak(renderPlayer));
    }
}
