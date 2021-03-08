package techieswashere.mine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import techieswashere.techieswashere;

@Environment(EnvType.CLIENT)
public class ProximityMineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /*
         * Registers our Cube Entity's renderer, which provides a model and texture for the entity.
         *
         * Entity Renderers can also manipulate the model before it renders based on entity context (EndermanEntityRenderer#render).
         */
        EntityRendererRegistry.INSTANCE.register(techieswashere.PROXIMITY_MINE, (dispatcher, context) -> {
            return new ProximityMineRenderer(dispatcher);
        });
    }
}
