package io.github.saimonovski.versechest.common.domain.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import io.github.saimonovski.versechest.common.application.service.impl.RarityService;
import org.bukkit.inventory.ItemStack;

@DatabaseTable(tableName = "versechest_chest_item")
    public class ChestItem {
        @DatabaseField(canBeNull = false, columnName = "itemstack", dataType = DataType.BYTE_ARRAY)
        private byte[] itemStackAsBytes;
        @DatabaseField(canBeNull = false)
        private String rarity;
        @DatabaseField(canBeNull = false)
        int dropChance;

        public ItemStack getItemStack(){
            return ItemStack.deserializeBytes(this.itemStackAsBytes);
        }
    public void setDropChance(int dropChance){
        this.dropChance = dropChance;
    }

    public Rarity getDropRarity(final RarityService rarityService) {
        return rarityService.findRarity(rarity); //todo
    }

    public int getDropChance() {
        return this.dropChance;
    }
}
