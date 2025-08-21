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

public class CompressorMethod extends AbstractHeckMethod {
    public CompressorMethod() {
        super(2);
    }

    @Override
    public Optional<String> getRequiredImports() {
        return Optional.of("import mods.extendedcrafting.CompressionCrafting;");
    }

    @Override
    public String removeExistingRecipe(ItemStack output) {
        return String.format(
                "CompressionCrafting.remove(%s);\n",
                stackToBracket(output)
        );
    }

    @Override
    public String writeZenscript(String recipeName, ItemStack output, List<ItemStack> inputs) {
        Random random = new Random();
        // addRecipe(<output>, <input>, inputCount, <catalyst>, rfCost);
        return String.format("CompressionCrafting.addRecipe(%s, %s %s, %s, %s);",
                stackToBracket(output),
                stackToBracket(inputs.get(0)),
                random.nextInt(114514),
                stackToBracket(inputs.get(1)),
                random.nextInt(114514)
        );
    }

    @Override
    public List<ItemStack> getRequiredItems() {
        return ImmutableList.of(new ItemStack(ModBlocks.blockCompressor, 1, 0));
    }

    @Override
    public Pair<Pair<List<ItemStack>, String>, Boolean> chooseInputs(HeckData allHeck, Heck.GoodItemStack outputGood, boolean base) throws Heckception {
        int inputSize = Heck.random.nextInt(2) + 1;
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

            YoureAnExpertHarry.LOGGER.info("Sanity-checking Compressor recipes.");
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
