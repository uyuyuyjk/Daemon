package tco.daemon.util.energy;

import java.util.HashMap;

import net.minecraft.src.ItemStack;

public class DecomposerRecipes {

	public static class DecomposerRecipe {
		public int cost;
		public ItemStack output;
		public DecomposerRecipe(int c, ItemStack o){
			cost = c;
			output = o;
		}
		public ItemStack createOutput(){
			return output.copy();
		}
		public void handleCraft(ItemStack stack){}
	}

	public static final HashMap<Integer, DecomposerRecipe> recipes =
			new HashMap<Integer, DecomposerRecipe>();

	public static void addRecipe(int itemId, DecomposerRecipe recipe){
		recipes.put(itemId, recipe);
	}

	public static void addRecipe(int itemId, int cost, ItemStack output){
		addRecipe(itemId, new DecomposerRecipe(cost, output));
	}

	public static boolean hasRecipe(int itemId){
		return recipes.containsKey(itemId);
	}

	public static DecomposerRecipe getRecipe(int itemId){
		return recipes.get(itemId);
	}

	public static ItemStack useRecipe(int itemId){
		DecomposerRecipe recipe = recipes.get(itemId);
		ItemStack output = recipe.createOutput();
		recipe.handleCraft(output);
		return output;
	}

}
