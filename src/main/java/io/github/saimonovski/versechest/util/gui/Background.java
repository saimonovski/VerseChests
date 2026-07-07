package io.github.saimonovski.versechest.util.gui;

import io.github.saimonovski.versechest.util.gui.item.WidgetItem;

import java.util.List;
import java.util.Map;

public interface Background {
    Map<Integer, WidgetItem> backgroundItems();

    default List<Integer> takenSlots(){
        return List.copyOf(backgroundItems().keySet());
    }

    default void render(PaginatedGui paginatedGui){
        for (var entry : backgroundItems().entrySet()){
            int slot = entry.getKey();
            WidgetItem item = entry.getValue();

            paginatedGui.setItem(slot,item);
        }
    }
}
