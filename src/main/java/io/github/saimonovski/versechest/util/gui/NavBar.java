package io.github.saimonovski.versechest.util.gui;

import io.github.saimonovski.versechest.util.gui.item.WidgetItem;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public interface NavBar {
    WidgetItem nextPageItem();
    WidgetItem previousPageItem();
    WidgetItem closeItem();

    int nextPageItemSlot();
    int previousPageItemSlot();
    int closeItemSlot();

    void render(PaginatedGui paginatedGui);

    List<Integer> takenSlots();



    void onClick(InventoryClickEvent e, PaginatedGui paginatedGui);
}
