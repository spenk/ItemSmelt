package com.TopicaRP.spenk.ItemSmelt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;

public class ItemSmeltRecipes {

	public void addRecipe(FurnaceRecipes r){
			if (r.getBurnMaterialData() == -1){
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial()));
			return;
			}
			Bukkit.addRecipe(new FurnaceRecipe(r.getReturnItem(),r.getBurnMaterial(),r.getBurnMaterialData()));
			return;
	}
	
	public void removeRecipe(FurnaceRecipes r){
		ArrayList<Recipe> toret = new ArrayList<Recipe>();
		Iterator<Recipe> recipes = Bukkit.recipeIterator();
		while(recipes.hasNext()){
			Recipe e = recipes.next();
			if (e instanceof FurnaceRecipe){
				FurnaceRecipe i =(FurnaceRecipe)e;
				if (i.getInput() == r.getInputItem() && i.getResult() == r.getReturnItem()){
					
				}
			}else{
				toret.add(e);
			}
		}
		Bukkit.clearRecipes();
		/**for (Recipe recip : toret){
			Bukkit.addRecipe(recip);
		}*/
		Logger.getLogger("minecraft").info("all recipes are reloaded!");//TODO void not ready yet!
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
