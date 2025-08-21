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

public class EliteTableMethod extends AbstractHeckMethod {
    public EliteTableMethod() {
        super(49);
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.extendedcrafting.TableCrafting;");
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        return ""; // no existing recipe
    }

    @Override
    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        Random random =  new Random();
        // addShapeless(tier, <output>, [<input>, <input>]);
        // addShaped(tier, <output>, [[<>, <>, <>], [<>, <>, <>], [<>, <>, <>]]);
        if (random.nextBoolean()) {
            return String.format("TableCrafting.addShapeless(3, %s, [%s]);",
                    stackToBracket(output),
                    stacksToBracketedList(inputs)
            );
        }
        return String.format("TableCrafting.addShaped(3, %s, [%s,\n %s,\n %s,\n %s,\n %s,\n %s,\n %s]);",
                stackToBracket(output),
                stacksToBracketedList(inputs.subList(0, 7)),
                stacksToBracketedList(inputs.subList(7, 14)),
                stacksToBracketedList(inputs.subList(14, 21)),
                stacksToBracketedList(inputs.subList(21, 28)),
                stacksToBracketedList(inputs.subList(28, 35)),
                stacksToBracketedList(inputs.subList(35, 42)),
                stacksToBracketedList(inputs.subList(42, 49))
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.blockEliteTable, 1, 0), new ItemStack(ModBlocks.blockAdvancedTable, 1, 0));
    }

    @Override
    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        int inputSize = Heck.random.nextInt(49) + 1;
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

            YoureAnExpertHarry.LOGGER.info("Sanity-checking Elite Table recipes.");
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
