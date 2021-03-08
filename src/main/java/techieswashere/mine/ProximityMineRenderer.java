package techieswashere.mine;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ProximityMineRenderer extends MobEntityRenderer<ProximityMineEntity, ProximityMineModel> {
    public ProximityMineRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ProximityMineModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(ProximityMineEntity entity) {
        return new Identifier("techieswashere", "textures/entity/techieswashere/proximitymine.png");
    }
}
