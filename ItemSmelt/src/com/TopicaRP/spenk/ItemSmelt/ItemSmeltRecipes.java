package com.TopicaRP.spenk.ItemSmelt;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;

public class ItemSmeltRecipes {
	
	public void addRecipe(FurnaceRecipes r){
			if (r.getBurnMaterialData() == -1){
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial()));
			return;
			}
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial(),r.getBurnMaterialData()));
			return;
	}
	
	public void loadRecipes(){
		ItemSmeltParseRecipes recipes = new ItemSmeltParseRecipes();
		for(FurnaceRecipes r : recipes.parseRecipes()){
			Logger.getLogger("minecraft").info(r.getBurnMaterial() +" "+r.getBurnMaterialData()+" "+r.getReturnItem().getTypeId()+" "+r.getReturnItem().getDurability());
			if (r.getBurnMaterialData() == -1){
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial()));
			}else{
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial(),r.getBurnMaterialData()));
			}
		}
		Logger.getLogger("minecraft").info("all furnace recipes are loaded!");
		return;
	}
	
}
