package io.github.saimonovski.versechest.common.domain.repository;

import io.github.saimonovski.versechest.common.domain.model.ChestPlayer;

import java.util.UUID;

public interface ChestPlayerRepository {
    void save(ChestPlayer player);
    void update(ChestPlayer player);
    void delete(ChestPlayer player);

    void findById(UUID uuid);

}
