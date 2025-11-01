package io.github.saimonovski.versechest.common.domain.repository;

import io.github.saimonovski.versechest.common.domain.model.Chest;
import io.github.saimonovski.versechest.common.domain.model.ChestCooldown;
import io.github.saimonovski.versechest.common.domain.model.ChestPlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CooldownRepository {
    void save(ChestCooldown chestCooldown);
    void  update(ChestCooldown chestCooldown);
    void delete(ChestCooldown chestCooldown);

    List<Map<ChestPlayer,ChestCooldown>> findByChest(Chest chest);
    ChestCooldown findChestCooldown(ChestPlayer chestPlayer, Chest chest);
}
