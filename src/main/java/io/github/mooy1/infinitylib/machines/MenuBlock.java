package io.github.mooy1.infinitylib.machines;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.Material;
import io.github.thebusybiscuit.slimefun5.libraries.xseries.XMaterial;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun5.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun5.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun5.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun5.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun5.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun5.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

@ParametersAreNonnullByDefault
public abstract class MenuBlock extends SlimefunItem {

    public static final ItemStack PROCESSING_ITEM = CustomItemStack.create(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial(), "&aProcessing...");
    public static final ItemStack NO_ENERGY_ITEM = CustomItemStack.create(XMaterial.RED_STAINED_GLASS_PANE.parseMaterial(), "&cNot enough energy!");
    public static final ItemStack IDLE_ITEM = CustomItemStack.create(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), "&8Idle");
    public static final ItemStack NO_ROOM_ITEM = CustomItemStack.create(XMaterial.ORANGE_STAINED_GLASS_PANE.parseMaterial(), "&6Not enough room!");
    public static final ItemStack OUTPUT_BORDER = CustomItemStack.create(ChestMenuUtils.getOutputSlotTexture(), "&6Output");
    public static final ItemStack INPUT_BORDER = CustomItemStack.create(ChestMenuUtils.getInputSlotTexture(), "&9Input");
    public static final ItemStack BACKGROUND_ITEM = ChestMenuUtils.getBackground();

    public MenuBlock(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                BlockMenu menu = BlockStorage.getInventory(e.getBlock());
                if (menu != null) {
                    onBreak(e, menu);
                }
            }

        }, new BlockPlaceHandler(false) {

            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                onPlace(e, e.getBlockPlaced());
            }

        });
    }

    @Override
    public final void postRegister() {
        new MenuBlockPreset(this);
    }

    protected abstract void setup(BlockMenuPreset preset);

    @Nonnull
    protected final int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        switch (flow) {
            case INSERT:
                return getInputSlots(menu, item);
            case WITHDRAW:
                return getOutputSlots();
            default:
                return new int[0];
        }
    }

    protected int[] getInputSlots(DirtyChestMenu menu, ItemStack item) {
        return getInputSlots();
    }

    protected abstract int[] getInputSlots();

    protected abstract int[] getOutputSlots();

    protected void onNewInstance(BlockMenu menu, Block b) {

    }

    protected void onBreak(BlockBreakEvent e, BlockMenu menu) {
        Location l = menu.getLocation();
        menu.dropItems(l, getInputSlots());
        menu.dropItems(l, getOutputSlots());
    }

    protected void onPlace(BlockPlaceEvent e, Block b) {

    }

    /**
     * Whether {@code p} may open this block's menu right now. Override to gate opening on a condition only
     * the block knows, such as a bespoke multiblock not being fully built. Returning false stops the menu
     * being created at all, rather than opening it and closing it again a tick later.
     *
     * @param b
     *            The block being opened
     * @param p
     *            The player opening it
     *
     * @return Whether the menu may open
     */
    protected boolean canOpen(Block b, Player p) {
        return true;
    }

    /**
     * Why {@link #canOpen} refused, shown to {@code p} in place of the generic "not permitted to access
     * this block". Override alongside {@link #canOpen} when the refusal is something the player can act
     * on rather than a permission problem.
     *
     * @param b
     *            The block being opened
     * @param p
     *            The player opening it
     *
     * @return The message to show, or {@code null} for the generic permission one
     */
    @Nullable
    protected String getAccessDenialMessage(Block b, Player p) {
        return null;
    }

}
