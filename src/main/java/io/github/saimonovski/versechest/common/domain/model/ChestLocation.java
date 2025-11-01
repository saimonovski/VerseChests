package io.github.saimonovski.versechest.common.domain.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import javax.annotation.Nullable;

@DatabaseTable(tableName = "versechest_location")
public class ChestLocation {

    @DatabaseField(unique = true)
    private int x;

    @DatabaseField(unique = true)
    private int y;

    @DatabaseField(unique = true)
    private int z;

    @DatabaseField(unique = true)
    private String worldName;

    @Nullable
    public Location getLocation(){
        World world = Bukkit.getWorld(worldName);
        if(world == null) return null;
        return new Location(world, this.x,this.y,this.z);
    }

}
