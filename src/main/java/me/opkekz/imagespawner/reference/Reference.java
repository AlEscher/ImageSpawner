package me.opkekz.imagespawner.reference;

public class Reference {
	
	public static final String MOD_ID = "imagespawner";
	public static final String MOD_NAME = "ImageSpawner";
	public static final String VERSION = "1.12.2-1.0";
	public static final String CLIENT_PROXY = "me.opkekz.imagespawner.proxy.ClientProxy";
	public static final String SERVER_PROXY = "me.opkekz.imagespawner.proxy.ServerProxy";
	
	public static enum ImageSpawnerEnum {
		IMAGESPAWNER("imagespawner");
		
		private String unlocalizedName;
		private String registryName;
		
		ImageSpawnerEnum(String registryName) {
			this.registryName = registryName;
		}
		
		public String getRegistryName() {
			return registryName;
		}
		
//		public String getUnlocalizedName() {
//			return unlocalizedName;
//		}
	}

}
