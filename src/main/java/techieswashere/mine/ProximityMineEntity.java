package techieswashere.mine;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AnimalEntity;
import techieswashere.ModConfig;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.Level;
import techieswashere.techieswashere;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("EntityConstructor")
public class ProximityMineEntity extends MobEntity {

    private static final TrackedData<Integer> FUSE_SPEED;
    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    private int lastFuseTime;
    private int currentFuseTime = 1;

    private int fuseTime = config.fuseTimer;
    private int explosionRadius = config.explosionRadius;

    protected void initGoals() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        this.goalSelector.add(1, new ProximityMineIgniteGoal(this));
        this.goalSelector.add(2,new ProximityMineIdleGoal(this));
        this.targetSelector.add(1, new FollowTargetGoal(this, PlayerEntity.class, false));
        if(config.explodeOnHostiles)
            this.targetSelector.add(2, new FollowTargetGoal(this, HostileEntity.class, false));
        if(config.explodeOnNeutrals)
            this.targetSelector.add(2, new FollowTargetGoal(this, AnimalEntity.class, false));
    }

    public ProximityMineEntity(EntityType<? extends MobEntity> entityType, World world){
        super(entityType,world);
    }
    protected void initDataTracker() {

        super.initDataTracker();
        this.dataTracker.startTracking(FUSE_SPEED, -1);
    }
    public void tick() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;

            int i = this.getFuseSpeed();
            if (i > 0 && this.currentFuseTime == 0) {
                this.playSound(techieswashere.PROXIMITY_MINE_PRIMED_EVENT, 0.8F, 1.0F);
            }

            this.currentFuseTime += i;

            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }
    public int getFuseTime() {
        return fuseTime;
    }

    private void explode() {
        if (!this.world.isClient) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius, destructionType);
            this.remove();
            this.spawnEffectsCloud();
        }
    }

    @Override
    public void onDeath(DamageSource source) {
        if(getTarget() != null && !source.isExplosive() && source.getAttacker() == getTarget()){

            this.playSound(techieswashere.PROXIMITY_MINE_DEATH_EVENT, 1.0F, 1.0F);
            techieswashere.log(Level.INFO,getTarget().toString());
            ItemEntity goldNugget = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.GOLD_NUGGET,RandomUtils.nextInt(config.goldMin,config.goldMax)));
            world.spawnEntity(goldNugget);
        }

        super.onDeath(source);
    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            Iterator var3 = collection.iterator();

            while(var3.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.world.spawnEntity(areaEffectCloudEntity);
        }

    }
    public int getFuseSpeed() {
        return (Integer)this.dataTracker.get(FUSE_SPEED);
    }

    public void setFuseSpeed(int fuseSpeed) {
        this.dataTracker.set(FUSE_SPEED, fuseSpeed);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {

        return true;
    }

    static {
        FUSE_SPEED = DataTracker.registerData(ProximityMineEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

}
