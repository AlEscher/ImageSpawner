package me.opkekz.imagespawner;

import me.opkekz.imagespawner.configuration.ConfigurationHandler;
import me.opkekz.imagespawner.init.ModItems;
import me.opkekz.imagespawner.item.ImageSpawnerItem;
import me.opkekz.imagespawner.proxy.IProxy;
import me.opkekz.imagespawner.reference.Reference;
import me.opkekz.imagespawner.utility.ImageSpawnerCommand;
import net.minecraft.item.Item;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION)
public class ImageSpawner {
	
	@Mod.Instance(Reference.MOD_ID)
	public static ImageSpawner instance;
	
	@SidedProxy(clientSide=Reference.CLIENT_PROXY, serverSide=Reference.SERVER_PROXY)
	public static IProxy proxy;
	
	@EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ImageSpawnerCommand());
    }

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		ModItems.init();
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		//proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
