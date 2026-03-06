package com.science.gtnl.mixins.late.AppliedEnergistics;

import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.science.gtnl.api.mixinHelper.IDualityInterface;
import com.science.gtnl.config.MainConfig;

import appeng.api.config.YesNo;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import appeng.helpers.MultiCraftingTracker;
import appeng.helpers.UnlockCraftingEvent;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.storage.MEMonitorPassThrough;
import appeng.parts.automation.UpgradeInventory;
import appeng.tile.inventory.AppEngInternalAEInventory;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.util.ConfigManager;
import appeng.util.ScheduledReason;
import appeng.util.inv.WrapperInvSlot;

@Mixin(value = DualityInterface.class, remap = false)
public abstract class MixinDualityInterface implements IDualityInterface {

    @Shadow
    @Final
    @Mutable
    private int[] sides;
    @Shadow
    @Final
    @Mutable
    private IAEItemStack[] requireWork;
    @Shadow
    @Final
    @Mutable
    private boolean[] hasFuzzyConfig;
    @Shadow
    @Final
    @Mutable
    private MultiCraftingTracker craftingTracker;
    @Shadow
    @Final
    @Mutable
    protected AENetworkProxy gridProxy;
    @Shadow
    @Final
    @Mutable
    private IInterfaceHost iHost;
    @Shadow
    @Final
    @Mutable
    private BaseActionSource mySource;
    @Shadow
    @Final
    @Mutable
    private BaseActionSource interfaceRequestSource;
    @Shadow
    @Final
    @Mutable
    private ConfigManager cm;
    @Shadow
    @Final
    @Mutable
    private AppEngInternalAEInventory config;
    @Shadow
    @Final
    @Mutable
    private AppEngInternalInventory patterns;
    @Shadow
    @Final
    @Mutable
    private MEMonitorPassThrough<IAEItemStack> items;
    @Shadow
    @Final
    @Mutable
    private MEMonitorPassThrough<IAEFluidStack> fluids;
    @Shadow
    @Final
    @Mutable
    private UpgradeInventory upgrades;

    @Shadow
    private AppEngInternalInventory storage;
    @Shadow
    private WrapperInvSlot slotInv;
    @Shadow
    private ItemStack stored;
    @Shadow
    private IAEItemStack fuzzyItemStack;
    @Shadow
    private boolean hasConfig;
    @Shadow
    private int priority;
    @Shadow
    public List<ICraftingPatternDetails> craftingList;
    @Shadow
    public boolean sharedInventory;
    @Shadow
    private List<ItemStack> waitingToSend;
    @Shadow
    private IMEInventory<IAEItemStack> destination;
    @Shadow
    private boolean isWorking;
    @Shadow
    private YesNo redstoneState;
    @Shadow
    private UnlockCraftingEvent unlockEvent;
    @Shadow
    private List<IAEItemStack> unlockStacks;
    @Shadow
    private int lastInputHash;
    @Shadow
    private ScheduledReason scheduledReason;

    @Invoker("updatePlan")
    public abstract void gtnl$invokeUpdatePlan(int slot);

