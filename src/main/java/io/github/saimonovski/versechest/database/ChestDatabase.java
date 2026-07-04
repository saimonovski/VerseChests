package io.github.saimonovski.versechest.database;

import java.util.UUID;

public interface ChestDatabase extends Database {
    void save(ChestModel chestModel);

    void remove(ChestModel chestModel);

    void update(ChestModel chestModel);

    void find(UUID chestId);

    void find(int x, int y, int z, UUID worldId);

}
