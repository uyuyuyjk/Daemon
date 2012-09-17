package tco.daemon;

import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;
import tco.daemon.util.ReferenceConfigs;

public class CommandDaemon extends CommandBase {

	@Override
	public String getCommandName() {
		return "daemon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName() + " <version|configuration> [arguments]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] command) {
		if (command.length > 0) {
			if ("version".equals(command[0])) {
				sender.sendChatToPlayer(ReferenceConfigs.FULL_VERSION);
			} else if("configuration".equals(command[0])) {
				sender.sendChatToPlayer(ReferenceConfigs.getConfigs());
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
			}
		} else {
			sender.sendChatToPlayer(ReferenceConfigs.FULL_VERSION);
		}
	}

}
