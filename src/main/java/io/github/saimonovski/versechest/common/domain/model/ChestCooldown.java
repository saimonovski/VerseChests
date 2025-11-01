package io.github.saimonovski.versechest.common.domain.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@DatabaseTable(tableName = "versechest_cooldowns")
public class ChestCooldown {

    @DatabaseField(canBeNull = false)
    private Chest chest;
    @DatabaseField(canBeNull = false)
    private ChestPlayer chestPlayer;
    @DatabaseField(canBeNull = false)
    private LocalDateTime cooldownEndTime;

    public boolean isStillOnCooldown(){
        //todo
    }

    public ChestCooldown setPlayer(ChestPlayer player) {
    this.chestPlayer = player;
    return this;
    }

    public ChestCooldown setTime(LocalDateTime localDateTime) {
        this.cooldownEndTime = localDateTime;
        return this;
    }

    public ChestCooldown setChest(Chest chest) {
        this.chest = chest;
        return this;
    }
}
