/*
 * Copyright © 2020. Troy Gidney
 * All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * File Last Modified: 9/5/20, 10:05 AM
 * File: StaffChatCommand.java
 * Project: BungeeUtils
 */

package me.pokerman981.BungeeUtils.commands;

import me.pokerman981.BungeeUtils.Main;
import me.pokerman981.BungeeUtils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCommand extends Command {

    public StaffChatCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(super.getPermission())) {
            Utils.msg(commandSender, Main.messages.getOrDefault("no-perm", "Config Error!"), ChatColor.RED);
            return;
        }
        if (strings.length <= 0) return;

        String messageWords = String.join(" ", strings).replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");

        String messageToSend = Main.messages.getOrDefault("admin-chat", "Config Error!")
                .replaceAll("%player%", commandSender.getName())
                .replaceAll("%message%", messageWords);

        Utils.msg(Main.instance.getProxy().getConsole(), messageToSend, ChatColor.AQUA);

        Main.instance.getProxy().getPlayers().forEach(proxiedPlayer -> {
            if (proxiedPlayer.hasPermission(super.getPermission()) && commandSender != Main.instance.getProxy().getConsole())
                Utils.msg(proxiedPlayer, messageToSend, ChatColor.AQUA, ((ProxiedPlayer) commandSender).getServer().getInfo().getName());
        });
    }
}
