# admin-si

A zero-configuration plugin for toggling admin permissions (like cheats).
This plugin is great for when you want to play on your survival server without cheats.

## Requirements

- LuckPerms is highly recommended.

## Features

The admin-su plugin is intended to be used with the [LuckPerms](https://www.spigotmc.org/resources/luckperms.28140/) permission plugin,
and it provides an extra `su=true` context for when players are in admin mode.
This allows for highly flexible configurations that don't differentiate between admins and players, unless admin cheats are needed for moderation purposes.   

If an unsupported permissions plugin is being used (anything that isn't LuckPerms), this plugin will instead toggle the player's `/op` status on and off.

**Other features:**
- Completely configurable messages.yml file.

## Commands

### /su toggle
**Alias:** `/su`  
**Permission:** `adminsu.toggle`

Toggles between admin and player mode.

### /su enable
**Alias:** `/su on`  
**Permission:** `adminsu.toggle`

Turns on admin mode.

### /su disable
**Alias:** `/su off`  
**Permission:** `adminsu.toggle`

Turns off admin mode.

### /su check
**Permission:** `adminsu.check`

Checks the current admin status of another player.

### /su reload
**Permission:** `adminsu.reload`

Reloads the localization messages.

## Permissions

|Permission|Description|
|:--|:--|
|`adminsu.toggle`|Gives access to `/su toggle`, `/su on`, and `/su off`.|  
|`adminsu.check`|Gives access to `/su check <player>`.|  
|`adminsu.check.broadcast`|Receive a message whenever another player enters admin mode. Type `/su` to get a response back.|
|`adminsu.reload`|Gives access to `/su reload`|
