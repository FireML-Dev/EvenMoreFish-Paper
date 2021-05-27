package com.oheers.fish.competition.reward.gui;

import com.oheers.fish.EvenMoreFish;
import com.oheers.fish.FishUtils;
import com.oheers.fish.competition.reward.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RewardGUI {

    Inventory inv;
    Player viewer;
    int page;

    private final Material blank = Material.GRAY_STAINED_GLASS_PANE;
    private final Material pageSwitch = Material.SPECTRAL_ARROW;
    private final Material nothing = Material.BLACK_STAINED_GLASS_PANE;

    public RewardGUI(Player viewer) {
        this.inv = Bukkit.createInventory(null, 18, "RewardGUI");
        this.viewer = viewer;
        this.page = 1;
        EvenMoreFish.rGuis.add(this);
        init();
    }

    public void init() {
        // Setting empty slots to a nice fancy filler
        cleanBar();
        // The amount of reward markers to be shown
        int quant = EvenMoreFish.rewards.size();
        System.out.println("size: " + EvenMoreFish.rewards.size());

        // detects if all the slots in that page are filled or not
        if (page*9 > quant) {
            // the quantity of items to be shown on this specific page
            int pageQuant = quant%9;
            // used to neaten up the values in the gui by putting the last uneven number on the bottom row
            if (pageQuant%2 != 0) {
                // keeps count of the working level
                int lvlCount = 0;
                // formatted: for(centre alignment; how many to go along by; incrementing by 1)
                for (int i= (9-pageQuant)/2; i-((9-pageQuant)/2) < pageQuant; i++) {
                    lvlCount++;
                    genItem((page-1)*9 + lvlCount, i);
                }
            } else {
                // checks if values actually exist since "0" is even according to java
                if (quant != 0) {
                    // keeps count of the working level
                    int lvlCount = 0;
                    // using 10 over 9 since there's one item in the bottom row that "quant" is still accounting for
                    for (int i= (10-pageQuant)/2; i-((10-pageQuant)/2) < pageQuant-1; i++) {
                        lvlCount++;
                        genItem((page-1)*9 + lvlCount, i);
                    }
                    lvlCount++;
                    genItem((page-1)*9 + lvlCount, 13);
                }
            }
        } else {
            for (int i=0; i<=8; i++) {
                genItem((page-1)*9 + (i+1), i);
            }
        }

        // if there's a page after it shows the forwardswitch
        if (quant/9.0 > page) {
            inv.setItem(16, FishUtils.setScrollItem(true));
        } else {
            inv.setItem(16, new ItemStack(blank));
        }

        // if it's not the first page, it shows the backswitch
        if (page != 1) {
            inv.setItem(10, FishUtils.setScrollItem(false));
        } else {
            inv.setItem(10, new ItemStack(blank));
        }
    }

    // Just creating a default item now that shows slot id
    private void genItem(Integer position, int slot) {
        ItemStack it = new ItemStack(EvenMoreFish.mainConfig.getRewardGUIItem(position));
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(EvenMoreFish.mainConfig.getRewardGUITitle(position));
        List<String> rewards = new ArrayList<>();

        for (Reward r : EvenMoreFish.rewards.get(position)) {
            String reward = r.format();
            if (reward != null) rewards.add(reward);
        }

        meta.setLore(rewards);
        it.setItemMeta(meta);
        inv.setItem(slot, it);
    }

    private void cleanBar() {
        for (int i=0; i<18; i++) {
            this.inv.setItem(i, new ItemStack(nothing));
        }
    }

    // Opens the inventory to the player. The GUI must have been constructed beforehand
    public void display() {
        this.viewer.openInventory(this.inv);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}