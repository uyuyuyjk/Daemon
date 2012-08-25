package tco.daemon;

import net.minecraft.src.*;

public class ItemDaemon extends Item {

	protected ItemDaemon(int id) {
		super(id);
		setTextureFile("/tco/daemon/sprites/daemonitems.png");
		setTabToDisplayOn(CreativeTabs.tabMisc);
	}

}
