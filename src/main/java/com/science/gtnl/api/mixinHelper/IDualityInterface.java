package com.science.gtnl.api.mixinHelper;

import java.util.List;

import net.minecraft.item.ItemStack;

import appeng.api.config.YesNo;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
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

public interface IDualityInterface {

    int getConfigSlots();

    void setConfigSlots(int slots);

    int getStorageSlots();

    void setStorageSlots(int slots);

    int getPatternSlots();

    void setPatternSlots(int slots);

    int getUpgradeSlots();

    void setUpgradeSlots(int slots);

    int[] gtnl$getSides();

    void gtnl$setSides(int[] sides);

    IAEItemStack[] gtnl$getRequireWork();

    void gtnl$setRequireWork(IAEItemStack[] requireWork);

    boolean[] gtnl$getHasFuzzyConfig();

    void gtnl$setHasFuzzyConfig(boolean[] hasFuzzyConfig);

    MultiCraftingTracker gtnl$getCraftingTracker();

    void gtnl$setCraftingTracker(MultiCraftingTracker craftingTracker);

    AENetworkProxy gtnl$getGridProxy();

    void gtnl$setGridProxy(AENetworkProxy gridProxy);

    IInterfaceHost gtnl$getIHost();

    void gtnl$setIHost(IInterfaceHost iHost);

    BaseActionSource gtnl$getMySource();

    void gtnl$setMySource(BaseActionSource mySource);

    BaseActionSource gtnl$getInterfaceRequestSource();

    void gtnl$setInterfaceRequestSource(BaseActionSource source);

    ConfigManager gtnl$getCm();

    void gtnl$setCm(ConfigManager cm);

    AppEngInternalAEInventory gtnl$getConfig();

    void gtnl$setConfig(AppEngInternalAEInventory config);

    AppEngInternalInventory gtnl$getPatterns();

    void gtnl$setPatterns(AppEngInternalInventory patterns);

    MEMonitorPassThrough<IAEItemStack> gtnl$getItems();

    void gtnl$setItems(MEMonitorPassThrough<IAEItemStack> items);

    MEMonitorPassThrough<IAEFluidStack> gtnl$getFluids();

    void gtnl$setFluids(MEMonitorPassThrough<IAEFluidStack> fluids);

    UpgradeInventory gtnl$getUpgrades();

    void gtnl$setUpgrades(UpgradeInventory upgrades);

    AppEngInternalInventory gtnl$getStorage();

    void gtnl$setStorage(AppEngInternalInventory storage);

    WrapperInvSlot gtnl$getSlotInv();

    void gtnl$setSlotInv(WrapperInvSlot slotInv);

    ItemStack gtnl$getStored();

    void gtnl$setStored(ItemStack stored);

    IAEItemStack gtnl$getFuzzyItemStack();

    void gtnl$setFuzzyItemStack(IAEItemStack stack);

    boolean gtnl$getHasConfig();

    void gtnl$setHasConfig(boolean value);

    int gtnl$getPriority();

    void gtnl$setPriority(int priority);

    List<ICraftingPatternDetails> gtnl$getCraftingList();

    void gtnl$setCraftingList(List<ICraftingPatternDetails> list);

    boolean gtnl$getSharedInventory();

    void gtnl$setSharedInventory(boolean value);

    List<ItemStack> gtnl$getWaitingToSend();

    void gtnl$setWaitingToSend(List<ItemStack> list);

    IMEInventory<IAEItemStack> gtnl$getDestination();

    void gtnl$setDestination(IMEInventory<IAEItemStack> destination);

    boolean gtnl$getIsWorking();

    void gtnl$setIsWorking(boolean working);

    YesNo gtnl$getRedstoneState();

    void gtnl$setRedstoneState(YesNo state);

    UnlockCraftingEvent gtnl$getUnlockEvent();

    void gtnl$setUnlockEvent(UnlockCraftingEvent event);

    List<IAEItemStack> gtnl$getUnlockStacks();

    void gtnl$setUnlockStacks(List<IAEItemStack> stacks);

    int gtnl$getLastInputHash();

    void gtnl$setLastInputHash(int hash);

    ScheduledReason gtnl$getScheduledReason();

    void gtnl$setScheduledReason(ScheduledReason reason);

    void gtnl$updatePlan(int slot);
}
