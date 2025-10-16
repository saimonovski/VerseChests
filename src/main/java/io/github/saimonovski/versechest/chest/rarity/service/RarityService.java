package io.github.saimonovski.versechest.chest.rarity.service;

import io.github.saimonovski.versechest.chest.item.service.ChestItemService;
import io.github.saimonovski.versechest.chest.rarity.Rarity;

import java.util.*;
import java.util.stream.Collectors;

public class RarityService {
    private final Set<Rarity> rarities = new TreeSet<>();
    private final Map<String,Rarity> stringRarityMap= new HashMap<>();

    public void registerRarity(Rarity rarity){
         this.rarities.add(rarity);
     }
     public Map<Rarity,Integer> getRarityValues(){
        return rarities.stream().collect(Collectors.toMap(rarity -> rarity, Rarity::itemAmount));
     }




}
