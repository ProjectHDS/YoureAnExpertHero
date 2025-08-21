package quaternary.youreanexpertharry.heck.methods;

import com.google.common.collect.ImmutableList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import quaternary.youreanexpertharry.heck.AbstractHeckMethod;
import quaternary.youreanexpertharry.heck.Heck;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCraftingMethod extends AbstractHeckMethod {
	public AbstractCraftingMethod(int count) {
		super(count);
	}

	public static Set<List<Heck.GoodItemStack>> sanitySet = new HashSet<>();
	
	@Override
	public Optional<String> getRequiredImports() {
		return Optional.empty();
	}
	
	@Override
	public String removeExistingRecipe(ItemStack output) {
		return String.format(
						"recipes.remove(%s, false);",
						stackToBracket(output)
		);
	}
	
	@Override
	public List<ItemStack> getRequiredItems() {
		return ImmutableList.of(new ItemStack(Blocks.CRAFTING_TABLE));
	}

	protected static boolean stackListSanityCheck(List<Heck.GoodItemStack> stackList) {
		if (sanitySet.contains(stackList)) {
			return false;
		}
		return true;
	}
}
