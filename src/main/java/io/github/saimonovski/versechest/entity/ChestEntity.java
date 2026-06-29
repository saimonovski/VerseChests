package io.github.saimonovski.versechest.entity;

import io.github.saimonovski.versechest.service.ChestCooldownService;
import io.github.saimonovski.versechest.service.ConfigService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.UUID;

public class ChestEntity {
    private final ChestCooldownService chestCooldownService;
    private final Location location;
    private final UUID chestId;
    private final ConfigService configService;


    public ChestEntity( Location location, UUID chestId, ConfigService configService) {
        this.chestCooldownService = new ChestCooldownService();
        this.location = location;
        this.chestId = chestId;
        this.configService = configService;
    }
    public void updateBLock(){
        Block block = this.location.getBlock();
        Material material = this.configService.getChestConfig().chestMaterial();

        block.setType(material);
    }
    public void putPlayerToCooldown(Player player){
        int duration = this.configService.getChestConfig().chestCooldownDuration();
        ChestCooldownService.TimeUnit timeUnit = this.configService.getChestConfig().chestCooldownUnit();
        UUID id = player.getUniqueId();

        this.chestCooldownService.setCooldown(id,duration,timeUnit);
    }

    public ChestCooldownService getChestCooldownService(){
        return this.chestCooldownService;
    }
    public UUID getChestId(){
        return this.chestId;
    }
    public Location getLocation(){
        return this.location;
    }
}
