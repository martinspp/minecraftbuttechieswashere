package techieswashere;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "techieswashere")
public class ModConfig implements ConfigData {
    public boolean hiddenMines = true;
    public int explosionRadius = 3;
    public int fuseTimer = 20;
    public float fusePrimeDistance = 9.0f;

    public int goldMin = 5;
    public int goldMax = 10;

    public boolean explodeOnHostiles = true;
    public boolean explodeOnNeutrals = false;

    public int mineHP = 2;

    public int spawnWeight = 300;
    public int minGroupSize = 1;
    public int maxGroupSize = 2;
}
