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
            new ItemStackLong[] {
                new ItemStackLong(WerkstoffMaterialPool.Gangue.get(OrePrefixes.dust, 1519274962), 1519274962L),
                new ItemStackLong(WerkstoffLoader.Bismutite.get(OrePrefixes.dust, 161842735), 161842735L),
                new ItemStackLong(WerkstoffLoader.VanadioOxyDravit.get(OrePrefixes.dust, 154923846), 154923846L),
                new ItemStackLong(WerkstoffLoader.Arsenopyrite.get(OrePrefixes.dust, 148375192), 148375192L),
                new ItemStackLong(WerkstoffLoader.Ferberite.get(OrePrefixes.dust, 142106483), 142106483L),
                new ItemStackLong(WerkstoffLoader.Loellingit.get(OrePrefixes.dust, 136159274), 136159274L),
                new ItemStackLong(WerkstoffLoader.Roquesit.get(OrePrefixes.dust, 129847315), 129847315L),
                new ItemStackLong(WerkstoffLoader.RedZircon.get(OrePrefixes.dust, 124036198), 124036198L),
                new ItemStackLong(WerkstoffLoader.Fayalit.get(OrePrefixes.dust, 118294756), 118294756L),
                new ItemStackLong(WerkstoffLoader.DescloiziteCUVO4.get(OrePrefixes.dust, 112958423), 112958423L),
                new ItemStackLong(WerkstoffLoader.FuchsitAL.get(OrePrefixes.dust, 107482931), 107482931L),
                new ItemStackLong(WerkstoffLoader.FuchsitCR.get(OrePrefixes.dust, 102165847), 102165847L),
                new ItemStackLong(WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.dust, 96947215), 96947215L),
                new ItemStackLong(WerkstoffLoader.BArTiMaEuSNeK.get(OrePrefixes.dust, 91583642), 91583642L),
                new ItemStackLong(WerkstoffLoader.Tiberium.get(OrePrefixes.dust, 86429173), 86429173L),
                new ItemStackLong(
                    WerkstoffMaterialPool.SamariumOreConcentrate.get(OrePrefixes.dust, 81856394),
                    81856394L),
                new ItemStackLong(GTNLMaterials.Technetium.get(OrePrefixes.dust, 77294815), 77294815L),
                new ItemStackLong(WerkstoffLoader.Zirconium.get(OrePrefixes.dust, 72918463), 72918463L),
                new ItemStackLong(WerkstoffLoader.Ruthenium.get(OrePrefixes.dust, 68472531), 68472531L),
                new ItemStackLong(WerkstoffLoader.Rhodium.get(OrePrefixes.dust, 63819472), 63819472L),
                new ItemStackLong(WerkstoffLoader.Atheneite.get(OrePrefixes.dust, 59283746), 59283746L),
                new ItemStackLong(MaterialsElements.getInstance().HAFNIUM.getDust(55104827), 55104827L),
                new ItemStackLong(WerkstoffMaterialPool.Hafnia.get(OrePrefixes.dust, 50928471), 50928471L),
                new ItemStackLong(WerkstoffMaterialPool.Iodine.get(OrePrefixes.dust, 46739158), 46739158L),
                new ItemStackLong(Materials.Draconium.getDust(42581639), 42581639L),
                new ItemStackLong(Materials.Beryllium.getDust(38927415), 38927415),
                new ItemStackLong(Materials.Iron.getDust(35284617), 35284617L),
                new ItemStackLong(Materials.Zinc.getDust(31846592), 31846592),
                new ItemStackLong(Materials.Samarium.getDust(28471935), 28471935L),
                new ItemStackLong(Materials.Neutronium.getDust(25293846), 25293846L),
                new ItemStackLong(Materials.Rutile.getDust(22184753), 22184753),
                new ItemStackLong(Materials.Ardite.getDust(19472638), 19472638L),
                new ItemStackLong(Materials.Ledox.getDust(16829471), 16829471L),
                new ItemStackLong(Materials.InfinityCatalyst.getDust(14385926), 14385926L),
                new ItemStackLong(Materials.MysteriousCrystal.getDust(12174853), 12174853L),
                new ItemStackLong(Materials.Rubracium.getDust(10284719), 10284719L),
                new ItemStackLong(Materials.Vulcanite.getDust(8572931), 8572931L),
                new ItemStackLong(Materials.GreenSapphire.getDust(7103846), 7103846L),
                new ItemStackLong(Materials.Olivine.getDust(5847192), 5847192L),
                new ItemStackLong(Materials.NetherStar.getDust(4729183), 4729183L),
                new ItemStackLong(Materials.Topaz.getDust(3819472), 3819472L),
                new ItemStackLong(Materials.Tanzanite.getDust(3104825), 3104825),
                new ItemStackLong(Materials.Opal.getDust(2518374), 2518374L),
                new ItemStackLong(Materials.BlueTopaz.getDust(1938472), 1938472L),
                new ItemStackLong(Materials.Forcicium.getDust(1492837), 1492837L),
                new ItemStackLong(Materials.Vinteum.getDust(1128475), 1128475L),
                new ItemStackLong(Materials.TricalciumPhosphate.getDust(947182), 947182L),
                new ItemStackLong(Materials.InfusedAir.getDust(819274), 819274L),
                new ItemStackLong(Materials.InfusedFire.getDust(728361), 728361L),
                new ItemStackLong(Materials.InfusedEarth.getDust(651928), 651928L),
                new ItemStackLong(Materials.InfusedWater.getDust(592834), 592834L),
                new ItemStackLong(Materials.InfusedEntropy.getDust(541283), 541283L),
                new ItemStackLong(Materials.SiliconDioxide.getDust(498271), 498271L),
                new ItemStackLong(MaterialsElements.getInstance().RHENIUM.getDust(462193), 462193L),
                new ItemStackLong(MaterialsElements.getInstance().THALLIUM.getDust(431827), 431827L),
                new ItemStackLong(MaterialsElements.getInstance().GERMANIUM.getDust(408273), 408273L),
                new ItemStackLong(MaterialsElements.getInstance().SELENIUM.getDust(385912), 385912L),
                new ItemStackLong(CustomItemList.ChargedCertusQuartzDust.get(364182), 364182L),
                new ItemStackLong(Materials.Salt.getDust(341928), 341928),
                new ItemStackLong(Materials.Mica.getDust(325817), 325817L),
                new ItemStackLong(Materials.Bastnasite.getDust(311928), 311928L),
                new ItemStackLong(Materials.Lepidolite.getDust(298471), 298471L),
                new ItemStackLong(Materials.Realgar.getDust(287319), 287319L),
                new ItemStackLong(Materials.Redstone.getDust(279182), 279182L),
                new ItemStackLong(Materials.CosmicNeutronium.getDust(274819), 274819L),
                new ItemStackLong(Materials.QuartzSand.getDust(270182), 270182L),
                new ItemStackLong(Materials.Asbestos.getDust(267491), 267491L),
                new ItemStackLong(Materials.Vyroxeres.getDust(265182), 265182L),
                new ItemStackLong(Materials.Orichalcum.getDust(263918), 263918L),
                new ItemStackLong(Materials.Glowstone.getDust(262473), 262473L),
                new ItemStackLong(Materials.Diamond.getGems(262105), 262105), },
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
