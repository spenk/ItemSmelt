package com.TopicaRP.spenk.ItemSmelt;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FurnaceRecipes {
	
	public int fromID;
	public short fromDamage;
	public int toID;
	public short toDamage;
	public int[] defaultSmeltItems = {319,363,365,349,392,153,129,73,56,21,16,15,14,4,12,337,87,17,81};
	public ItemSmeltFile f = new ItemSmeltFile();
	
	public FurnaceRecipes(int fromID, short fromDamage, int toID, short toDamage){
		this.fromID = fromID;
		this.fromDamage = fromDamage;
		this.toID = toID;
		this.toDamage = toDamage;
	}
	
	public ItemStack getReturnItem() {
		ItemStack result;
		if (toDamage != -1) {
			result = new ItemStack(toID, 1, toDamage);
		} else {
			result = new ItemStack(toID, 1);
		}
		return result;
	}
	
	public ItemStack getInputItem() {
		ItemStack result;
		if (fromDamage != -1) {
			result = new ItemStack(fromID, 1, fromDamage);
		} else {
			result = new ItemStack(fromID, 1);
		}
		return result;
	}
	
	public Material getBurnMaterial(){
		return Material.getMaterial(fromID);
	}
	
	public short getBurnMaterialData(){
		return fromDamage;
	}
	
	public boolean isValidRecipe(){
		for(int i : defaultSmeltItems){
			if (i == fromID){
				return false;
			}
			if (f.getUsedIDs().contains(fromID)){
				return false;
			}
		}
		return true;
	}
	
}
