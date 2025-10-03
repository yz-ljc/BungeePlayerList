package top.yzljc.BungeePlayerList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class BungeePlayerList extends Plugin implements Listener {

    private static Logger logger;
    private static ProxyServer proxyServer;
    private static Configuration config;
    private static File dataDirectory;

    @Override
    public void onEnable() {
        logger = getLogger();
        proxyServer = getProxy();
        dataDirectory = getDataFolder();

        saveDefaultConfig();
        loadConfig();

        // 注册指令
        proxyServer.getPluginManager().registerCommand(this, new CommandOnlines(this));
        proxyServer.getPluginManager().registerCommand(this, new CommandVTP(this));
        proxyServer.getPluginManager().registerCommand(this, new CommandVPL(this));

        // 注册事件监听
        proxyServer.getPluginManager().registerListener(this, this);

        logger.info("BungeePlayerList 已启用!");
    }

    public static Configuration getConfig() {
        return config;
    }

    public static ProxyServer getProxyServer() {
        return proxyServer;
    }

    public static String color(String msg) {
        if (msg == null) return "";
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void reloadConfig() {
        loadConfig();
    }

    private void loadConfig() {
        try {
            File configFile = new File(dataDirectory, "config.yml");
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            logger.info("Successfully loaded config file");
        } catch (IOException e) {
            logger.severe("Could not load config file: " + e.getMessage());
        }
    }

    private void saveDefaultConfig() {
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }
        File configFile = new File(dataDirectory, "config.yml");
        if (!configFile.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                if (in != null) {
                    Files.copy(in, configFile.toPath());
                }
            } catch (IOException e) {
                logger.severe("Could not save default config file: " + e.getMessage());
            }
        }
    }

    // 玩家加入事件
    @net.md_5.bungee.event.EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (config.getBoolean("options.player-join-notify", true)) {
            for (ProxiedPlayer target : proxyServer.getPlayers()) {
                if (target.hasPermission("bpl.announce")) {
                    // 判断是否为staff
                    if (player.hasPermission("bpl.staff")) {
                        target.sendMessage(color("&b[STAFF] &c[ADMIN] " + player.getName() + " &e加入了服务器"));
                    } else {
                        target.sendMessage(color(config.getString("message.join", "&7[&a+&7] {player}").replace("{player}", player.getName())));
                    }
                }
            }
        }
    }

    // 玩家离开事件
    @net.md_5.bungee.event.EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        boolean isStaff = player.hasPermission("bpl.staff"); // 先保存权限状态

        if (config.getBoolean("options.player-leave-notify", true)) {
            for (ProxiedPlayer target : proxyServer.getPlayers()) {
                if (target.hasPermission("bpl.announce")) {
                    if (isStaff) {
                        target.sendMessage(color("&b[STAFF] &c[ADMIN] " + player.getName() + " &e离开了服务器"));
                    } else {
                        target.sendMessage(color(config.getString("message.leave", "&7[&c-&7] {player}").replace("{player}", player.getName())));
                    }
                }
            }
        }
    }
}