package com.science.gtnl.utils.enums;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import gregtech.api.interfaces.IIconContainer;
import gregtech.client.iconContainers.blocks.GTBlockIconContainer;

public class BlockIcons {

    private static final String BASE_REPLICATOR = "basicmachines/replicator/";
    private static final String BASE_NINE_HATCH = RESOURCE_ROOT_ID + ":iconsets/OVERLAY_NINE_HATCH/";
    private static final String BASE = RESOURCE_ROOT_ID + ":iconsets/";

    public static IIconContainer OVERLAY_ENERGY_TRANSFER_NODE = GTBlockIconContainer
        .create(BASE + "OVERLAY_ENERGY_TRANSFER_NODE");
    public static IIconContainer OVERLAY_ENERGY_TRANSFER_NODE_ACTIVE = GTBlockIconContainer
        .create(BASE + "OVERLAY_ENERGY_TRANSFER_NODE_ACTIVE");

    public static IIconContainer LASER_BEACON_TOP = GTBlockIconContainer.create(BASE + "LASER_BEACON_TOP");

    public static IIconContainer BEAMLINE_PIPE_MIRROR = GTBlockIconContainer.create(BASE + "BEAMLINE_PIPE_MIRROR");

    public static IIconContainer OVERLAY_FRONT_FULLAUTOMAINTENANCE = GTBlockIconContainer
        .create("iconsets/OVERLAY_FULLAUTOMAINTENANCE");
    public static IIconContainer OVERLAY_FRONT_DUAL_HATCH = GTBlockIconContainer.create(BASE + "OVERLAY_DUAL_HATCH");
    public static IIconContainer OVERLAY_FRONT_PARALLEL_CONTROLLER = GTBlockIconContainer
        .create(BASE + "OVERLAY_PARALLEL_CONTROLLER");
    public static IIconContainer OVERLAY_FRONT_ITEMVAULTPORTHATCH = GTBlockIconContainer
        .create(BASE + "OVERLAY_FRONT_ITEMVAULTPORTHATCH");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH = GTBlockIconContainer.create(BASE_NINE_HATCH + "OVERLAY");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_NONE = GTBlockIconContainer.create(BASE_NINE_HATCH + "NONE");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_BLACK = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "BLACK");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_RED = GTBlockIconContainer.create(BASE_NINE_HATCH + "RED");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_GREEN = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "GREEN");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_BROWN = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "BROWN");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_BLUE = GTBlockIconContainer.create(BASE_NINE_HATCH + "BLUE");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_PURPLE = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "PURPLE");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_CYAN = GTBlockIconContainer.create(BASE_NINE_HATCH + "CYAN");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_LIGHTGRAY = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "LIGHTGRAY");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_GRAY = GTBlockIconContainer.create(BASE_NINE_HATCH + "GRAY");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_PINK = GTBlockIconContainer.create(BASE_NINE_HATCH + "PINK");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_LIME = GTBlockIconContainer.create(BASE_NINE_HATCH + "LIME");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_YELLOW = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "YELLOW");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_LIGHTBLUE = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "LIGHTBLUE");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_MAGENTA = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "MAGENTA");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_ORANGE = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "ORANGE");
    public static IIconContainer OVERLAY_FRONT_NINE_HATCH_WHITE = GTBlockIconContainer
        .create(BASE_NINE_HATCH + "WHITE");

    public static final IIconContainer[] OVERLAY_FRONT_NINE_HATCH_COLOR = { OVERLAY_FRONT_NINE_HATCH_NONE,
        OVERLAY_FRONT_NINE_HATCH_BLACK, OVERLAY_FRONT_NINE_HATCH_RED, OVERLAY_FRONT_NINE_HATCH_GREEN,
        OVERLAY_FRONT_NINE_HATCH_BROWN, OVERLAY_FRONT_NINE_HATCH_BLUE, OVERLAY_FRONT_NINE_HATCH_PURPLE,
        OVERLAY_FRONT_NINE_HATCH_CYAN, OVERLAY_FRONT_NINE_HATCH_LIGHTGRAY, OVERLAY_FRONT_NINE_HATCH_GRAY,
        OVERLAY_FRONT_NINE_HATCH_PINK, OVERLAY_FRONT_NINE_HATCH_LIME, OVERLAY_FRONT_NINE_HATCH_YELLOW,
        OVERLAY_FRONT_NINE_HATCH_LIGHTBLUE, OVERLAY_FRONT_NINE_HATCH_MAGENTA, OVERLAY_FRONT_NINE_HATCH_ORANGE,
        OVERLAY_FRONT_NINE_HATCH_WHITE, };

    public static IIconContainer OVERLAY_FRONT_INDICATOR = GTBlockIconContainer
        .create(BASE + "Indicator/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_INDICATOR_RED = GTBlockIconContainer
        .create(BASE + "Indicator/OVERLAY_FRONT_RED");
    public static IIconContainer OVERLAY_FRONT_INDICATOR_YELLOW = GTBlockIconContainer
        .create(BASE + "Indicator/OVERLAY_FRONT_YELLOW");
    public static IIconContainer OVERLAY_FRONT_INDICATOR_GREEN = GTBlockIconContainer
        .create(BASE + "Indicator/OVERLAY_FRONT_GREEN");

    public static final IIconContainer OVERLAY_SIDE_REPLICATOR_ACTIVE = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_SIDE_REPLICATOR_ACTIVE");
    public static final IIconContainer OVERLAY_SIDE_REPLICATOR_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_SIDE_REPLICATOR_ACTIVE_GLOW");
    public static final IIconContainer OVERLAY_SIDE_REPLICATOR = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_SIDE_REPLICATOR");
    public static final IIconContainer OVERLAY_SIDE_REPLICATOR_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_SIDE_REPLICATOR_GLOW");
    public static final IIconContainer OVERLAY_FRONT_REPLICATOR_ACTIVE = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_FRONT_REPLICATOR_ACTIVE");
    public static final IIconContainer OVERLAY_FRONT_REPLICATOR_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_FRONT_REPLICATOR_ACTIVE_GLOW");
    public static final IIconContainer OVERLAY_FRONT_REPLICATOR = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_FRONT_REPLICATOR");
    public static final IIconContainer OVERLAY_FRONT_REPLICATOR_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_FRONT_REPLICATOR_GLOW");
    public static final IIconContainer OVERLAY_TOP_REPLICATOR_ACTIVE = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_TOP_REPLICATOR_ACTIVE");
    public static final IIconContainer OVERLAY_TOP_REPLICATOR_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_TOP_REPLICATOR_ACTIVE_GLOW");
    public static final IIconContainer OVERLAY_TOP_REPLICATOR = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_TOP_REPLICATOR");
    public static final IIconContainer OVERLAY_TOP_REPLICATOR_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_TOP_REPLICATOR_GLOW");
    public static final IIconContainer OVERLAY_BOTTOM_REPLICATOR_ACTIVE = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_BOTTOM_REPLICATOR_ACTIVE");
    public static final IIconContainer OVERLAY_BOTTOM_REPLICATOR_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_BOTTOM_REPLICATOR_ACTIVE_GLOW");
    public static final IIconContainer OVERLAY_BOTTOM_REPLICATOR = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_BOTTOM_REPLICATOR");
    public static final IIconContainer OVERLAY_BOTTOM_REPLICATOR_GLOW = GTBlockIconContainer
        .create(BASE_REPLICATOR + "OVERLAY_BOTTOM_REPLICATOR_GLOW");

    public static IIconContainer OVERLAY_FRONT_TECTECH_MULTIBLOCK = GTBlockIconContainer.create("iconsets/EM_COMPUTER");
    public static IIconContainer OVERLAY_FRONT_TECTECH_MULTIBLOCK_ACTIVE = GTBlockIconContainer
        .create("iconsets/EM_COMPUTER_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_GOD_FORGE_MODULE_ACTIVE = GTBlockIconContainer
        .create("iconsets/GODFORGE_MODULE_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_DECAY_HASTENER = GTBlockIconContainer
        .create("icons/NeutronActivator_Off");
    public static IIconContainer OVERLAY_FRONT_DECAY_HASTENER_ACTIVE = GTBlockIconContainer
        .create("icons/NeutronActivator_On");

    public static IIconContainer OVERLAY_FRONT_LARGE_GAS_COLLECTOR = GTBlockIconContainer
        .create(BASE + "LargeGasCollector/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_LARGE_GAS_COLLECTOR_ACTIVE = GTBlockIconContainer
        .create(BASE + "LargeGasCollector/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_CACTUS_WONDER = GTBlockIconContainer
        .create(BASE + "CactusWonder/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_CACTUS_WONDER_ACTIVE = GTBlockIconContainer
        .create(BASE + "CactusWonder/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_STEAM_CARPENTER = GTBlockIconContainer
        .create(BASE + "SteamCarpenter/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_CARPENTER_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamCarpenter/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_STEAM_EXTRACTINATOR = GTBlockIconContainer
        .create(BASE + "SteamExtractinator/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_EXTRACTINATOR_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamExtractinator/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_STEAM_GATE = GTBlockIconContainer
        .create(BASE + "SteamGate/OVERLAY_FRONT");

    public static IIconContainer OVERLAY_FRONT_STEAM_GATE_ASSEMBLER = GTBlockIconContainer
        .create(BASE + "SteamGateAssembler/OVERLAY_FRONT");

    public static IIconContainer OVERLAY_FRONT_STEAM_INFERNAL_COKE_OVEN = GTBlockIconContainer
        .create(BASE + "SteamInfernalCokeOven/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_INFERNAL_COKE_OVEN_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamInfernalCokeOven/OVERLAY_FRONT_ACTIVE");
    public static IIconContainer OVERLAY_FRONT_STEAM_INFERNAL_COKE_OVEN_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE + "SteamInfernalCokeOven/OVERLAY_FRONT_ACTIVE_GLOW");

    public static IIconContainer OVERLAY_FRONT_STEAM_LAVA_MAKER = GTBlockIconContainer
        .create(BASE + "SteamLavaMaker/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_LAVA_MAKER_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamLavaMaker/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_STEAM_MANUFACTURER = GTBlockIconContainer
        .create(BASE + "SteamManufacturer/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_MANUFACTURER_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamManufacturer/OVERLAY_FRONT_ACTIVE");

    public static IIconContainer OVERLAY_FRONT_METEOR_MINER = GTBlockIconContainer
        .create(BASE + "MeteorMiner/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_METEOR_MINER_GLOW = GTBlockIconContainer
        .create(BASE + "MeteorMiner/OVERLAY_FRONT_GLOW");
    public static IIconContainer OVERLAY_FRONT_METEOR_MINER_ACTIVE = GTBlockIconContainer
        .create(BASE + "MeteorMiner/OVERLAY_FRONT_ACTIVE");
    public static IIconContainer OVERLAY_FRONT_METEOR_MINER_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE + "MeteorMiner/OVERLAY_FRONT_ACTIVE_GLOW");

    public static IIconContainer OVERLAY_FRONT_MEGA_SOLAR_BOILER = GTBlockIconContainer
        .create(BASE + "MegaSolarBoiler/OVERLAY_FRONT");

    public static IIconContainer OVERLAY_FRONT_MEGA_STEAM_COMPRESSOR = GTBlockIconContainer
        .create(BASE + "MegaSteamCompressor/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_MEGA_STEAM_COMPRESSOR_GLOW = GTBlockIconContainer
        .create(BASE + "MegaSteamCompressor/OVERLAY_FRONT_GLOW");
    public static IIconContainer OVERLAY_FRONT_MEGA_STEAM_COMPRESSOR_ACTIVE = GTBlockIconContainer
        .create(BASE + "MegaSteamCompressor/OVERLAY_FRONT_ACTIVE");
    public static IIconContainer OVERLAY_FRONT_MEGA_STEAM_COMPRESSOR_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE + "MegaSteamCompressor/OVERLAY_FRONT_ACTIVE_GLOW");

    public static IIconContainer OVERLAY_FRONT_STEAM_ITEM_VAULT = GTBlockIconContainer
        .create(BASE + "SteamItemVault/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_STEAM_ITEM_VAULT_ACTIVE = GTBlockIconContainer
        .create(BASE + "SteamItemVault/OVERLAY_FRONT_ACTIVE");
    public static IIconContainer OVERLAY_FRONT_STEAM_ITEM_VAULT_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE + "SteamItemVault/OVERLAY_FRONT_ACTIVE_GLOW");

    public static IIconContainer OVERLAY_FRONT_SINGULARITY_DATA_HUB = GTBlockIconContainer
        .create(BASE + "SingularityDataHub/OVERLAY_FRONT");
    public static IIconContainer OVERLAY_FRONT_SINGULARITY_DATA_HUB_ACTIVE = GTBlockIconContainer
        .create(BASE + "SingularityDataHub/OVERLAY_FRONT_ACTIVE");
    public static IIconContainer OVERLAY_FRONT_SINGULARITY_DATA_HUB_ACTIVE_GLOW = GTBlockIconContainer
        .create(BASE + "SingularityDataHub/OVERLAY_FRONT_ACTIVE_GLOW");

    public static IIconContainer OVERLAY_FRONT_NEUTRON_ACTIVATOR = GTBlockIconContainer
        .create("icons/NeutronActivator_Off");
    public static IIconContainer OVERLAY_FRONT_NEUTRON_ACTIVATOR_GLOW = GTBlockIconContainer
        .create("icons/NeutronActivator_Off_GLOW");
    public static IIconContainer OVERLAY_FRONT_NEUTRON_ACTIVATOR_ACTIVE = GTBlockIconContainer
        .create("icons/NeutronActivator_On");
    public static IIconContainer OVERLAY_FRONT_NEUTRON_ACTIVATOR_ACTIVE_GLOW = GTBlockIconContainer
        .create("icons/NeutronActivator_On_GLOW");

}
