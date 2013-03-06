package com.TopicaRP.spenk.ItemSmelt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Material;

public class ItemSmeltFile {
	
	int fromID;
	short fromDamage = -1;
	int toID;
	short toDamage = -1;
	
	public ItemSmeltFile(){}
	
	public ItemSmeltFile(int fromID, short fromDamage, int toID, short toDamage){
		this.fromID = fromID;
		this.fromDamage = fromDamage;
		this.toID = toID;
		this.toDamage = toDamage;
	}
	
	PropertiesFile f = new PropertiesFile("plugins/config/ItemSmelt.txt");
	
	public void createFiles(){
		File file = new File("plugins/config");
		File f = new File("plugins/config/ItemSmelt.txt");
		if (!f.exists()){
			file.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public FurnaceRecipes getFullRecipe(){
		String s = f.getString(fromID+"/"+fromDamage);
		String[] split = s.split("/");
		int toid = Integer.parseInt(split[0]);
		short todamage = Short.parseShort(split[1]);
		return new FurnaceRecipes(fromID,fromDamage,toid,todamage);
	}
	
	public void addRecipe(){
		f.getString(fromID+"/"+fromDamage,toID+"/"+toDamage);
	}
	
	public void removeRecipe(){
		f.removeKey(fromID+"/"+fromDamage);
	}
	
	public boolean existsRecipe(){
		return f.containsKey(fromID+"/"+fromDamage);
	}
	
	public String getBurnItemName(){
		return Material.getMaterial(fromID).name().toLowerCase();
	}
	
	public String getResultName(){
		return Material.getMaterial(Integer.parseInt(f.getString(fromID+"/"+fromDamage).split("/")[0])).name().toLowerCase();
	}
	
	public Map<String,String> getRecipes(){
		try {
			return f.returnMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Integer> getUsedIDs(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		Map<String,String> m = getRecipes();
		for(String i : m.keySet()){
			int id = Integer.parseInt(i.split("/")[0]);
			list.add(id);
		}
		return list;
	}

}
