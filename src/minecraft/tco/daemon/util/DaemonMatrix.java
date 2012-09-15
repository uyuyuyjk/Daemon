package tco.daemon.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ItemTool;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import tco.daemon.ItemDagger;
import tco.daemon.ItemOrb;

public enum DaemonMatrix {
	DEFAULT,
	CRAFT,
	SMELT,
	CHARGE,
	CONDUCT,
	CRAFTMATRIX;
	
	public static final int MATRIX_DIM = 4;
	public static final int MATRIX_SIZE = MATRIX_DIM * MATRIX_DIM;

	public static final Map<Item, Integer> itemMap = new HashMap<Item, Integer>();
	
	public static void initialize(){
		addDeath(Item.bone);
		addDeath(Item.gunpowder);
		addDeath(Item.enderPearl);
		
		addDecay(Item.rottenFlesh);
		addDecay(Item.beefRaw);
		addDecay(Item.porkRaw);
		
		addDisease(Item.spiderEye);
		addDisease(Item.chickenRaw);
		addDisease(Item.feather);
	}
	
	public static void addDeath(Item item){
		itemMap.put(item, 0x1);
	}
	public static void addDecay(Item item){
		itemMap.put(item, 0x2);
	}
	public static void addDisease(Item item){
		itemMap.put(item, 0x4);
	}
	
	public static DaemonMatrix getType(ISidedInventory matrix){
		ItemStack stack = matrix.getStackInSlot(DaemonMatrix.MATRIX_SIZE - 1);
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemOrb) {
				return CRAFTMATRIX;
			}
			if (item instanceof ItemDagger) {
				return CONDUCT;
			}
			if (item == Item.coal) {
				return SMELT;
			}
			if (item instanceof ItemTool) {
				return CRAFT;
			}
		}
		return DEFAULT;
	}
		
	public static ItemStack getStorageItem(ISidedInventory matrix){
		ItemStack stack = matrix.getStackInSlot(DaemonMatrix.MATRIX_DIM - 1);
		if(stack != null && stack.getItem() instanceof IDaemonEnergyStorage) {
			return stack;
		}
		return null;
	}
	
	public static DaemonEnergy calculateEnergy(ISidedInventory matrix, ForgeDirection side){
		int death, decay, disease;
		death = decay = disease = 0;
		
		//total number of each item
		Map<Item, Integer> itemTally = new HashMap<Item, Integer>();
		//stacks of items present
		List<Item> stackList = new ArrayList<Item>();
		
		//sets of the different stacks of items and the total number of each item
		int start = matrix.getStartInventorySide(side);
		int size = matrix.getSizeInventorySide(side);
		for(int i = 0; i < size; i++){
			ItemStack stack = matrix.getStackInSlot(start + i);
			if(stack == null) continue;
			Item item = stack.getItem();
			stackList.add(item);
			if(!itemTally.containsKey(item)){
				itemTally.put(item, stack.stackSize);
			}else{
				int iTotal = itemTally.get(item);
				itemTally.put(item, iTotal + stack.stackSize);
			}
		}

		//only include stacks of items with > 1 total items in the inventory
		for (Item item : stackList) {
			if (itemMap.containsKey(item) && itemTally.containsKey(item) && itemTally.get(item) > 1) {
				int type = itemMap.get(item);
				if ((type & 0x1) > 0)
					death++;
				if ((type & 0x2) > 0)
					decay++;
				if ((type & 0x4) > 0)
					disease++;
			}
		}

		return new DaemonEnergy(death, decay, disease, death + decay + disease);
	}
	
	public static DaemonEnergy calculateEnergy(ISidedInventory matrix){
		return calculateEnergy(matrix, ForgeDirection.DOWN);
	}
		
	public static int calculateInstability(ISidedInventory matrix, ForgeDirection side){
		int instability = 0;
		
		int start = matrix.getStartInventorySide(side);
		
		//across
		for(int i = 0; i < MATRIX_DIM; i++){
			for(int j = 0; j < MATRIX_DIM; j++){
				ItemStack stack = matrix.getStackInSlot(start + j + MATRIX_DIM * i);
				if(stack == null) continue;
				for(int k = j; k < MATRIX_DIM; k++){
					ItemStack stack2 = matrix.getStackInSlot(start + k + MATRIX_DIM * i);
					if(stack2 == null) continue;
					if(stack.getItem() == stack2.getItem()){
						instability++;
					}
				}
			}
		}
		
		//down
		for(int i = 0; i < MATRIX_DIM; i++){
			for(int j = 0; j < MATRIX_DIM; j++){
				ItemStack stack = matrix.getStackInSlot(start + i + MATRIX_DIM * j);
				if(stack == null) continue;
				for(int k = j; k < MATRIX_DIM; k++){
					ItemStack stack2 = matrix.getStackInSlot(start + i + MATRIX_DIM * k);
					if(stack2 == null) continue;
					if(stack.getItem() == stack2.getItem()){
						instability++;
					}
				}
			}
		}

		return instability;
	}
	
	public static int calculateInstability(ISidedInventory matrix){
		return calculateInstability(matrix, ForgeDirection.DOWN);
	}
	
}
