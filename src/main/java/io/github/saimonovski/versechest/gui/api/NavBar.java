package io.github.saimonovski.versechest.gui.api;

import io.github.saimonovski.versechest.gui.api.item.GuiItem;
import io.github.saimonovski.versechest.gui.api.item.WidgetItem;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface NavBar {
    WidgetItem nextPageItem();
    WidgetItem previousPageItem();
    WidgetItem closeItem();

    int nextPageItemSlot();
    int previousPageItemSlot();
    int closeItemSlot();

    void render(PaginatedGui paginatedGui);

    List<Integer> takenSlots();

    Consumer<InventoryClickEvent> getClickAction();
}
