package onelemonyboi.miniutilities.items.enchantments;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import onelemonyboi.miniutilities.ModRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoltenHeadLootModifier extends LootModifier {
    public static final Supplier<Codec<MoltenHeadLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, MoltenHeadLootModifier::new)));



    public MoltenHeadLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> smeltedLoot = new ObjectArrayList<>();
        for (ItemStack item : generatedLoot) {
            Optional<SmeltingRecipe> recipe = context.getLevel().getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(item), context.getLevel());
            smeltedLoot.add(recipe.map(a -> a.getResultItem(RegistryAccess.EMPTY)).orElse(item));
        }
        return smeltedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}