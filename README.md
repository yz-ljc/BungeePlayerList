# 此插件是TryStage服务器的VelocityPlayerList改编版本，支持BungeeCord端，以下介绍文件直接抄的他们的懒得改

# VelocityPlayerList

## 📌 Overview
**VelocityPlayerList** is a powerful utility plugin for [Velocity](https://velocitypowered.com/) proxy servers that enhances player management and visibility across your network. Developed by TrystageBedwars, this plugin provides essential tools for server administrators and staff members to monitor and interact with players efficiently.

---

## ✨ Features

### 🔗 Cross-Server Teleportation
- `/vtp <player>` — Instantly teleport to the sub-server where the target player is located  
  _Requires `vpl.teleport` permission_
- Customizable error messages for invalid targets

### 👥 Player Tracking
- `/onlines [server]` — View player lists with flexible options:  
  - Shows all players network-wide when no server specified  
  - Filters by specific sub-server when provided  
  _Requires `vpl.onlines` permission_
- Displays formatted player counts

### 🔔 Join/Leave Notifications
- Customizable player join/leave messages
- Displays in proxy chat when players connect/disconnect
- _Requires `vpl.announce` permission to see notifications_
- Fully configurable message formats

---

## ⚙️ Configuration

The plugin uses `config.toml` with these sections:

<details>
<summary>Example: <code>config.toml</code></summary>

```toml
#  ██████  ██████ ██████████████ ██████
#  ██░░██  ██░░██ ██░░░░░░░░░░██ ██░░██
#  ██░░██  ██░░██ ██░░██████░░██ ██░░██
#  ██░░██  ██░░██ ██░░██  ██░░██ ██░░██
#  ██░░██  ██░░██ ██░░██████░░██ ██░░██
#  ██░░██  ██░░██ ██░░░░░░░░░░██ ██░░██
#  ██░░██  ██░░██ ██░░██████████ ██░░██
#  ██░░░░██░░░░██ ██░░██         ██░░██
#  ████░░░░░░████ ██░░██         ██░░██████████
#    ████░░████   ██░░██         ██░░░░░░░░░░██
#      ██████     ██████         ██████████████
#
# VelocityPlayerList 1.0 by TrystageBedwars
# Website: https://github.com/4C01/VelocityPlayerList
#
# Thanks for using VelocityPlayerList!  |  TrystageBedwars
#
# Permission:
#   vpl.announce - Saw the player join/leave message
#   vpl.teleport - /vtp <player>
#   vpl.onlines  - /onlines

[options]
player-join-notify = true
player-leave-notify = true

[message]
split-format = ","
join = "<gray>[<green>+<gray>] {player}"
leave = "<gray>[<red>-<gray>] {player}"
onlines = "<green>Theres {count} players online"
vtp-usage = "<red>Usage: /vtp <player>"
null-player = "<red>Theres no player that name"
teleport = "<gray>[<yellow>↑<gray>] {player}..."
teleport-failed = "<red>Failed to teleport to target player's server"
console-run-command = "<red>Only player can run this command."
noperm-run-command = "<red>You do not have permission to run this command."
server-not-exists = "<red>Server does not exist"
```
</details>

---

## 🔐 Permission Nodes

| Permission       | Description                                 |
| ---------------- | ------------------------------------------- |
| `vpl.announce`   | Receive player join/leave notifications     |
| `vpl.teleport`   | Access to `/vtp` command                    |
| `vpl.onlines`    | Access to `/onlines` command                |

---

## 📥 Installation

1. **Download** the latest jar from [GitHub Releases](https://github.com/4C01/VelocityPlayerList/releases)
2. **Place** it in your Velocity `plugins` folder
3. **Restart** the proxy server
4. **Configure** `config.toml` to your preferences

---

## 📝 Credits

- Plugin by [TrystageBedwars](https://github.com/4C01)
- Inspired by network management needs

---
