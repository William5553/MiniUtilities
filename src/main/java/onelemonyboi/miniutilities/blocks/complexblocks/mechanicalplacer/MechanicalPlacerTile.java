package onelemonyboi.miniutilities.blocks.complexblocks.mechanicalplacer;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import onelemonyboi.lemonlib.annotations.SaveInNBT;
import onelemonyboi.lemonlib.blocks.tile.TileBase;
import onelemonyboi.lemonlib.identifiers.RenderInfoIdentifier;
import onelemonyboi.lemonlib.trait.tile.TileTraits;
import onelemonyboi.miniutilities.blocks.complexblocks.mechanicalminer.MechanicalMinerTile;
import onelemonyboi.miniutilities.init.TEList;
import onelemonyboi.miniutilities.trait.TileBehaviors;

import java.util.ArrayList;
import java.util.List;

public class MechanicalPlacerTile extends TileBase implements MenuProvider, RenderInfoIdentifier {
    public static int slots = 9;

    // 1: Always on
    // 2: Redstone to Enable
    // 3: Redstone to Disable
    @SaveInNBT(key = "RedstoneMode")
    public Integer redstonemode;
    public Integer timer;
    @SaveInNBT(key = "WaitTime")
    public Integer waittime;

    public MechanicalPlacerTile(BlockPos pos, BlockState state) {
        super(TEList.MechanicalPlacerTile.get(), pos, state, TileBehaviors.mechanicalPlacer);
        this.redstonemode = 1;
        this.timer = 0;
        this.waittime = 20;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.miniutilities.mechanical_placer");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new MechanicalPlacerContainer(id, player, ContainerLevelAccess.create(getLevel(), getBlockPos()));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MechanicalPlacerTile tile) {
        level.sendBlockUpdated(pos, state, state, 2);
        tile.timer++;
        if (tile.timer < tile.waittime) {return;}
        tile.timer = 0;
        if (tile.redstonemode == 1){
            tile.blockPlacer();
        }
        else if (level.hasNeighborSignal(tile.getBlockPos()) && tile.redstonemode == 2){
            tile.blockPlacer();
        }
        else if (!level.hasNeighborSignal(tile.getBlockPos()) && tile.redstonemode == 3){
            tile.blockPlacer();
        }
    }

    protected void blockPlacer() {
        BlockPos blockPos = this.getBlockPos().relative(this.getBlockState().getValue(BlockStateProperties.FACING));
        Boolean flag = false;
        for (int j = 0; j < slots && !flag; ++j) {
            ItemStack itemStack1 = getBehaviour().getRequired(TileTraits.ItemTrait.class).getItemStackHandler().getStackInSlot(j);
            Item item1 = itemStack1.getItem();
            if (!itemStack1.isEmpty() && item1 instanceof net.minecraft.world.item.BlockItem && level.isEmptyBlock(blockPos)) {
                level.setBlockAndUpdate(blockPos, ((BlockItem) item1).getBlock().defaultBlockState());
                itemStack1.shrink(1);
                flag = true;
            }
        }
        this.setChanged();
    }

    @Override
    public List<MutableComponent> getInfo() {
        List<MutableComponent> output = new ArrayList<>();

        output.add(this.getBlockState().getBlock().getName());
        output.add(Component.literal(""));
        switch (this.redstonemode) {
            case 1:
                output.add(Component.translatable("text.miniutilities.redstonemodeone"));
                break;
            case 2:
                output.add(Component.translatable("text.miniutilities.redstonemodetwo"));
                break;
            case 3:
                output.add(Component.translatable("text.miniutilities.redstonemodethree"));
                break;
        }
        output.add(Component.translatable("text.miniutilities.waittime").append(this.waittime.toString() + " ticks"));
        return output;
    }
}
