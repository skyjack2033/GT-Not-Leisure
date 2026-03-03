package com.science.gtnl.common.block.blocks.tile;

import java.util.Objects;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.science.gtnl.utils.machine.LargeEssentiaEnergyData;

import cpw.mods.fml.common.Optional;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumicenergistics.api.storage.IAspectStorage;

@Optional.Interface(iface = "thaumicenergistics.api.storage.IAspectStorage", modid = "thaumicenergistics")
public class TileEntityEssentiaHatch extends TileEntity
    implements IAspectContainer, IEssentiaTransport, IAspectStorage {

    public Aspect mLocked;
    public final Object2IntMap<Aspect> aspects = new Object2IntOpenHashMap<>();
    public int totalEssentia = 0;
    public int mState = 0;
    public int maxAmountEssentia = 20000;
    public int suctionAmount = 256;

    public void setLockedAspect(Aspect aAspect) {
        this.mLocked = aAspect;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        this.mLocked = Aspect.getAspect(tagCompound.getString("mLocked"));
        this.mState = tagCompound.getInteger("mState");
        aspects.clear();
        totalEssentia = 0;

        NBTTagList tlist = tagCompound.getTagList("Aspects", 10);
        for (int j = 0; j < tlist.tagCount(); ++j) {
            NBTTagCompound rs = tlist.getCompoundTagAt(j);
            if (rs.hasKey("key")) {
                Aspect aspect = Aspect.getAspect(rs.getString("key"));
                int amount = rs.getInteger("amount");
                aspects.put(aspect, amount);
                totalEssentia += amount;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        tagCompound.setString("mLocked", this.mLocked == null ? "" : this.mLocked.getTag());
        tagCompound.setInteger("mState", mState);
        NBTTagList tlist = new NBTTagList();
        for (Object2IntMap.Entry<Aspect> entry : aspects.object2IntEntrySet()) {
            Aspect aspect = entry.getKey();
            if (aspect != null) {
                NBTTagCompound f = new NBTTagCompound();
                f.setString("key", aspect.getTag());
                f.setInteger("amount", entry.getIntValue());
                tlist.appendTag(f);
            }
        }
        tagCompound.setTag("Aspects", tlist);
    }

    @Override
    public final Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (this.worldObj.isRemote) return;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void updateEntity() {
        fillfrompipe();
    }

    public void fillfrompipe() {
        if (totalEssentia >= maxAmountEssentia) return;

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(worldObj, xCoord, yCoord, zCoord, dir);
            if (!(te instanceof IEssentiaTransport pipe)) continue;
            if (!pipe.canOutputTo(dir)) continue;

            ForgeDirection opposite = dir.getOpposite();
            Aspect readyInput = pipe.getEssentiaType(opposite);
            if (readyInput == null) continue;
            if (pipe.getSuctionAmount(dir) >= getSuctionAmount(dir)) continue;

            int typeIndex = LargeEssentiaEnergyData.getAspectTypeIndex(readyInput);
            if (typeIndex != -1 && (mState & (1 << typeIndex)) == 0) continue;

            if (readyInput.equals(mLocked)) {
                addToContainer(mLocked, pipe.takeEssentia(mLocked, 1, dir));
            } else if (mLocked == null) {
                addToContainer(readyInput, pipe.takeEssentia(readyInput, 1, dir));
            }
        }
    }

    @Override
    public AspectList getAspects() {
        AspectList list = new AspectList();
        for (Object2IntMap.Entry<Aspect> entry : aspects.object2IntEntrySet()) {
            list.add(entry.getKey(), entry.getIntValue());
        }
        return list;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        for (Aspect a : aspectList.aspects.keySet()) {
            int amount = aspectList.getAmount(a);
            addToContainer(a, amount);
        }
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        int type = LargeEssentiaEnergyData.getAspectTypeIndex(aspect);
        if (type != -1 && (mState & (1 << type)) == 0) return false;
        return (mLocked == null || mLocked.equals(aspect)) && totalEssentia < maxAmountEssentia;
    }

    @Override
    public int addToContainer(Aspect aspect, int amount) {
        int type = LargeEssentiaEnergyData.getAspectTypeIndex(aspect);
        if (type != -1 && (mState & (1 << type)) == 0) return amount;

        int ready = Math.min(maxAmountEssentia - totalEssentia, amount);
        if ((mLocked == null || Objects.equals(mLocked, aspect)) && ready > 0) {
            aspects.put(aspect, aspects.getInt(aspect) + ready);
            totalEssentia += ready;
            markDirty();
            return amount - ready;
        }
        markDirty();
        return amount;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amount) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amount) {
        return aspects.containsKey(aspect) && amount <= aspects.getInt(aspect);
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        for (Aspect a : aspectList.aspects.keySet()) {
            if (!aspects.containsKey(a)) return false;
        }
        return true;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return aspects.getInt(aspect);
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {}

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        return mLocked;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return suctionAmount;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, ForgeDirection forgeDirection) {
        return amount - addToContainer(aspect, amount);
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {
        return aspects.isEmpty() ? null
            : aspects.keySet()
                .iterator()
                .next();
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        return totalEssentia;
    }

    @Override
    public int getMinimumSuction() {
        return Integer.MAX_VALUE;
    }

    @Override
    @Optional.Method(modid = "thaumicenergistics")
    public int getContainerCapacity() {
        return maxAmountEssentia;
    }

    @Override
    @Optional.Method(modid = "thaumicenergistics")
    public boolean doesShareCapacity() {
        return true;
    }

    @Override
    public boolean renderExtendedTube() {
        return true;
    }
}
