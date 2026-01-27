package com.science.gtnl.common.recipe.gregtech;

import com.dreammaster.gthandler.CustomItemList;
import com.science.gtnl.api.IRecipePool;
import com.science.gtnl.common.material.GTNLMaterials;
import com.science.gtnl.utils.recipes.EyeOfHarmonyRecipeFactory;

import bartworks.system.material.WerkstoffLoader;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GTModHandler;
import gtPlusPlus.core.material.MaterialsElements;
import gtnhlanth.common.register.WerkstoffMaterialPool;
import tectech.util.FluidStackLong;
import tectech.util.ItemStackLong;

public class EyeOfHarmonyRecipes implements IRecipePool {

    @Override
    public void loadRecipes() {
        EyeOfHarmonyRecipeFactory.addCustomRecipeEntry(
            GTModHandler.getModItem(Mods.EternalSingularity.ID, "combined_singularity", 1, 15),
            new ItemStackLong[] { new ItemStackLong(WerkstoffMaterialPool.Gangue.get(OrePrefixes.dust, 1), 1519274962L),
                new ItemStackLong(WerkstoffLoader.Bismutite.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.VanadioOxyDravit.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Arsenopyrite.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Ferberite.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Loellingit.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Roquesit.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.RedZircon.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Fayalit.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.DescloiziteCUVO4.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.FuchsitAL.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.FuchsitCR.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.BArTiMaEuSNeK.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Tiberium.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(GTNLMaterials.Technetium.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Zirconium.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Ruthenium.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Rhodium.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffLoader.Atheneite.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(MaterialsElements.getInstance().HAFNIUM.getDust(1), 1L),
                new ItemStackLong(WerkstoffMaterialPool.Hafnia.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(WerkstoffMaterialPool.Iodine.get(OrePrefixes.dust, 1), 1L),
                new ItemStackLong(Materials.Draconium.getDust(1), 1L),
                new ItemStackLong(Materials.Beryllium.getDust(1), 1L), new ItemStackLong(Materials.Iron.getDust(1), 1L),
                new ItemStackLong(Materials.Zinc.getDust(1), 1L), new ItemStackLong(Materials.Samarium.getDust(1), 1L),
                new ItemStackLong(Materials.Neutronium.getDust(1), 1L),
                new ItemStackLong(Materials.Rutile.getDust(1), 1L), new ItemStackLong(Materials.Ardite.getDust(1), 1L),
                new ItemStackLong(Materials.Ledox.getDust(1), 1L),
                new ItemStackLong(Materials.InfinityCatalyst.getDust(1), 1L),
                new ItemStackLong(Materials.MysteriousCrystal.getDust(1), 1L),
                new ItemStackLong(Materials.Rubracium.getDust(1), 1L),
                new ItemStackLong(Materials.Vulcanite.getDust(1), 1L),
                new ItemStackLong(Materials.Diamond.getDust(1), 1L),
                new ItemStackLong(Materials.GreenSapphire.getDust(1), 1L),
                new ItemStackLong(Materials.Olivine.getDust(1), 1L),
                new ItemStackLong(Materials.NetherStar.getDust(1), 1L),
                new ItemStackLong(Materials.Topaz.getDust(1), 1L),
                new ItemStackLong(Materials.Tanzanite.getDust(1), 1L), new ItemStackLong(Materials.Opal.getDust(1), 1L),
                new ItemStackLong(Materials.BlueTopaz.getDust(1), 1L),
                new ItemStackLong(Materials.Forcicium.getDust(1), 1L),
                new ItemStackLong(Materials.Vinteum.getDust(1), 1L),
                new ItemStackLong(Materials.TricalciumPhosphate.getDust(1), 1L),
                new ItemStackLong(Materials.InfusedAir.getDust(1), 1L),
                new ItemStackLong(Materials.InfusedFire.getDust(1), 1L),
                new ItemStackLong(Materials.InfusedEarth.getDust(1), 1L),
                new ItemStackLong(Materials.InfusedWater.getDust(1), 1L),
                new ItemStackLong(Materials.InfusedEntropy.getDust(1), 1L),
                new ItemStackLong(Materials.SiliconDioxide.getDust(1), 1L),
                new ItemStackLong(MaterialsElements.getInstance().RHENIUM.getDust(1), 1L),
                new ItemStackLong(MaterialsElements.getInstance().THALLIUM.getDust(1), 1L),
                new ItemStackLong(MaterialsElements.getInstance().GERMANIUM.getDust(1), 1L),
                new ItemStackLong(MaterialsElements.getInstance().SELENIUM.getDust(1), 1L),
                new ItemStackLong(CustomItemList.ChargedCertusQuartzDust.get(1), 1L),
                new ItemStackLong(Materials.Salt.getDust(1), 1L), new ItemStackLong(Materials.Mica.getDust(1), 1L),
                new ItemStackLong(Materials.Bastnasite.getDust(1), 1L),
                new ItemStackLong(Materials.Lepidolite.getDust(1), 1L),
                new ItemStackLong(Materials.Realgar.getDust(1), 1L),
                new ItemStackLong(Materials.Redstone.getDust(1), 1L),
                new ItemStackLong(Materials.CosmicNeutronium.getDust(1), 1L),
                new ItemStackLong(Materials.QuartzSand.getDust(1), 1L),
                new ItemStackLong(Materials.Asbestos.getDust(1), 1L),
                new ItemStackLong(Materials.Vyroxeres.getDust(1), 1L),
                new ItemStackLong(Materials.Orichalcum.getDust(1), 1L),
                new ItemStackLong(Materials.Glowstone.getDust(1), 1L), },
            new FluidStackLong[] { new FluidStackLong(Materials.Radon.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Bismuth.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Oxygen.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Tin.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Lead.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Thorium.getPlasma(56000000), 56000000L),
                new FluidStackLong(Materials.Naquadria.getPlasma(56000000), 56000000L),
                new FluidStackLong(MaterialsUEVplus.DimensionallyTranscendentResidue.getFluid(112000000), 112000000L),
                new FluidStackLong(MaterialsUEVplus.RawStarMatter.getFluid(700000), 700000L),
                new FluidStackLong(MaterialsUEVplus.WhiteDwarfMatter.getMolten(4608), 4608L),
                new FluidStackLong(MaterialsUEVplus.BlackDwarfMatter.getMolten(4608), 4608L),
                new FluidStackLong(MaterialsUEVplus.Universium.getMolten(1152), 1152L) },
            7,
            568000000000000000L,
            7090000000000000L,
            10000000000L,
            10000000000L,
            189744,
            0.65);
    }
}
