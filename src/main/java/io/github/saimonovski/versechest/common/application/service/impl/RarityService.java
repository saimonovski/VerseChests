package io.github.saimonovski.versechest.common.application.service.impl;

import io.github.saimonovski.versechest.common.domain.model.Rarity;
import io.github.saimonovski.versechest.common.application.service.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RarityService implements Service {
    private final Set<Rarity> rarities = new TreeSet<>();
    private final Map<String,Rarity> stringRarityMap= new HashMap<>();

    public void registerRarity(Rarity rarity){
         this.rarities.add(rarity);
     }
     public Map<Rarity,Integer> getRarityValues(){
        return rarities.stream().collect(Collectors.toMap(rarity -> rarity, Rarity::itemAmount));
     }


    @Override
    public void reload() {
        this.rarities.clear();
        this.stringRarityMap.clear();
    }
}
