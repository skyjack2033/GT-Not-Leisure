package com.science.gtnl.utils.machine;

import java.util.IdentityHashMap;
import java.util.Map;

import com.github.bsideup.jabel.Desugar;

import thaumcraft.api.aspects.Aspect;

public class LargeEssentiaEnergyData {

    public static final Map<Aspect, FuelData> ASPECT_FUEL_DATA = new IdentityHashMap<>(128);

    static {
        add("Aer", 1000, 1, 1.0f);
        add("Terra", 800, 0, 0.0f);
        add("Ignis", 16000, 2, 1.0f);
        add("Aqua", 5000, 0, 0.0f);
        add("Ordo", 12000, 0, 0.0f);
        add("Perditio", 4500, 3, 1.0f);
        add("Vacuos", 8200, 3, 1.5f);
        add("Lux", 13000, 0, 0.0f);
        add("Tempestas", 61200, 0, 0.0f);
        add("Motus", 20000, 1, 0.5f);
        add("Gelum", 3900, 0, 0.0f);
        add("Vitreus", 2000, 0, 0.0f);
        add("Victus", 20000, 4, 1.0f);
        add("Venenum", 17000, 3, 2.0f);
        add("Potentia", 25600, 0, 0.0f);
        add("Permutatio", 6000, 0, 0.0f);
        add("Metallum", 2600, 0, 0.0f);
        add("Mortuus", 7200, 5, 1.0f);
        add("Volatus", 56000, 1, 2.0f);
        add("Tenebrae", 9000, 0, 0.0f);
        add("Spiritus", 37600, 4, 0.7f);
        add("Sano", 24600, 4, 1.2f);
        add("Iter", 16000, 6, 1.0f);
        add("Alienis", 9700, 5, 1.7f);
        add("Praecantatio", 36000, 0, 0.0f);
        add("Auram", 42000, 1, 2.7f);
        add("Vitium", 4700, 5, 10.0f);
        add("Limus", 11800, 6, 1.0f);
        add("Herba", 2600, 0, 0.0f);
        add("Arbor", 4500, 0, 0.0f);
        add("Bestia", 1000, 0, 0.0f);
        add("Corpus", 5200, 4, 0.9f);
        add("Exanimis", 9000, 5, 1.2f);
        add("Cognitio", 2200, 7, 1.2f);
        add("Sensus", 1400, 7, 0.7f);
        add("Humanus", 16700, 4, 1.3f);
        add("Messis", 9800, 0, 0.0f);
        add("Perfodio", 1300, 0, 0.0f);
        add("Instrumentum", 1200, 0, 0.0f);
        add("Meto", 3600, 0, 0.0f);
        add("Telum", 14200, 3, 2.5f);
        add("Tutamen", 6200, 0, 0.0f);
        add("Fames", 17600, 4, 1.1f);
        add("Lucrum", 6000, 7, 2.0f);
        add("Fabrico", 1100, 0, 0.0f);
        add("Pannus", 600, 0, 0.0f);
        add("Machina", 61200, 6, 1.0f);
        add("Vinculum", 500, 0, 0.0f);
        add("Strontio", 200, 7, 0.03f);
        add("Nebrisum", 24300, 7, 2.0f);
        add("Electrum", 8, 9, 0.0f);
        add("Magneto", 108000, 0, 0.0f);
        add("Radio", 238000, 8, 1.0f);
        add("Custom1", 300000, 2, 7.0f);
        add("Custom2", 1, 0, 0.0f);
        add("Custom3", 217000, 0, 0.0f);
        add("Custom4", 118000, 8, 0.5f);
        add("Custom5", 120000, 4, 0.6f);
        add("Luxuria", 79200, 7, 3.7f);
        add("Infernus", 35700, 2, 3.0f);
        add("Superbia", 10900, 7, 2.1f);
        add("Gula", 64000, 0, 0.0f);
        add("Invidia", 7700, 7, 1.0f);
        add("Desidia", 600, 7, 0.1f);
        add("Ira", 86200, 2, 5.0f);
        add("Tempus", 142857, 0, 0.0f);
        add("Terminus", 300000, 3, 10.0f);
    }

    private static void add(String name, int fuel, int category, float ceo) {
        Aspect aspect = Aspect.getAspect(name.toLowerCase(java.util.Locale.ROOT));
        if (aspect != null) {
            ASPECT_FUEL_DATA.put(aspect, new FuelData(fuel, (short) category, ceo));
        }
    }

    public static FuelData get(Aspect aspect) {
        return ASPECT_FUEL_DATA.get(aspect);
    }

    /**
     * <li>0 - NORMAL</li>
     * <li>1 - AIR</li>
     * <li>2 - THERMAL</li>
     * <li>3 - UNSTABLE</li>
     * <li>4 - VICTUS</li>
     * <li>5 - TAINTED</li>
     * <li>6 - MECHANICS</li>
     * <li>7 - SPRITE</li>
     * <li>8 - RADIATION</li>
     * <li>9 - ELECTRIC</li>
     */
    public static int getAspectTypeIndex(Aspect aspect) {
        FuelData d = ASPECT_FUEL_DATA.get(aspect);
        return d == null ? -1 : d.categoryIndex;
    }

    public static int getAspectFuelValue(Aspect aspect) {
        FuelData d = ASPECT_FUEL_DATA.get(aspect);
        return d == null ? 0 : d.fuelValue;
    }

    public static float getAspectCeo(Aspect aspect) {
        FuelData d = ASPECT_FUEL_DATA.get(aspect);
        return d == null ? 0f : d.consumeSpeed;
    }

    @Desugar
    public record FuelData(int fuelValue, short categoryIndex, float consumeSpeed) {}
}
