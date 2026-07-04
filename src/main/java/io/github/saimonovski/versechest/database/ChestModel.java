package io.github.saimonovski.versechest.database;

import io.github.saimonovski.versechest.entity.ChestEntity;
import io.github.saimonovski.versechest.service.ChestCooldownService;
import io.github.saimonovski.versechest.service.ConfigService;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record ChestModel(UUID chestId, int x, int y, int z, UUID worldId, Map<UUID, Instant> serializedCooldowns) {
    public static ChestModel serializeChestEntity(ChestEntity chest) {
        Location location = chest.getLocation();
        UUID chestId = chest.getChestId();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        UUID worldId = location.getWorld().getUID();
        var serializedCooldowns = chest.getChestCooldownService().serializeCooldowns();
        return new ChestModel(chestId, x, y, z, worldId, serializedCooldowns);
    }

    public ChestEntity deserialize(ConfigService configService) {
        Location location = new Location(Bukkit.getWorld(worldId), x, y, z);
        ChestCooldownService cooldownService = ChestCooldownService.deserialize(serializedCooldowns);
        return new ChestEntity(location, chestId, configService, cooldownService);
    }

}