package artifacts;

import artifacts.client.render.MimicRenderer;
import artifacts.common.init.Entities;
import artifacts.common.init.Items;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class ArtifactsClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Mimic EntityRenderer
		EntityRendererRegistry.INSTANCE.register(Entities.MIMIC, (dispatcher, context) -> {
			return new MimicRenderer(dispatcher);
		});

		// ModelPredicateProvider for rendering of umbrella blocking
		FabricModelPredicateProviderRegistry.register(Items.UMBRELLA, new Identifier("blocking"), (stack, world, entity) -> {
			return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1 : 0;
		});
	}
}