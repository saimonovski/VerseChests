package io.github.saimonovski.versechest.util.gui;

import io.github.saimonovski.versechest.util.gui.item.GuiItem;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public abstract class  PaginatedGui implements Gui {
    protected final Map<Integer, PageItems> pages = new HashMap<>();
    protected int page = 1;
    protected Set<Integer> contentSlots(){
        Set<Integer> contentSlots = new HashSet<>();
        for(int slot = 0; slot<size(); slot++){
            if(getNavBar() != null && getNavBar().takenSlots().contains(slot)){
                continue;
            }
            if(background() != null && background().takenSlots().contains(slot)){
                continue;
            }
            contentSlots.add(slot);
        }
        return Set.copyOf(contentSlots);
    }
   public  int currentPage(){
       return this.page;
   }
    public abstract int maxPages();

    @Override
    public void clickAction(InventoryClickEvent e) {
        int slot = e.getSlot();
        if(background() != null && background().takenSlots().contains(slot)){
            e.setCancelled(true);
            return;
        }
        if(getNavBar() != null && getNavBar().takenSlots().contains(slot)){
            e.setCancelled(true);
            getNavBar().onClick(e, this);
            return;
        }

        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));
        GuiItem guiItem = items.guiItems.get(slot);
        if (guiItem != null){
            guiItem.onClick(e);
        }

    }

    public boolean isLastPage(){
        return currentPage() == maxPages();
    }
    public boolean isFirstPage(){
        return currentPage() == 1;
   }
   @Nullable
    protected abstract  NavBar getNavBar();

    protected void saveCurrentPage() {
        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));
        for (int slot : contentSlots()) {
            ItemStack item = getInventory().getItem(slot);
            items.saveSlot(slot,item == null ? null : item.clone());
        }
        this.pages.put(currentPage(),items);
    }
    protected void renderPage() {
        getInventory().clear();
        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));
        if(background() != null) {
            background().render(this);
        }
        for (int slot : contentSlots()) {
            items.updateSlot(slot, getInventory());
        }
        if(getNavBar() != null) {
            getNavBar().render(this);
        }
    }

    @Override
    public void setItem(int slot, GuiItem guiItem) {
        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));
        items.guiItems().put(slot,guiItem);
        items.updateSlot(slot, getInventory());
    }

    public void nextPage(){
        if(isLastPage()){
            return;
        }
        saveCurrentPage();
        page++;
        renderPage();
    }
    public void previousPage(){
        if(isFirstPage()){
            return;
        }
        saveCurrentPage();
        page--;
        renderPage();
    }
    protected record PageItems(Map<Integer, GuiItem> guiItems, Map<Integer, ItemStack> itemStackSlots){
        protected PageItems(){
            this(new HashMap<>(), new HashMap<>());
        }
        void saveSlot(int slot,@Nullable ItemStack itemStack){
            if(guiItems.containsKey(slot)) return;
            itemStackSlots.put(slot, itemStack);
        }

        public void updateSlot(int slot, @NotNull Inventory inventory) {
            ItemStack item  = guiItems.containsKey(slot) ? guiItems.get(slot).getItemStack() : itemStackSlots.get(slot);
            inventory.setItem(slot,item);
        }
    }

}
