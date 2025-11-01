package io.github.saimonovski.versechest.common.application.service.impl;

import io.github.saimonovski.versechest.common.application.service.Service;
import io.github.saimonovski.versechest.common.domain.model.ChestItem;
import io.github.saimonovski.versechest.common.domain.model.Rarity;

import java.util.*;

public class ChestItemService implements Service {
    private final Map<Rarity, List<ChestItem>> rarityChestItemMap = new TreeMap<>();

    public Set<ChestItem> randomizeChestItems(Map<Rarity,Integer> rarityIntegerMap, int totalAmount){
        Set<ChestItem> chestItems = new HashSet<>();
        rarityIntegerMap.forEach((rarity, totalRarityAmount) -> {
            int currentRarityAmount = 0;
            Random random = new Random();
            while (currentRarityAmount != totalRarityAmount && chestItems.size() < totalAmount){

                List<ChestItem> list = this.rarityChestItemMap.get(rarity);
                int index = random.nextInt(0, list.size()-1);
                ChestItem item = list.get(index);
                if(chestItems.contains(item) || item == null ) continue;

                chestItems.add(item);
                currentRarityAmount = currentRarityAmount+1;
            }

        });
    return chestItems;
    }

    @Override
    public void reload() {
        this.rarityChestItemMap.clear();
    }
}
