package io.github.saimonovski.versechest.gui.api;

import io.github.saimonovski.versechest.gui.api.item.WidgetItem;

import java.util.List;
import java.util.Map;

public interface Background {
    Map<Integer, WidgetItem> backgroundItems();

    default List<Integer> takenSlots(){
        return List.copyOf(backgroundItems().keySet());
    }
}
