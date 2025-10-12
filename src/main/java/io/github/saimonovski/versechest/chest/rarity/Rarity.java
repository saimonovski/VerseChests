package io.github.saimonovski.versechest.chest.rarity;

import io.github.saimonovski.versechest.config.RarityConfig;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Random;

public record Rarity(RarityConfig conf, String rarityName) implements Comparable<Rarity> {

    public int itemAmount(){
        int minItems =  conf.getMinItems();
        int maxItems = conf.getMaxItems();

        Random random = new Random();
        return random.nextInt(minItems,maxItems);
    }


    @Override
    public int compareTo(@NotNull Rarity rarity) {
        return Comparator
                .comparingInt((Rarity r) -> r.conf.getChanceToDrop())
                .thenComparingInt(r -> r.conf.getMinItems())
                .thenComparingInt(r -> r.conf.getMaxItems())
                .compare(this, rarity);
    }
}
