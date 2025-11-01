package io.github.saimonovski.versechest.paper.config;

public class RarityConfig {
    private final int minItems, maxItems, maxItemsInChest, minItemsInChest, chanceToDrop;

    public RarityConfig(MainConfig mainConfig, int minItems, int maxItems, int chanceToDrop) {
        this.maxItemsInChest = mainConfig.getChestConfiguration().getMaxItems();
        this.minItemsInChest = mainConfig.getChestConfiguration().getMinItems();
        this.chanceToDrop = chanceToDrop;

        this.minItems = minItems;
        this.maxItems = maxItems;

    }

    public int getMinItems() {
        return minItems;
    }


    public int getMaxItems() {
        return maxItems;
    }

    public int getMaxItemsInChest() {
        return maxItemsInChest;
    }

    public int getMinItemsInChest() {
        return minItemsInChest;
    }

    public int getChanceToDrop() {
        return chanceToDrop;
    }
}
