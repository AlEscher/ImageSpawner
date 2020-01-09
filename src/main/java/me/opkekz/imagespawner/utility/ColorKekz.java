package me.opkekz.imagespawner.utility;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A set of enums represent colors, these enums are used for the color-similarity calculation in {@link RgbToEnum#rgbToEnum(int[][]) rgbToEnum(int[][] matrix)}
 * @author OPKekz
 *
 */
public enum ColorKekz {
	
	/**
	 * These were taken from {@link MapColor MapColor.class}
	 */
	WOOD(9402184),
	OBSIDIAN(8476209),
	SAND(16247203),
	DIRT(9923917),
	ADOBE(14188339),
	BROWN_MAP(6704179),
	JUNGLE(16032864),
	GRASS(8368696),
	ICE(10526975),
	IRON(10987431),
	CLAY(10791096),
	STONE(7368816),
	QUARTZ(16776437),
	GOLD(16445005),
	DIAMOND(6085589),
	LAPIS(4882687),
	EMERALD(55610),
	NETHER_BRICK(7340544),
	WHITE_CLAY(13742497),
	ORANGE_CLAY(10441252),
	MAGENTA_CLAY(9787244),
	LIGHT_BLUE_CLAY(7367818),
	YELLOW_CLAY(12223780),
	LIME_CLAY(6780213),
	PINK_CLAY(10505550),
	GRAY_CLAY(3746083),
	SILVER_CLAY(8874850),
	CYAN_CLAY(5725276),
	PURPLE_CLAY(8014168),
	BLUE_CLAY(4996700),
	BROWN_CLAY(4993571),
	GREEN_CLAY(5001770),
	RED_CLAY(9321518),
	BLACK_CLAY(2430480),
	/**
	 * These were taken from {@link EnumDye EnumDyeColor.class}:
	 */
	WHITE(16383998),
    ORANGE(16351261),
    MAGENTA(13061821),
    LIGHT_BLUE(3847130),
    YELLOW(16701501),
    LIME(8439583),
    PINK(15961002),
    GRAY(4673362),
    SILVER(10329495),
    CYAN(1481884),
    PURPLE(8991416),
    BLUE(3949738),
    BROWN(8606770),
    GREEN(6192150),
    RED(11546150),
    BLACK(1908001);
	private final int colorValue;
	
	private ColorKekz(int colorValue) {
		this.colorValue = colorValue;
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorValue() {
		return colorValue;
	}

}
