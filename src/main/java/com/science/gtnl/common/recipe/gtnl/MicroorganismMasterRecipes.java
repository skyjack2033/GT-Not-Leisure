package com.science.gtnl.common.recipe.gtnl;

import net.minecraft.item.ItemStack;

import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.GTNLRecipeMaps;

import bartworks.API.recipe.BartWorksRecipeMaps;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.GTUtility;

public class MicroorganismMasterRecipes implements IRecipePool {

    public RecipeMap<?> MMR = GTNLRecipeMaps.MicroorganismMasterRecipes;

    @Override
    public void loadRecipes() {
        for (GTRecipe oldRecipe : BartWorksRecipeMaps.bacterialVatRecipes.getAllRecipes()) {
            GTRecipe recipe = oldRecipe.copy();

            if (recipe.mSpecialItems instanceof ItemStack stack) {
                ItemStack[] oldInputs = recipe.mInputs;

                ItemStack[] newInputs = new ItemStack[oldInputs.length + 1];
                System.arraycopy(oldInputs, 0, newInputs, 0, oldInputs.length);
                newInputs[oldInputs.length] = GTUtility.copyAmountUnsafe(0, stack);

                recipe.mInputs = newInputs;
            }

            recipe.mSpecialItems = null;
            recipe.isNBTSensitive = true;
            MMR.add(recipe);
        }
    }
}
