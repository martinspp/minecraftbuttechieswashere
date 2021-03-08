package techieswashere.mine;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.EffectCommand;

import java.util.EnumSet;

public class ProximityMineIdleGoal extends Goal {
    private final ProximityMineEntity mine;

    @Override
    public boolean canStart() {
        return true;
    }
    public ProximityMineIdleGoal(ProximityMineEntity mine) {
        this.mine = mine;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public void tick() {
        if(mine.config.hiddenMines)
            mine.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 5, 1, false, false));
        super.tick();
    }
    public void stop() {
        mine.removeStatusEffect(StatusEffects.INVISIBILITY);
    }
}
