package top.yzljc.BungeePlayerList;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.ProxyServer;

import java.util.List;
import java.util.stream.Collectors;

public class CommandOnlines extends Command {

    private final BungeePlayerList plugin;

    public CommandOnlines(BungeePlayerList plugin) {
        super("bonlines", null, new String[0]);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // 权限检查（仅玩家需要，控制台不需要）
        if (sender instanceof ProxiedPlayer && !sender.hasPermission("bpl.onlines")) {
            sender.sendMessage(plugin.color(plugin.getConfig().getString("message.noperm-run-command")));
            return;
        }

        // 存在参数，查指定服务器
        if (args.length > 0) {
            String serverName = args[0];
            if (ProxyServer.getInstance().getServerInfo(serverName) == null) {
                sender.sendMessage(plugin.color(plugin.getConfig().getString("message.server-not-exists")
                        .replace("{server}", serverName)));
                return;
            }
            List<ProxiedPlayer> players = ProxyServer.getInstance().getServerInfo(serverName).getPlayers().stream().collect(Collectors.toList());
            sender.sendMessage(plugin.color(plugin.getConfig().getString("message.onlines")
                    .replace("{count}", String.valueOf(players.size()))));
            String delimiter = plugin.getConfig().getString("message.split-format", ",");
            String playerNames = players.stream()
                    .map(ProxiedPlayer::getName)
                    .collect(Collectors.joining(delimiter));
            sender.sendMessage(plugin.color(playerNames));
        } else {
            // 查全服
            int total = ProxyServer.getInstance().getPlayers().size();
            sender.sendMessage(plugin.color(plugin.getConfig().getString("message.onlines")
                    .replace("{count}", String.valueOf(total))));
            String delimiter = plugin.getConfig().getString("message.split-format", ",");
            String playerNames = ProxyServer.getInstance().getPlayers().stream()
                    .map(ProxiedPlayer::getName)
                    .collect(Collectors.joining(delimiter));
            sender.sendMessage(plugin.color(playerNames));
        }
    }
}