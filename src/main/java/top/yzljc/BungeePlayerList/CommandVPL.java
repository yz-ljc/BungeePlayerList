package top.yzljc.BungeePlayerList;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandVPL extends Command {

    private final BungeePlayerList plugin;

    public CommandVPL(BungeePlayerList plugin) {
        super("bpl", null, new String[0]);
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // 仅玩家权限判断，控制台无需权限
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (!player.hasPermission("bpl.admin")) {
                player.sendMessage(plugin.color(plugin.getConfig().getString("message.noperm-run-command")));
                return;
            }
        }
        plugin.reloadConfig();
        sender.sendMessage(plugin.color(plugin.getConfig().getString("message.reloaded")));
    }
}
