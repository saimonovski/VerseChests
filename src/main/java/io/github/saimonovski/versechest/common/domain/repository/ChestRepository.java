package io.github.saimonovski.versechest.common.domain.repository;

import io.github.saimonovski.versechest.common.domain.model.Chest;
import org.bukkit.Location;

public interface ChestRepository {
    void add(Chest chest);
    void update(Chest chest);
    void delete(Chest chest);
    void findByLocation(Location location);

}
