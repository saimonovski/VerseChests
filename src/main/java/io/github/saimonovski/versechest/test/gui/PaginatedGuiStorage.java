package io.github.saimonovski.versechest.test.gui;

import io.github.saimonovski.versechest.util.ChatUtil;
import io.github.saimonovski.versechest.util.gui.Background;
import io.github.saimonovski.versechest.util.gui.NavBar;
import io.github.saimonovski.versechest.util.gui.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class PaginatedGuiStorage extends PaginatedGui {
    private final NavBar navBar = new SimpleNavBar();
    private Inventory inventory = null;
    @Override
    public int maxPages() {
        return 10;
    }

    @Override
    protected NavBar getNavBar() {
        return this.navBar;
    }

    @Override
    public void open(Player player) {
        if(inventory == null){
            this.inventory = Bukkit.createInventory(this,size(), ChatUtil.fix("<red> Test Paginated Gui"));
        }
        renderPage();
        player.openInventory(inventory);
    }

    @Override
    public void open(Player... players) {
    //ignored for tests
    }

    @Override
    public Background background() {
        return null;
    }

    @Override
    public void inventoryCloseAction(InventoryCloseEvent e) {
//ignored
    }

    @Override
    public void inventoryOpenAction(InventoryOpenEvent e) {
//ignored
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
