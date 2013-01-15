package com.TopicaRP.spenk.ItemSmelt;

import java.util.Map;
import java.util.logging.Logger;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemSmelt extends JavaPlugin {

	public String name = "ItemSmelt";
	public double version = 1.0;
	public Logger log = Logger.getLogger("Minecraft");
	public Permission perms = null;
	ItemSmeltFile f = new ItemSmeltFile();

	public void onDisable() {
		this.log.info(name + " version " + version + " By spenk is disabled.");
	}

	public void onEnable() {
		if (!this.isPermissionsEnabled()) {
			log.info("[iSmelt] Error no vault found! disabeling iSmelt!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		ItemSmeltRecipes recipes = new ItemSmeltRecipes();
		f.createFiles();
		recipes.loadRecipes();
		this.log.info(name + " version " + version + " By spenk is enabled.");
	}

	public boolean isPermissionsEnabled() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		if (rsp == null) {
			return false;
		}
		perms = rsp.getProvider();
		return perms != null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("ismelt")){
			
			if (!perms.has(sender, "ItemSmelt.admin")){
				sender.sendMessage("§cYou are not allowed to use this command!");
				return true;
			}

			if (args.length == 1){			
				if (args[0].equalsIgnoreCase("reload")){
					ItemSmeltRecipes recipes = new ItemSmeltRecipes();
					f.createFiles();
					recipes.loadRecipes();
					sender.sendMessage("§2Recipes reloaded!");
					return true;
				}
				if(args[0].equalsIgnoreCase("list")){
					Map<String, String> recipes = f.getRecipes();
					sender.sendMessage("§aFromID/FromDamage §b/§a ToID/ToDamage");
					for (String s : recipes.keySet()){
						sender.sendMessage("§a"+s+"§b / §a"+recipes.get(s));
					}
					return true;
				}
			}
			
			if (args.length == 2){			
				if (args[0].equalsIgnoreCase("remove")){
					if (isNumber(args[1])){
						int fromID;
						short fromDamage;
						if (!args[1].contains("/")){
							fromID = Integer.parseInt(args[1]);
							fromDamage = -1;
						}else{
							fromID = Integer.parseInt(args[1].split("/")[0]);
							fromDamage = Short.parseShort(args[1].split("/")[1]);
						}
						ItemSmeltFile r = new ItemSmeltFile(fromID, fromDamage,0,(short)0);
						ItemSmeltRecipes handler = new ItemSmeltRecipes();
						if (r.existsRecipe()){
							r.removeRecipe();
							sender.sendMessage("§2Recipe removed.");
							return true;
						}
						sender.sendMessage("§cThis recipe does not exist!");
						return true;
					}
					
				}
				if (args[0].equalsIgnoreCase("check")){
					if (isNumber(args[1])){
						int fromID;
						short fromDamage;
						if (!args[1].contains("/")){
							fromID = Integer.parseInt(args[1]);
							fromDamage = -1;
						}else{
							fromID = Integer.parseInt(args[1].split("/")[0]);
							fromDamage = Short.parseShort(args[1].split("/")[1]);
						}	
						ItemSmeltFile r = new ItemSmeltFile(fromID, fromDamage,0,(short)0);
						if (r.existsRecipe()){
							sender.sendMessage("§6" + r.getBurnItemName() + "§2" + " turns into §4" + r.getResultName()+".");
							return true;
						}
						sender.sendMessage("§cThis recipe does not exist!");
						return true;
					}
				}
			}
			
			if (args.length == 3){			
				if (args[0].equalsIgnoreCase("add")){
					if (isNumber(args[1]) && isNumber(args[2])){
						int fromID;
						short fromDamage;
						int toID;
						short toDamage;
						
						if (!args[1].contains("/")){
							fromID = Integer.parseInt(args[1]);
							fromDamage = -1;
						}else{
							fromID = Integer.parseInt(args[1].split("/")[0]);
							fromDamage = Short.parseShort(args[1].split("/")[1]);
						}
						
						if (!args[2].contains("/")){
							toID = Integer.parseInt(args[2]);
							toDamage = -1;
						}else{
							toID = Integer.parseInt(args[2].split("/")[0]);
							toDamage = Short.parseShort(args[2].split("/")[1]);
						}
						
						ItemSmeltFile f = new ItemSmeltFile(fromID,fromDamage,toID,toDamage);
						FurnaceRecipes r = new FurnaceRecipes(fromID,fromDamage,toID,toDamage);
						if (!r.isValidRecipe()){
						 sender.sendMessage("§cThis recipe already exists!");
						 return true;
						}
						ItemSmeltRecipes recipe = new ItemSmeltRecipes();
						f.addRecipe();
						recipe.addRecipe(r);
						sender.sendMessage("§2Recipe added.");
						return true;
					}
				}
			}
			help(sender);
			return true;
		}
		return false;
	}
	
	public boolean isNumber(String num){
		if (num.contains("/")){
			boolean s1 = checkNumber(num.split("/")[0]);
			boolean s2 = checkNumber(num.split("/")[0]);
			return s1 && s2;
		}else{
		return this.checkNumber(num);	
		}
		
	}
	
	public boolean checkNumber(String n){
		try{
			Integer.parseInt(n);
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
    private void help(CommandSender sender)
    {
      sender.sendMessage("§ciSmelt Commands/ §2/ismelt reload§c - reloads iSmelt config.");
      sender.sendMessage("§2/ismelt add (from id)/(from damage) (to id)/(to damage)§c - adds a new smeltable.");
      sender.sendMessage("§2/ismelt remove (fromID/fromDamage)§c - removes a smeltable.");
      sender.sendMessage("§2/ismelt check (from id)§c - checks to see what a smeltable item turns into.");
      sender.sendMessage("§2/ismelt list§c - lists all smeltables added by iSmelt.");
    }
	
}
