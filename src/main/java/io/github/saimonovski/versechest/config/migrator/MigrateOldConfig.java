package io.github.saimonovski.versechest.config.migrator;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.github.saimonovski.versechest.config.MainConfig;
import io.github.saimonovski.versechest.chest.Chest;
import io.github.saimonovski.versechest.chest.item.ChestItem;
import io.github.saimonovski.versechest.chest.rarity.Rarity;
import io.github.saimonovski.versechest.util.Logger;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MigrateOldConfig {

    private final YamlDocument dataFile;
    private final Map<String, Rarity> stringRarityMap;
    private final MainConfig config;
    private final Logger logger;
    public MigrateOldConfig(YamlDocument dataFile, Map<String, Rarity> stringRarityMap, MainConfig config) {
        this.dataFile = dataFile;
        this.stringRarityMap = stringRarityMap;
        this.config = config;
        this.logger = config.getLogger();
    }

    @SuppressWarnings("unchecked")
    public List<Chest> migratedChests() {
        this.logger.debug("migrating chests from old config to new config");
        List<Chest> chests = new ArrayList<>();

        for (Map<?, ?> chest : this.dataFile.getMapList("chestList", new ArrayList<>())) {
            Location chestLocation = Location.deserialize((Map<String, Object>) chest.get("location"));
            this.logger.debug("migrating chest from location: "+chestLocation);
            chests.add(new Chest(chestLocation, config));
        }
        this.logger.debug("chests migrated");
        return chests;
    }

    public Map<Rarity, List<ChestItem>> migrateChestItems() {
        this.logger.debug("migrating items from old config to new config");
        Map<Rarity, List<ChestItem>> chestItemsMap = new HashMap<>();
        Section section = this.dataFile.getSection("itemsInChests");

        if (section == null) return chestItemsMap;
        for (var rarityEntry : this.stringRarityMap.entrySet()) {
            String rarityId = rarityEntry.getKey();
            Rarity rarity = rarityEntry.getValue();
            this.logger.debug("loading rarity for: "+rarityId);
            var mapList = section.getMapList(rarityId, new ArrayList<>());
            var chestItems = loadChestItemsFromRarity(mapList,rarity);
            chestItemsMap.put(rarity,chestItems);
        }
        this.logger.debug("all items migrated");
        return chestItemsMap;
    }
    @SuppressWarnings("unchecked")
    private List<ChestItem> loadChestItemsFromRarity(List<Map<?, ?>> maps, Rarity rarity){

        List<ChestItem> chestItems = new ArrayList<>();
        for (Map<?, ?> map : maps) {
            map.remove("v");
            map.remove("version");
            this.logger.debug("loading item: "+map);
            ItemStack itemStack;
            try {
                itemStack = ItemStack.deserialize((Map<String, Object>) map);
            } catch (Exception e) {
                this.logger.warning("couldn't migrate item: \n" + map + " from data file to database. This item won't be accessible");
                this.logger.debug("loading item failed");
                continue;
            }
            this.logger.debug("item loaded");
            ChestItem chestItem = new ChestItem(itemStack,rarity);
            chestItems.add(chestItem);
        }
        return chestItems;
    }
}
