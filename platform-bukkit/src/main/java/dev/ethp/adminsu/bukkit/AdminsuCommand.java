package dev.ethp.adminsu.bukkit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dev.ethp.adminsu.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static dev.ethp.adminsu.bukkit.PluginPermissions.PERMISSION_SU_TOGGLE;

public class AdminsuCommand implements CommandExecutor {

	private final Plugin adminsu;
	private final Map<String, CommandExecutor> subcommands;
	
	public final SuCheck SUBCOMMAND_CHECK;
	public final SuDisable SUBCOMMAND_DISABLE;
	public final SuEnable SUBCOMMAND_ENABLE;
	public final SuToggle SUBCOMMAND_TOGGLE;
	public final About SUBCOMMAND_ABOUT;
	public final Reload SUBCOMMAND_RELOAD;

	public AdminsuCommand(Plugin adminsu) {
		this.adminsu = adminsu;
		
		SUBCOMMAND_CHECK = new SuCheck(adminsu);
		SUBCOMMAND_DISABLE = new SuDisable(adminsu);
		SUBCOMMAND_ENABLE = new SuEnable(adminsu);
		SUBCOMMAND_TOGGLE = new SuToggle(adminsu);
		SUBCOMMAND_ABOUT = new About(adminsu);
		SUBCOMMAND_RELOAD = new Reload(adminsu);
		
		this.subcommands = new HashMap<>();
		this.subcommands.put("about", SUBCOMMAND_ABOUT);
		this.subcommands.put("info", SUBCOMMAND_ABOUT);
		this.subcommands.put("reload", SUBCOMMAND_RELOAD);
		this.subcommands.put("check", SUBCOMMAND_CHECK);
		this.subcommands.put("disable", SUBCOMMAND_DISABLE);
		this.subcommands.put("off", SUBCOMMAND_DISABLE);
		this.subcommands.put("enable", SUBCOMMAND_ENABLE);
		this.subcommands.put("on", SUBCOMMAND_ENABLE);
		this.subcommands.put("toggle", SUBCOMMAND_TOGGLE);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if ((sender instanceof Player) && sender.hasPermission(PERMISSION_SU_TOGGLE) && label.equalsIgnoreCase("su")) {
				args = new String[] { "toggle" };
			} else {
				args = new String[] { "about" };
			}
		}

		// Find the subcommand.
		CommandExecutor subcommand = this.subcommands.get(args[0].toLowerCase());
		if (subcommand == null) {
			adminsu.i18n().ERROR_UNKNOWN_COMMAND.param("command", label).send(sender);
			return false;
		}
		
		// Execute the subcommand.
		return subcommand.onCommand(sender, command, label + " " + args[0], Arrays.copyOfRange(args, 1, args.length));
	}

}
