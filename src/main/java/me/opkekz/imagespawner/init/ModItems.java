package me.opkekz.imagespawner.init;

import java.util.HashSet;
import java.util.Set;

import me.opkekz.imagespawner.item.ImageSpawnerItem;
import me.opkekz.imagespawner.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=Reference.MOD_ID)
public class ModItems {
	
	public static ImageSpawnerItem imagespawner;
	
	public static void init() {
		
		imagespawner = new ImageSpawnerItem();
		//ForgeRegistries.ITEMS.register(imagespawner);
		
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(imagespawner);
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		registerRender(imagespawner);
	}
	
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
	}
	
}

