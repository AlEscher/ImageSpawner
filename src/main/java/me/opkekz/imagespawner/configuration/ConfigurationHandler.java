package me.opkekz.imagespawner.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {
	
	public static void init(File configFile) {
		
	Configuration configuration  = new Configuration(configFile);
	
	try {
		
		// Load the configuration file
		configuration.load();
		
		//Read in properties from configuration file
		boolean configValue = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, true, "Test");
		
	} catch (Exception e) {
		
		// Log the exception
		
	} finally {
		
		// save the configuration file
		configuration.save();
	}
		
	}

}
