package io.github.saimonovski.versechest.test.gui;

import io.github.saimonovski.versechest.test.GuiTest;
import io.github.saimonovski.versechest.util.gui.NavBar;
import io.github.saimonovski.versechest.util.gui.PaginatedGui;
import io.github.saimonovski.versechest.util.gui.item.WidgetItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SimpleNavBar implements NavBar {

    private static final WidgetItem NEXT_PREVIOUS_PAGE_ITEM = new WidgetItem();
    private static final WidgetItem CLOSE_PAGE_ITEM = new WidgetItem();

    @Override
    public WidgetItem nextPageItem() {
        NEXT_PREVIOUS_PAGE_ITEM.setItemStack(new ItemStack(Material.ARROW));
        return NEXT_PREVIOUS_PAGE_ITEM;
    }

    @Override
    public WidgetItem previousPageItem() {
        NEXT_PREVIOUS_PAGE_ITEM.setItemStack(new ItemStack(Material.ARROW));
        return NEXT_PREVIOUS_PAGE_ITEM;
    }

    @Override
    public WidgetItem closeItem() {
        CLOSE_PAGE_ITEM.setItemStack(new ItemStack(Material.BARRIER));
        return CLOSE_PAGE_ITEM;
    }

    @Override
    public int nextPageItemSlot() {
        return 23;
    }

    @Override
    public int previousPageItemSlot() {
        return 21;
    }

    @Override
    public int closeItemSlot() {
        return 22;
    }

    @Override
    public void render(PaginatedGui paginatedGui) {
        GuiTest.plugin.getLogger().info("rendered navbar for page: "+paginatedGui.currentPage());
        paginatedGui.setItem(nextPageItemSlot(), nextPageItem());
        paginatedGui.setItem(previousPageItemSlot(), previousPageItem());
        paginatedGui.setItem(closeItemSlot(), closeItem());
    }

    @Override
    public List<Integer> takenSlots() {
        return List.of(nextPageItemSlot(), previousPageItemSlot(), closeItemSlot(), 18,19,20,24,25,26);
    }

    @Override
    public void onClick(InventoryClickEvent e, PaginatedGui paginatedGui) {

        int slot = e.getSlot();
        switch (slot){
            case 23 -> paginatedGui.nextPage();
            case 21 -> paginatedGui.previousPage();
            case 22 -> e.getWhoClicked().closeInventory();
        }
    }
}
