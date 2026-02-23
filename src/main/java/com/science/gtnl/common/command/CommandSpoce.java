package com.science.gtnl.common.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import com.science.gtnl.common.render.SpoceRenderHandler;
import com.science.gtnl.utils.render.SpoceEffect;

public class CommandSpoce extends CommandBase {

    private static final double DEF_RADIUS = 4.0;
    private static final int DEF_COLOR = 0x66CCFF;
    private static final float DEF_ALPHA = 0.5f;
    private static final boolean DEF_LINES = true;

    @Override
    public String getCommandName() {
        return "spoce";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/spoce test | start <x y z> <radius color renderLines...> | stop";
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "test", "start", "stop");
        }

        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(sender)));
            return;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "stop" -> {
                SpoceRenderHandler.clearAll();
                sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "All render effects cleared"));
            }
            case "test" -> {
                if (!(sender instanceof EntityPlayer player)) {
                    sender.addChatMessage(
                        new ChatComponentText(
                            EnumChatFormatting.RED + "This command can only be executed by a player"));
                    return;
                }

                SpoceEffect testEffect = new SpoceEffect(player.posX, player.posY, player.posZ, 0.8f, 1.0f);
                testEffect.addLayer(4.0, 0x66CCFF, true);
                testEffect.addLayer(6.0, 0xFF9933, true);
                testEffect.addLayer(8.0, 0x66FF66, true);

                SpoceRenderHandler.addEffect(testEffect);
                sender.addChatMessage(
                    new ChatComponentText(EnumChatFormatting.AQUA + "Loaded default Spoce test parameters (3 layers)"));
            }
            case "start" -> {
                try {
                    int idx = 1;
                    double x, y, z;

                    if (args.length >= 4 && (args[1].startsWith("~") || args[1].equals("*") || isNumeric(args[1]))) {
                        double bx = (sender instanceof EntityPlayer) ? ((EntityPlayer) sender).posX : 0;
                        double by = (sender instanceof EntityPlayer) ? ((EntityPlayer) sender).posY : 0;
                        double bz = (sender instanceof EntityPlayer) ? ((EntityPlayer) sender).posZ : 0;

                        x = args[1].equals("*") ? bx : parseCoordinate(bx, args[1]);
                        y = args[2].equals("*") ? by : parseCoordinate(by, args[2]);
                        z = args[3].equals("*") ? bz : parseCoordinate(bz, args[3]);
                        idx = 4;
                    } else if (sender instanceof EntityPlayer player) {
                        x = player.posX;
                        y = player.posY;
                        z = player.posZ;
                    } else {
                        sender.addChatMessage(
                            new ChatComponentText(
                                EnumChatFormatting.RED + "Non-player execution must provide coordinates or use *"));
                        return;
                    }

                    SpoceEffect effect = new SpoceEffect(x, y, z, 0.8f, 1.0f);

                    while (idx < args.length) {
                        String rStr = args[idx++];
                        double radius = rStr.equals("*") ? DEF_RADIUS : Double.parseDouble(rStr);

                        String cStr = (idx < args.length) ? args[idx++] : "*";
                        int color = cStr.equals("*") ? DEF_COLOR : parseColor(cStr);

                        String aStr = (idx < args.length) ? args[idx++] : "*";
                        float alpha = aStr.equals("*") ? DEF_ALPHA : Float.parseFloat(aStr);

                        String lStr = (idx < args.length) ? args[idx++] : "*";
                        boolean lines = lStr.equals("*") ? DEF_LINES : Boolean.parseBoolean(lStr);

                        SpoceEffect.Layer layer = new SpoceEffect.Layer(radius, color, lines);
                        layer.a = alpha;
                        effect.layers.add(layer);
                    }

                    if (effect.layers.isEmpty()) {
                        sender.addChatMessage(
                            new ChatComponentText(
                                EnumChatFormatting.YELLOW
                                    + "Warning: No sphere layers added, using default single layer"));
                        effect.addLayer(DEF_RADIUS, DEF_COLOR, DEF_LINES);
                    }

                    SpoceRenderHandler.addEffect(effect);
                    sender.addChatMessage(
                        new ChatComponentText(
                            EnumChatFormatting.AQUA + String.format(
                                "Spoce instance started (Position: %.1f %.1f %.1f, Layers: %d)",
                                x,
                                y,
                                z,
                                effect.layers.size())));

                } catch (Exception e) {
                    sender.addChatMessage(
                        new ChatComponentText(EnumChatFormatting.RED + "Parameter parsing error: " + e.getMessage()));
                    e.printStackTrace();
                }
            }
        }
    }

    public int parseColor(String s) {
        if (s.startsWith("#")) return (int) Long.parseLong(s.substring(1), 16);
        if (s.startsWith("0x")) return (int) Long.parseLong(s.substring(2), 16);
        return (int) Long.parseLong(s, 16);
    }

    public boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double parseCoordinate(double base, String arg) {
        if (arg.startsWith("~")) {
            return arg.length() == 1 ? base : base + Double.parseDouble(arg.substring(1));
        }
        return Double.parseDouble(arg);
    }
}
