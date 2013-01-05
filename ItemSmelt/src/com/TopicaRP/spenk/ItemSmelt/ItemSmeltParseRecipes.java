package com.TopicaRP.spenk.ItemSmelt;

import java.util.ArrayList;
import java.util.Map;

public class ItemSmeltParseRecipes {
	public ArrayList<FurnaceRecipes> parseRecipes(){
		Map<String,String> fileRecipes = new ItemSmeltFile().getRecipes();
		ArrayList<FurnaceRecipes> recipes = new ArrayList<FurnaceRecipes>();
		
		for(String s : fileRecipes.keySet()){
			String[] fromData = s.split("/");
			String[] toData = fileRecipes.get(s).split("/");
			int fromID = Integer.parseInt(fromData[0]);
			short fromDamage = Short.parseShort(fromData[1]);
			
			int toID = Integer.parseInt(toData[0]);
			short toDamage = Short.parseShort(toData[1]);
			
			FurnaceRecipes r = new FurnaceRecipes(fromID, fromDamage, toID, toDamage);
				recipes.add(r);
		}
		return recipes;
	}
	
}
