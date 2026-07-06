package io.github.saimonovski.versechest.gui.api;

import io.github.saimonovski.versechest.gui.api.item.GuiItem;
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
            if(getNavBar().takenSlots().contains(slot)){
                continue;
            }
            if(background().takenSlots().contains(slot)){
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
        if(background().takenSlots().contains(slot)){
            e.setCancelled(true);
            return;
        }
        if(getNavBar().takenSlots().contains(slot)){
            getNavBar().getClickAction().accept(e);
            return;
        }

        PageItems items = pages.get(currentPage());
        GuiItem guiItem = items.guiItems.get(slot);
        if (guiItem != null){
            guiItem.getClickAction().accept(e);
            return;
        }

    }

    public boolean isLastPage(){
        return currentPage() == maxPages();
    }
    public boolean isFirstPage(){
        return currentPage() == 1;
   }
    protected abstract  NavBar getNavBar();

    protected void saveCurrentPage() {
        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));


        int index = 0;
        for (int slot : contentSlots()) {
            ItemStack item = getInventory().getItem(slot);
            items.saveSlot(slot,item == null ? null : item.clone());
        }
        this.pages.put(currentPage(),items);
    }
    protected void renderPage() {
       getInventory().clear();
        PageItems items = pages.computeIfAbsent(page, ignored -> new PageItems(new HashMap<>(), new HashMap<>()));
        for (int slot : contentSlots()) {
            items.updateSlot(slot, getInventory());
        }
        getNavBar().render(this);
    }



    private void nextPage(){
        if(isLastPage()){
            return;
        }
        saveCurrentPage();
        page++;
        renderPage();
    }
    private void previousPage(){
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
