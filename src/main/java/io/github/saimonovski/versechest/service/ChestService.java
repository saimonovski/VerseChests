package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.database.repository.ChestRepository;
import io.github.saimonovski.versechest.database.model.ChestModel;
import io.github.saimonovski.versechest.entity.ChestEntity;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChestService {
    private final ChestRepository chestRepository;
    private final ConfigService configService;
    private final Logger logger;
    private final Map<ChestKey, ChestEntity> chestEntityMap = new HashMap<>();

    public ChestService(ChestRepository chestRepository, ConfigService configService, Logger logger) {
        this.chestRepository = chestRepository;
        this.configService = configService;
        this.logger = logger;
    }

    public void loadChests() {
        this.chestEntityMap.clear();

        for (ChestModel chestModel : this.chestRepository.findAll()) {
            if (Bukkit.getWorld(chestModel.worldId()) == null) {
                this.logger.warning("Skipped chest " + chestModel.chestId() + ", world is not loaded: " + chestModel.worldId());
                continue;
            }

            ChestEntity chestEntity = chestModel.deserialize(this.configService);
            this.chestEntityMap.put(ChestKey.convertFromLocation(chestEntity.getLocation()), chestEntity);
        }

        this.logger.debug("Loaded " + this.chestEntityMap.size() + " chests from database");
    }

    @Nullable
    public ChestEntity findChest(Location location) {
        return chestEntityMap.get(ChestKey.convertFromLocation(location));
    }

    public void removeChest(ChestEntity chestEntity) {
        if (chestEntity == null) {
            return;
        }

        this.chestEntityMap.remove(ChestKey.convertFromLocation(chestEntity.getLocation()));
        this.chestRepository.remove(ChestModel.serializeChestEntity(chestEntity));
        chestEntity.getLocation().getBlock().setType(Material.AIR);
    }

    public void openChest(Player player, ChestEntity chestEntity) {
        chestEntity.putPlayerToCooldown(player);
        this.chestRepository.update(ChestModel.serializeChestEntity(chestEntity));
    }

    public ChestEntity createChest(Location location) {
        ChestEntity existingChest = findChest(location);
        if (existingChest != null) {
            return existingChest;
        }

        ChestEntity chestEntity = new ChestEntity(location, UUID.randomUUID(), this.configService);
        this.chestEntityMap.put(ChestKey.convertFromLocation(location), chestEntity);
        this.chestRepository.save(ChestModel.serializeChestEntity(chestEntity));
        return chestEntity;
    }

    private record ChestKey(UUID world, int x, int y, int z) {
        boolean matchWithLocation(Location location) {
            return this.equals(convertFromLocation(location));
        }

        static ChestKey convertFromLocation(Location location) {
            return new ChestKey(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
    }
}
