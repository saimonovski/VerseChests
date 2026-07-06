package io.github.saimonovski.versechest.gui.api.item;

import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public interface WidgetItem extends GuiItem{
    @Override
   default Consumer<InventoryClickEvent> getClickAction(){
        return e -> {};
    }

}
