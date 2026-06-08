package com.science.gtnl.loader.compat;

import com.Nxer.TwistSpaceTechnology.common.recipeMap.GTCMRecipe;
import com.Nxer.TwistSpaceTechnology.recipe.machineRecipe.expanded.CircuitAssemblyLineWithoutImprintRecipePool;
import com.science.gtnl.common.recipe.gregtech.CircuitAssemblerRecipes;
import com.science.gtnl.common.recipe.gregtech.CircuitAssemblyLineRecipes;
import com.science.gtnl.utils.enums.ModList;

import cpw.mods.fml.common.Optional;

public class BartWorksCircuitRecipeLoader {

    public void loadRecipes() {
        new CircuitAssemblerRecipes().loadRecipes();
        new CircuitAssemblyLineRecipes().loadRecipes();

        if (ModList.TwistSpaceTechnology.isModLoaded()) {
            loadTSTAdvCircuitAssemblyLineRecipes();
        }
    }

    @Optional.Method(modid = "TwistSpaceTechnology")
    public static void loadTSTAdvCircuitAssemblyLineRecipes() {
        GTCMRecipe.advCircuitAssemblyLineRecipes.getBackend()
            .clearRecipes();
        CircuitAssemblyLineWithoutImprintRecipePool.loadRecipes();
        System.out.println("[GTNL] Register TwistSpaceTechnology AdvCircuitAssemblyLine recipes");
    }
}
