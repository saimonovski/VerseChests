package io.github.saimonovski.versechest.common.domain.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.github.saimonovski.versechest.paper.config.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;

@DatabaseTable(tableName = "versechest_chest")
public class Chest {
    @DatabaseField(unique = true)
    ChestLocation chestLocation;

    @DatabaseField(unique = true)
    private String serverId;


   public void updateBlockMaterial(final MainConfig.BlockCreator blockCreator){
        Location chestLocation = this.chestLocation.getLocation();
        if(chestLocation == null) return;
       chestLocation.getBlock().setType(blockCreator.getMaterial());

   }

}
