package quaternary.youreanexpertharry.modules.extendedcrafting;

import com.blakebr0.extendedcrafting.block.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import quaternary.youreanexpertharry.YoureAnExpertHarry;
import quaternary.youreanexpertharry.etc.ShapelessStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;
import quaternary.youreanexpertharry.heck.HeckData;
import quaternary.youreanexpertharry.heck.Heckception;

import java.util.*;

public class CombinationMethod extends AbstractHeckMethod {
    public CombinationMethod() {
        super(28);
    }

    public static Set<HashSet<ShapelessStack>> sanitySet = new HashSet<>();

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.extendedcrafting.CombinationCrafting;");
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        return ""; // no existing recipe
    }

    @Override
    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        Random random = new Random();
        // addRecipe(<output>, rfCost, <input>, [<pedestalItem>, <pedestalItem>]);
        // addRecipe(<minecraft:stick> * 10, 10000, <minecraft:diamond>, [<ore:ingotIron>, <minecraft:stick>]);
        return String.format("CombinationCrafting.addRecipe(%s, %s, %s, [%s]);",
                stackToBracket(output),
                random.nextInt(1145141919),
                stacksToBracketedList(inputs).indexOf(0),
                stacksToBracketedList(inputs.subList(1, inputs.size()))
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.blockCraftingCore, 1, 0), new ItemStack(ModBlocks.blockPedestal, 1 , 0));
    }

    @Override
    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        int inputSize = Heck.random.nextInt(28) + 1;
        List<ItemStack> recipeStacks = new ArrayList<>(inputSize);
        HashSet<ShapelessStack> shapelessSet = new HashSet<>();
        boolean sanity = false;

        while (!(sanity)) {
            recipeStacks.clear();
            shapelessSet.clear();
            for(int a = 0; a < inputSize; a++) {
                recipeStacks.add(Heck.chooseItem(allHeck, outputGood, base));
            }
            recipeStacks.forEach(is -> ShapelessStack.shapelessAdd(shapelessSet, is));

            YoureAnExpertHarry.LOGGER.info("Sanity-checking Combination recipes.");
            YoureAnExpertHarry.LOGGER.info(recipeStacks.toString());
            sanity = shapelessSanityCheck(shapelessSet);
        }
        YoureAnExpertHarry.LOGGER.info("Sanity succeeded");
        sanitySet.add(shapelessSet);
        if (allHeck.currentLevel != 0) addItemsToTask(recipeStacks, allHeck, Heck.settings);
        String b = writeZenscript("", outputGood.actualStack, recipeStacks);

        return new MutablePair<>(new MutablePair<>(recipeStacks, b), true);
    }
}
