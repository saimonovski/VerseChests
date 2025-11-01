package io.github.saimonovski.versechest.common.domain.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
@DatabaseTable(tableName = "versechest_player")
public class ChestPlayer {

    @DatabaseField(unique = true, id = true)
    private UUID playerId;

    @DatabaseField
    private int chestOpenedCount;

    public ChestCooldown setPlayerToCooldown(int cooldownTime, Chest chest){
        ChestCooldown chestCooldown = new ChestCooldown();
        chestCooldown
                .setPlayer(this)
                .setTime(LocalDateTime.now().plusSeconds(cooldownTime))
                .setChest(chest);

        return chestCooldown;

    }

    private boolean isStillCooldown(LocalDateTime cooldownEndDateTime){
        LocalDateTime now = LocalDateTime.now();
        Duration between = Duration.between(now,cooldownEndDateTime);
        return between.getSeconds() <= 0;
    }

    public void addPlayerOpenedChest() {
        this.chestOpenedCount++;
    }

    public int getOpenedChests() {
        return this.chestOpenedCount;
    }


}
