package dev.ethp.adminsu.common.command;

import dev.ethp.adminsu.base.command.AbstractCommandOfSubcommands;
import dev.ethp.adminsu.base.command.Command;
import dev.ethp.adminsu.base.platform.PlatformAdapter;
import dev.ethp.adminsu.base.platform.PlayerAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * /su
 * <p>
 * The top-level admin-su command.
 */
public class SuCommand extends AbstractCommandOfSubcommands {

	// -------------------------------------------------------------------------------------------------------------
	// Constructors:
	// -------------------------------------------------------------------------------------------------------------

	public SuCommand(PlatformAdapter<?, ?> platform) {
		super(platform, null);

		final Command about = new AboutCommand(platform);
		this.register("about", about);
		this.register("info", about, true);

		final Command check = new CheckCommand(platform);
		this.register("check", check);

		final Command disable = new DisableCommand(platform);
		this.register("disable", disable);
		this.register("off", disable, true);

		final Command enable = new EnableCommand(platform);
		this.register("enable", enable);
		this.register("on", enable, true);

		final Command reload = new ReloadCommand(platform);
		this.register("reload", reload);

		final Command toggle = new ToggleCommand(platform);
		this.register("toggle", toggle);
	}


	// -------------------------------------------------------------------------------------------------------------
	// Implementation: AbstractParentCommand
	// -------------------------------------------------------------------------------------------------------------

	@Override
	protected @NotNull String getDefault(@NotNull String label, @NotNull PlayerAdapter executor, @NotNull PlayerAdapter target) {
		return label.equalsIgnoreCase("su") ? "toggle" : "about";
	}

}
