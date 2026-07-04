package io.github.saimonovski.versechest.service;

import io.github.saimonovski.versechest.entity.ChestEntity;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChestService {
    private final Map<ChestKey, ChestEntity> chestEntityMap = new HashMap<>();

    @Nullable
    public ChestEntity findChest(Location location) {
        return chestEntityMap.get(ChestKey.convertFromLocation(location));
    }

    public void removeChest(ChestEntity chestEntity) {
        //todo
    }


    public void openChest(Player player, ChestEntity chestEntity) {

        chestEntity.putPlayerToCooldown(player);

        ///todo send a message
    }

    public ChestEntity createChest(Location location) {
        //todo
    }

    private record ChestKey(UUID world, int x, int y, int z) {
        boolean matchWithLocation(Location location) {
            return false;
        }

        static ChestKey convertFromLocation(Location location) {
            return new ChestKey(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
    }
}
