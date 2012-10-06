package tco.daemon;

import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WrongUsageException;
import tco.daemon.util.ReferenceConfigs;
import tco.daemon.util.UtilItem;
import tco.daemon.util.energy.DaemonEnergy;

public class CommandDaemon extends CommandBase {

	@Override
	public String getCommandName() {
		return "daemon";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + getCommandName() + " <version|configuration|charge> [arguments]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] command) {
		if (command.length > 0) {
			if ("version".equals(command[0])) {
				sender.sendChatToPlayer(ReferenceConfigs.FULL_VERSION);
			} else if("configuration".equals(command[0])) {
				sender.sendChatToPlayer(ReferenceConfigs.getConfigString());
			} else if("charge".equals(command[0])) {
				InventoryPlayer inv = ((EntityPlayerMP) sender).inventory;
				int orbSlot = DaemonEnergy.getFirstStorage(inv);
				if(orbSlot > -1) {
					ItemStack orb = inv.getStackInSlot(orbSlot);
					DaemonEnergy energy = UtilItem.getDaemonEnergy(orb);
					energy.maxEnergy = 9000000;
					energy.chargeEnergy(3000000, 3000000, 3000000);
					UtilItem.setDaemonEnergy(orb, energy);
					sender.sendChatToPlayer("Super charged container");
				} else {
					sender.sendChatToPlayer("No energy container found");
				}
			} else if("dimension".equals(command[0])) {
				((EntityPlayerMP) sender).mcServer.getConfigurationManager()
				.transferPlayerToDimension((EntityPlayerMP) sender, ModDaemon.instance.dimensionId);
			} else {
				throw new WrongUsageException(getCommandUsage(sender));
			}
		} else {
			sender.sendChatToPlayer(ReferenceConfigs.FULL_VERSION);
		}
	}

}