    @Inject(
        method = "getTermName",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;getUnlocalizedName()Ljava/lang/String;",
            remap = true),
        cancellable = true)
    private void gtnl$injectBeforeItemReturn(CallbackInfoReturnable<String> cir,
        @Local(name = "item") ItemStack itemStack) {
        if (!MainConfig.machine.enableHatchInterfaceTerminalEnhance) return;
        if (!itemStack.hasDisplayName()) return;
        String name = itemStack.getDisplayName();
        if (!name.startsWith("gt_circuit_") && !name.contains("extra_start_")) return;
        cir.setReturnValue(name + itemStack.getUnlocalizedName());
    }

    @Unique
    private int configSlots = 9;
    @Unique
    private int storageSlots = 9;
    @Unique
    private int patternSlots = 9 * 4;
    @Unique
    private int upgradeSlots = 4;

    @ModifyConstant(method = "readConfig", constant = @Constant(intValue = 9))
    private int modifyConfigSlots(int original) {
        return configSlots;
    }

    @ModifyConstant(method = "updateStorage", constant = @Constant(intValue = 9))
    private int modifyUpdateStorage(int original) {
        return storageSlots;
    }

    @Override
    public int getConfigSlots() {
        return configSlots;
    }

    @Override
    public void setConfigSlots(int slots) {
        this.configSlots = slots;
    }

    @Override
    public int getStorageSlots() {
        return storageSlots;
    }

    @Override
    public void setStorageSlots(int slots) {
        this.storageSlots = slots;
    }

    @Override
    public int getPatternSlots() {
        return patternSlots;
    }

    @Override
    public void setPatternSlots(int slots) {
        this.patternSlots = slots;
    }

    @Override
    public int getUpgradeSlots() {
        return upgradeSlots;
    }

    @Override
    public void setUpgradeSlots(int slots) {
        this.upgradeSlots = slots;
    }

    @Override
    public int[] gtnl$getSides() {
        return this.sides;
    }

    @Override
    public void gtnl$setSides(int[] sides) {
        this.sides = sides;
    }

    @Override
    public IAEItemStack[] gtnl$getRequireWork() {
        return this.requireWork;
    }

    @Override
    public void gtnl$setRequireWork(IAEItemStack[] requireWork) {
        this.requireWork = requireWork;
    }

    @Override
    public boolean[] gtnl$getHasFuzzyConfig() {
        return this.hasFuzzyConfig;
    }

    @Override
    public void gtnl$setHasFuzzyConfig(boolean[] hasFuzzyConfig) {
        this.hasFuzzyConfig = hasFuzzyConfig;
    }

    @Override
    public MultiCraftingTracker gtnl$getCraftingTracker() {
        return this.craftingTracker;
    }

    @Override
    public void gtnl$setCraftingTracker(MultiCraftingTracker tracker) {
        this.craftingTracker = tracker;
    }

    @Override
    public AENetworkProxy gtnl$getGridProxy() {
        return this.gridProxy;
    }

    @Override
    public void gtnl$setGridProxy(AENetworkProxy proxy) {
        this.gridProxy = proxy;
    }

    @Override
    public IInterfaceHost gtnl$getIHost() {
        return this.iHost;
    }

    @Override
    public void gtnl$setIHost(IInterfaceHost host) {
        this.iHost = host;
    }

    @Override
    public BaseActionSource gtnl$getMySource() {
        return this.mySource;
    }

    @Override
    public void gtnl$setMySource(BaseActionSource source) {
        this.mySource = source;
    }

    @Override
    public BaseActionSource gtnl$getInterfaceRequestSource() {
        return this.interfaceRequestSource;
    }

    @Override
    public void gtnl$setInterfaceRequestSource(BaseActionSource source) {
        this.interfaceRequestSource = source;
    }

    @Override
    public ConfigManager gtnl$getCm() {
        return this.cm;
    }

    @Override
    public void gtnl$setCm(ConfigManager cm) {
        this.cm = cm;
    }

    @Override
    public AppEngInternalAEInventory gtnl$getConfig() {
        return this.config;
    }

    @Override
    public void gtnl$setConfig(AppEngInternalAEInventory config) {
        this.config = config;
    }

    @Override
    public AppEngInternalInventory gtnl$getPatterns() {
        return this.patterns;
    }

    @Override
    public void gtnl$setPatterns(AppEngInternalInventory patterns) {
        this.patterns = patterns;
    }

    @Override
    public MEMonitorPassThrough<IAEItemStack> gtnl$getItems() {
        return this.items;
    }

    @Override
    public void gtnl$setItems(MEMonitorPassThrough<IAEItemStack> items) {
        this.items = items;
    }

    @Override
    public MEMonitorPassThrough<IAEFluidStack> gtnl$getFluids() {
        return this.fluids;
    }

    @Override
    public void gtnl$setFluids(MEMonitorPassThrough<IAEFluidStack> fluids) {
        this.fluids = fluids;
    }

    @Override
    public UpgradeInventory gtnl$getUpgrades() {
        return this.upgrades;
    }

    @Override
    public void gtnl$setUpgrades(UpgradeInventory upgrades) {
        this.upgrades = upgrades;
    }

    @Override
    public AppEngInternalInventory gtnl$getStorage() {
        return this.storage;
    }

    @Override
    public void gtnl$setStorage(AppEngInternalInventory storage) {
        this.storage = storage;
    }

    @Override
    public WrapperInvSlot gtnl$getSlotInv() {
        return this.slotInv;
    }

    @Override
    public void gtnl$setSlotInv(WrapperInvSlot slotInv) {
        this.slotInv = slotInv;
    }

    @Override
    public ItemStack gtnl$getStored() {
        return this.stored;
    }

    @Override
    public void gtnl$setStored(ItemStack stored) {
        this.stored = stored;
    }

    @Override
    public IAEItemStack gtnl$getFuzzyItemStack() {
        return this.fuzzyItemStack;
    }

    @Override
    public void gtnl$setFuzzyItemStack(IAEItemStack stack) {
        this.fuzzyItemStack = stack;
    }

    @Override
    public boolean gtnl$getHasConfig() {
        return this.hasConfig;
    }

    @Override
    public void gtnl$setHasConfig(boolean value) {
        this.hasConfig = value;
    }

    @Override
    public int gtnl$getPriority() {
        return this.priority;
    }

    @Override
    public void gtnl$setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public List<ICraftingPatternDetails> gtnl$getCraftingList() {
        return this.craftingList;
    }

    @Override
    public void gtnl$setCraftingList(List<ICraftingPatternDetails> list) {
        this.craftingList = list;
    }

    @Override
    public boolean gtnl$getSharedInventory() {
        return this.sharedInventory;
    }

    @Override
    public void gtnl$setSharedInventory(boolean value) {
        this.sharedInventory = value;
    }

    @Override
    public List<ItemStack> gtnl$getWaitingToSend() {
        return this.waitingToSend;
    }

    @Override
    public void gtnl$setWaitingToSend(List<ItemStack> list) {
        this.waitingToSend = list;
    }

    @Override
    public IMEInventory<IAEItemStack> gtnl$getDestination() {
        return this.destination;
    }

    @Override
    public void gtnl$setDestination(IMEInventory<IAEItemStack> dest) {
        this.destination = dest;
    }

    @Override
    public boolean gtnl$getIsWorking() {
        return this.isWorking;
    }

    @Override
    public void gtnl$setIsWorking(boolean working) {
        this.isWorking = working;
    }

    @Override
    public YesNo gtnl$getRedstoneState() {
        return this.redstoneState;
    }

    @Override
    public void gtnl$setRedstoneState(YesNo state) {
        this.redstoneState = state;
    }

    @Override
    public UnlockCraftingEvent gtnl$getUnlockEvent() {
        return this.unlockEvent;
    }

    @Override
    public void gtnl$setUnlockEvent(UnlockCraftingEvent event) {
        this.unlockEvent = event;
    }

    @Override
    public List<IAEItemStack> gtnl$getUnlockStacks() {
        return this.unlockStacks;
    }

    @Override
    public void gtnl$setUnlockStacks(List<IAEItemStack> stacks) {
        this.unlockStacks = stacks;
    }

    @Override
    public int gtnl$getLastInputHash() {
        return this.lastInputHash;
    }

    @Override
    public void gtnl$setLastInputHash(int hash) {
        this.lastInputHash = hash;
    }

    @Override
    public ScheduledReason gtnl$getScheduledReason() {
        return this.scheduledReason;
    }

    @Override
    public void gtnl$setScheduledReason(ScheduledReason reason) {
        this.scheduledReason = reason;
    }

    @Override
    public void gtnl$updatePlan(int slot) {
        this.gtnl$invokeUpdatePlan(slot);
    }
}
