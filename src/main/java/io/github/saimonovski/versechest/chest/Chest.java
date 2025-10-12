package io.github.saimonovski.versechest.chest;

import io.github.saimonovski.versechest.config.MainConfig;
import org.bukkit.Location;

public record Chest(Location chestLocation, MainConfig config) {
   public void updateBlockMaterial(){
       chestLocation.getBlock().setType(config.getBlockCreator().getMaterial());
   }

}
