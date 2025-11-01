package io.github.saimonovski.versechest.common.application.service.impl;

import io.github.saimonovski.versechest.common.application.service.Service;
import io.github.saimonovski.versechest.common.domain.model.Chest;
import io.github.saimonovski.versechest.paper.config.MainConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ChestService implements Service {
    private final Map<Location, Chest> chestMap = new HashMap<>();

    public void registerChest(Location location, MainConfig mainConfig){
        Chest chest = new Chest(location,mainConfig);
        this.chestMap.put(location,chest);

        Block block = location.getBlock();
        Material material = mainConfig.getChestConfiguration().getMaterial();
        block.setType(material);
    }
    @Nullable
    public Chest getChestFromLocation(Location location){
        return this.chestMap.get(location);
    }

    public boolean isChestExist(Location location) {
        return this.chestMap.containsKey(location);
    }

    public void removeChest(Location location) {
        this.chestMap.remove(location);
    }

    @Override
    public void reload() {
        this.chestMap.clear();
        load();
    }
}
