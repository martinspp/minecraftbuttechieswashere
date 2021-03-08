// Made with Blockbench 3.8.0
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

package techieswashere.mine;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import techieswashere.mine.ProximityMineEntity;
import techieswashere.mine.ProximityMineRenderer;

public class ProximityMineModel extends EntityModel<ProximityMineEntity> {
    private final ModelPart spikes;
    private final ModelPart bb_main;

    public ProximityMineModel() {
        textureWidth = 16;
        textureHeight = 16;

        spikes = new ModelPart(this,0,0);

        spikes.setTextureOffset(0, 0).addCuboid(2.0F, -6.0F, 4.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(-3.0F, -7.0F, -4.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(3.0F, -7.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(0.0F, -3.0F, 5.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(-6.0F, -2.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(-6.0F, -4.0F, 2.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(4.0F, -6.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(4.0F, -3.0F, -6.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(1.0F, -7.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(0, 0).addCuboid(-5.0F, -6.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        bb_main = new ModelPart(this,0,0);

        bb_main.setTextureOffset(0, 0).addCuboid(-5.0F, -5.0F, -5.0F, 10.0F, 5.0F, 10.0F, 0.0F, false);
        bb_main.setTextureOffset(0, 0).addCuboid(-4.0F, -6.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
    }

    @Override
    public void setAngles(ProximityMineEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.translate(0, 1.5, 0);
        spikes.render(matrices, vertices, light, overlay);
        bb_main.render(matrices, vertices, light, overlay);
    }
}