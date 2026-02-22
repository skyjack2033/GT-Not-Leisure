package com.science.gtnl.asm;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.science.gtnl.mixins.EarlyMixinLoader;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.github.tox1cozz.mixinbooterlegacy.IEarlyMixinLoader;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({ "com.science.gtnl.asm" })
@IFMLLoadingPlugin.Name("GTNL core plugin")
public class GTNLEarlyCoreMod implements IFMLLoadingPlugin, IEarlyMixinLoader, IFMLCallHook {

    public static Logger LOGGER = LogManager.getLogger("GTNL Asm Core Mod");

    public GTNLEarlyCoreMod() {}

    static {
        try {
            if (System.getProperty("java.version")
                .startsWith("1.8")) {
                LOGGER.info("Patching ObfuscationRun.theConstructor for Java 8 compatibility...");
                ObfuscationRunPatcher.patchConstructor();
            } else {
                LOGGER.warn("Skipping ObfuscationRun patch, not running Java 8.");
            }
        } catch (Throwable t) {
            LOGGER.error("Failed to patch ObfuscationRun", t);
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return "com.science.gtnl.asm.GTNLEarlyCoreMod";
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public Void call() throws Exception {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return EarlyMixinLoader.getMixinConfigs();
    }

    @Override
    public boolean shouldMixinConfigQueue(final String mixinConfig) {
        return EarlyMixinLoader.shouldMixinConfigQueue(mixinConfig);
    }
}
