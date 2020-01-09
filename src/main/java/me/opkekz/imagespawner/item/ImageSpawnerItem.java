package me.opkekz.imagespawner.item;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.eventbus.Subscribe;

import ibxm.Player;
import me.opkekz.imagespawner.reference.Reference;
import me.opkekz.imagespawner.utility.ColorKekz;
import me.opkekz.imagespawner.utility.GetRGB;
import me.opkekz.imagespawner.utility.RgbToEnum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ImageSpawnerItem extends Item{
	
	private ColorKekz[][] colorMatrix;
	public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);
	public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.<BlockPlanks.EnumType>create("variant", BlockPlanks.EnumType.class);
	private String imageName;
	private EntityPlayer currentPlayer;
	private IBlockState[][] blockMatrix;
	
	public ImageSpawnerItem() {
		
		super();
		this.setCreativeTab(CreativeTabs.TOOLS);
		//setUnlocalizedName(Reference.ImageSpawnerEnum.IMAGESPAWNER.getUnlocalizedName());
		setRegistryName(Reference.ImageSpawnerEnum.IMAGESPAWNER.getRegistryName());
		this.setUnlocalizedName(this.getRegistryName().toString());
		//initColorMatrix();
		
	}
	
	public void setImageName(String name) {
		this.imageName = name;
	}
	
	public void initColorMatrix() {
		if (imageName != null) {
			int[][] rgbMatrix = GetRGB.getValues(imageName);
			if (rgbMatrix == null && currentPlayer != null)
				currentPlayer.sendMessage(new TextComponentString("There was an error accessing the image."));
			else {
				colorMatrix = RgbToEnum.rgbToEnum(rgbMatrix);
				int result = buildBlockMatrix(colorMatrix);
				if (currentPlayer != null) {
					if (blockMatrix != null && result == 0)
						currentPlayer.sendMessage(new TextComponentString("Your image was loaded successfully!"));
					else
						currentPlayer.sendMessage(new TextComponentString("There was an error creating blockMatrix!"));
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("On item use:");
		tooltip.add("Build your image");
	}
	
	@Override
	/**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		currentPlayer = player;
		if (imageName != null && colorMatrix != null && blockMatrix != null) {
			for (int i = 0; i < colorMatrix.length; i++) {
				// build each line of the blockMatrix
				for (int j = 0; j < colorMatrix[0].length; j++) {

					if (colorMatrix[i][j] != null)
						//worldIn.setBlockState(new BlockPos(pos.getX() + j, pos.getY() + 1, pos.getZ() + i), Blocks.WOOL.getDefaultState().withProperty(COLOR, colorMatrix[i][j]));
						worldIn.setBlockState(new BlockPos(pos.getX() + j, pos.getY() + 1, pos.getZ() + i), blockMatrix[i][j]);
					else
						worldIn.setBlockState(new BlockPos(pos.getX() + j, pos.getY() + 1, pos.getZ() + i), Blocks.TNT.getDefaultState());
				}
			}
			if (worldIn.isRemote)
				player.sendMessage(new TextComponentString("Image build successfully!"));
		} else if (worldIn.isRemote) {
			player.sendMessage(new TextComponentString("There was an error building your image. Image name: " + imageName + "; colorMatrix: " + colorMatrix + "; blockMatrix: " + blockMatrix));
		}
			
		return EnumActionResult.PASS;
    }
	
	/**
	 * Creates a matrix of Blocks, each Block will represent a Pixel of the image.
	 * If an Enum is not recognized, a TNT Block will be set by default. 
	 * @param A matrix consisting of Enums, each representing a color
	 */
	public int buildBlockMatrix(ColorKekz[][] colorMatrix) {
		if (colorMatrix == null) {
			return -1;
		}
		int height = colorMatrix.length;
		int width = colorMatrix[0].length;
		IBlockState[][] blockMatrix = new IBlockState[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				switch (colorMatrix[i][j]) {
				
				case BLACK_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.BLACK);
					break;
				case RED_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.RED);
					break;
				case GREEN_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.GREEN);
					break;
				case BROWN_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.BROWN);
					break;
				case BLUE_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.BLUE);
					break;
				case PURPLE_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.PURPLE);
					break;
				case CYAN_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.CYAN);
					break;
				case SILVER_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.SILVER);
					break;
				case GRAY_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.GRAY);
					break;
				case PINK_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.PINK);
					break;
				case LIME_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.LIME);
					break;
				case YELLOW_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.YELLOW);
					break;
				case LIGHT_BLUE_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.LIGHT_BLUE);
					break;
				case MAGENTA_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.MAGENTA);
					break;
				case ORANGE_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.ORANGE);
					break;
				case WHITE_CLAY:
					blockMatrix[i][j] = Blocks.STAINED_HARDENED_CLAY.getDefaultState().withProperty(COLOR, EnumDyeColor.WHITE);
					break;
				case NETHER_BRICK:
					blockMatrix[i][j] = Blocks.NETHER_BRICK.getDefaultState();
					break;
				case EMERALD:
					blockMatrix[i][j] = Blocks.EMERALD_BLOCK.getDefaultState();
					break;
				case LAPIS:
					blockMatrix[i][j] = Blocks.LAPIS_BLOCK.getDefaultState();
					break;
				case DIAMOND:
					blockMatrix[i][j] = Blocks.DIAMOND_BLOCK.getDefaultState();
					break;
				case GOLD:
					blockMatrix[i][j] = Blocks.GOLD_BLOCK.getDefaultState();
					break;
				case QUARTZ:
					blockMatrix[i][j] = Blocks.QUARTZ_BLOCK.getDefaultState();
					break;
				case STONE:
					blockMatrix[i][j] = Blocks.STONE.getDefaultState();
					break;
				case CLAY:
					blockMatrix[i][j] = Blocks.CLAY.getDefaultState();
					break;
				case IRON:
					blockMatrix[i][j] = Blocks.IRON_BLOCK.getDefaultState();
					break;
				case ICE:
					blockMatrix[i][j] = Blocks.ICE.getDefaultState();
					break;
				case DIRT:
					blockMatrix[i][j] = Blocks.DIRT.getDefaultState();
					break;
				case GRASS:
					blockMatrix[i][j] = Blocks.GRASS.getDefaultState();
					break;
				case BROWN_MAP:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.DARK_OAK);
					break;
				case ADOBE:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.ACACIA);
					break;
				case JUNGLE:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.JUNGLE);
					break;
				case SAND:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.BIRCH);
					break;
				case OBSIDIAN:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.SPRUCE);
					break;
				case WOOD:
					blockMatrix[i][j] = Blocks.PLANKS.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.OAK);
					break;
				case WHITE:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.WHITE);
					break;
				case BLACK:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.BLACK);
					break;
				case RED:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.RED);
					break;
				case ORANGE:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.ORANGE);
					break;
				case MAGENTA:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.MAGENTA);
					break;
				case LIGHT_BLUE:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.LIGHT_BLUE);
					break;
				case YELLOW:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.YELLOW);
					break;
				case LIME:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.LIME);
					break;
				case PINK:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.PINK);
					break;
				case GRAY:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.GRAY);
					break;
				case SILVER:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.SILVER);
					break;
				case CYAN:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.CYAN);
					break;
				case PURPLE:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.PURPLE);
					break;
				case BLUE:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.BLUE);
					break;
				case BROWN:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.BROWN);
					break;
				case GREEN:
					blockMatrix[i][j] = Blocks.WOOL.getDefaultState().withProperty(COLOR, EnumDyeColor.GREEN);
					break;
				default:
					blockMatrix[i][j] = Blocks.TNT.getDefaultState();
				
				}
			}
		}
		this.blockMatrix = blockMatrix;
		return 0;
	}
	
	

}
