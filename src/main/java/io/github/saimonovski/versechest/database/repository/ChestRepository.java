package io.github.saimonovski.versechest.database.repository;

import io.github.saimonovski.versechest.database.model.ChestModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChestRepository {
    void save(ChestModel chestModel);

    void remove(ChestModel chestModel);

    void update(ChestModel chestModel);

    Optional<ChestModel> find(UUID chestId);

    Optional<ChestModel> find(int x, int y, int z, UUID worldId);

    List<ChestModel> findAll();
}
