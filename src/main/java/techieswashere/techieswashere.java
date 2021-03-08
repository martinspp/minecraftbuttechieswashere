package techieswashere;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import techieswashere.mine.ProximityMineEntity;

import java.util.List;

import static net.fabricmc.fabric.api.biome.v1.BiomeModifications.addSpawn;

public class techieswashere implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "techieswashere";
    public static final String MOD_NAME = "Minecraft but Techies was here";

    private static final Identifier PROXIMITY_MINE_LOOT_TABLE_ID = new Identifier("techieswashere", "proximity_mine");

    public static final EntityType<ProximityMineEntity> PROXIMITY_MINE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("techieswashere", "proximity_mine"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ProximityMineEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.25f)).build()
    );
    public static final Identifier PROXIMITY_MINE_PRIMED = new Identifier("techieswashere:proximity_mine_primed");
    public static final Identifier PROXIMITY_MINE_EXPLODE = new Identifier("techieswashere:proximity_mine_explode");
    public static final Identifier PROXIMITY_MINE_DEATH = new Identifier("techieswashere:proximity_mine_death");
    public static SoundEvent PROXIMITY_MINE_PRIMED_EVENT = new SoundEvent(PROXIMITY_MINE_PRIMED);
    public static SoundEvent PROXIMITY_MINE_EXPLODE_EVENT = new SoundEvent(PROXIMITY_MINE_EXPLODE);
    public static SoundEvent PROXIMITY_MINE_DEATH_EVENT = new SoundEvent(PROXIMITY_MINE_DEATH);
    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        Registry.register(Registry.SOUND_EVENT,techieswashere.PROXIMITY_MINE_PRIMED,PROXIMITY_MINE_PRIMED_EVENT);
        Registry.register(Registry.SOUND_EVENT,techieswashere.PROXIMITY_MINE_EXPLODE,PROXIMITY_MINE_EXPLODE_EVENT);
        Registry.register(Registry.SOUND_EVENT,techieswashere.PROXIMITY_MINE_DEATH,PROXIMITY_MINE_DEATH_EVENT);

        FabricDefaultAttributeRegistry.register(PROXIMITY_MINE, ProximityMineEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH,config.mineHP)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 500));

        BiomeModifications.create(new Identifier("techieswashere:lotsofmines"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInOverworld().and(context -> {
                            Biome biome = context.getBiome();
                        return biome.getCategory() != Biome.Category.OCEAN  ;
                        }),
                        (selection, context) -> {
                            context.getSpawnSettings().addSpawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(
                                    PROXIMITY_MINE, config.spawnWeight , config.minGroupSize,config.maxGroupSize
                            ));
                        });


    }



    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}