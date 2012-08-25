package tco.daemon;

public class ItemDagger extends ItemDaemon {

	protected ItemDagger(int id) {
		super(id);
		setMaxStackSize(1);
		setFull3D();
		this.setItemName("daggerSacrifice");
	}

}
