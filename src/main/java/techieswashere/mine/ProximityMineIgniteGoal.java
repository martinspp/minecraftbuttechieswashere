package techieswashere.mine;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.CreeperEntity;
import org.apache.logging.log4j.Level;

import java.util.EnumSet;

public class ProximityMineIgniteGoal extends Goal {
    private final ProximityMineEntity mine;
    private LivingEntity target;

    public ProximityMineIgniteGoal(ProximityMineEntity mine) {
        this.mine = mine;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }
    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.mine.getTarget();
        return this.mine.getFuseSpeed() > 0 || livingEntity != null && this.mine.squaredDistanceTo(livingEntity) < mine.config.fusePrimeDistance;
    }
    public void start() {
        this.mine.getNavigation().stop();
        this.target = this.mine.getTarget();
    }
    public void tick() {
        if (this.target == null) {
            this.mine.setFuseSpeed(-1);
        } else if (this.mine.squaredDistanceTo(this.target) > 9.0D) {
            this.mine.setFuseSpeed(-1);
        } else {
            this.mine.setFuseSpeed(1);
        }
    }
}
