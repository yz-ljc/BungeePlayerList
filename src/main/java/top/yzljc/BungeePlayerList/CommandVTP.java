package top.yzljc.BungeePlayerList;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandVTP extends Command {

    private final BungeePlayerList plugin;

    public CommandVTP(BungeePlayerList plugin) {
        super("vtp", null, new String[0]);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // 只允许玩家使用
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(plugin.color(plugin.getConfig().getString("message.console-run-command")));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (!player.hasPermission("bpl.teleport")) {
            player.sendMessage(plugin.color(plugin.getConfig().getString("message.noperm-run-command")));
            return;
        }

        ProxiedPlayer basePlayer;
        ProxiedPlayer targetPlayer;

        switch (args.length) {
            case 1:
                targetPlayer = ProxyServer.getInstance().getPlayer(args[0]);
                if (targetPlayer == null) {
                    player.sendMessage(plugin.color(plugin.getConfig().getString("message.null-player").replace("{player}", args[0])));
                    return;
                }
                basePlayer = player;
                break;
            case 2:
                targetPlayer = ProxyServer.getInstance().getPlayer(args[1]);
                if (targetPlayer == null) {
                    player.sendMessage(plugin.color(plugin.getConfig().getString("message.null-player").replace("{player}", args[1])));
                    return;
                }
                basePlayer = ProxyServer.getInstance().getPlayer(args[0]);
                if (basePlayer == null) {
                    player.sendMessage(plugin.color(plugin.getConfig().getString("message.null-player").replace("{player}", args[0])));
                    return;
                }
                break;
            default:
                player.sendMessage(plugin.color(plugin.getConfig().getString("message.vtp-usage")));
                return;
        }

        if (targetPlayer.getServer() == null) {
            player.sendMessage(plugin.color(plugin.getConfig().getString("message.teleport-failed").replace("{player}", targetPlayer.getName())));
            return;
        }

        basePlayer.connect(targetPlayer.getServer().getInfo(), (success, throwable) -> {
            if (success) {
                player.sendMessage(plugin.color(plugin.getConfig().getString("message.teleport").replace("{player}", targetPlayer.getName())));
            } else {
                player.sendMessage(plugin.color(plugin.getConfig().getString("message.teleport-failed").replace("{player}", targetPlayer.getName())));
            }
        });
    }
}