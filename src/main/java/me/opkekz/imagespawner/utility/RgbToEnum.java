package me.opkekz.imagespawner.utility;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import net.minecraft.item.EnumDyeColor;
import scala.actors.threadpool.Arrays;

public class RgbToEnum {
	
	/**
	 * 
	 * @param v1
	 * @param v2
	 * @return The cosine similarity of the given vectors
	 */
	private static double vectorSimilarity(int[] v1, int[] v2) {
		
		int zaehler = v1[0] * v2[0] + v1[1] * v1[1] + v1[2] * v2[2];
		double nenner = Math.sqrt(v1[0]*v1[0] + v1[1]*v1[1] + v1[2]*v1[2]) * Math.sqrt(v2[0]*v2[0] + v2[1]*v2[1] + v2[2]*v2[2]);
		
		return zaehler / nenner;
	}
	
	private static double cosineSimilarity(int[] vectorA, int[] vectorB) {
	    double dotProduct = 0.0;
	    double normA = 0.0;
	    double normB = 0.0;
	    for (int i = 0; i < vectorA.length; i++) {
	        dotProduct += vectorA[i] * vectorB[i];
	        normA += Math.pow(vectorA[i], 2);
	        normB += Math.pow(vectorB[i], 2);
	    }   
	    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}
	
	/**
	 * 
	 * @param first RGB value
	 * @param second RGB value
	 * @return similarity of the two values
	 */
	private static double rgbSimilarity(int[] v1, int[] v2) {
		double diffR = v2[0] - v1[0];
		double diffG = v2[1] - v1[1];
		double diffB = v2[2] - v1[2];
		return Math.sqrt(diffR * diffR + diffG * diffG + diffB * diffB);
		
	}
	
	/**
	 * Used in connection with {@link RgbToEnum#rgbSimilarity(int[], int[]) rgbSimilarity(int[] v1, int[] v2)}
	 * 
	 * @param array in which to search
	 * @return index of smallest value
	 */
	private static int findSmallestValue(double[] array) {
		double smallest = array[0];
		int position = 0;
		for (int l = 0; l < array.length; l++) {
			if (array[l] < smallest) {
				smallest = array[l];
				position = l;
			}
		}
		return position;
	}
	
	/**
	 * Used in connection with {@link RgbToEnum#vectorSimilarity(int[], int[]) vectorSimilarity(int[] v1, int[] v2)}
	 * 
	 * @param array in which to search
	 * @return index of biggest value
	 */
	private static int findBiggestValue(double[] array) {
		double biggest = array[0];
		int position = 0;
		for (int l = 0; l < array.length; l++) {
			if (array[l] > biggest) {
				biggest = array[l];
				position = l;
			}
		}
		return position;
	}
	
	/**
	 * 
	 * @param int[][] rgbMatrix
	 * @return Matrix containing {@link ColorKekz ColorKekz.class} that correspond to a color
	 */
	public static ColorKekz[][] rgbToEnum(int[][] rgbMatrix) {
		
		if (rgbMatrix == null)
			return null;
		
		System.out.println(Arrays.toString(ColorKekz.values()));
		int height = rgbMatrix.length;
		int width = rgbMatrix[0].length;
		ColorKekz[][] colorMatrix = new ColorKekz[height][width];
		
		// save the RGB values separately in a vector for each DyeColor
		int k = 0;
		int[][] enumRGBVectors = new int[ColorKekz.values().length][3];
		for (ColorKekz color : ColorKekz.values()) {
			
			// take the RGB value and then isolate each color
			int valueEnum = color.getColorValue();
			int redE = valueEnum & 0xff0000;
			redE = redE >> 16; // SHR 16
			int greenE = valueEnum & 0x00ff00;
			greenE = greenE >> 8; // SHR 8
			int blueE = valueEnum & 0x0000ff;
			int[] rgbArrayEnum = { redE, greenE, blueE};
			
			enumRGBVectors[k][0] = redE;
			enumRGBVectors[k][1] = greenE;
			enumRGBVectors[k][2] = blueE;
			k++;
			
		}
		
		// set an Color enum for every pixel of the given matrix
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// get the current RGB value, then isolate the red, green and blue values
				int value = rgbMatrix[i][j];
				int red = value & 0xff0000;
				red = red >> 16; // SHR 16
				int green = value & 0x00ff00;
				green = green >> 8; // SHR 8
				int blue = value & 0x0000ff;
				int[] rgbArray = { red, green, blue};
				
				// if value is 0, don't calculate vectorSimilarity (div by 0)
				if (value == 0) {
					colorMatrix[i][j] = ColorKekz.BLACK;
					continue;
				}
				
				// for debug purposes:
//				if (i == 49 && j == 112)
//					System.out.println(i);
				
				// try to filter out white pixels separately
				if (red >= 250 && green >= 250 && blue >= 230 || red >= 250 && green >= 230 && blue >= 250 || red >= 230 && green >= 250 && blue >= 250) {
					colorMatrix[i][j] = ColorKekz.WHITE;
					continue;
				}
				
				// if the red, green and blue values are all similar (i.e. RGB(10,10,10)), skip the similarity calculation
				if (red - green > -15 && red - green < 15 && red - blue > -15 && red - blue < 15) {
					
					// find out if the current color is black, white or gray
					if (red < 40) {
						colorMatrix[i][j] = ColorKekz.BLACK;
						continue;
					} else if (red < 150) {
						colorMatrix[i][j] = ColorKekz.GRAY;
						continue;
					} else if (red <= 210) {
						colorMatrix[i][j] = ColorKekz.SILVER;
						continue;
					} else {
						colorMatrix[i][j] = ColorKekz.WHITE;
						continue;
					}
				}
				
				// try to filter out edge cases such as RGB(255, 255, 0) that might mess up the calculation due to slight rounding errors
				if (red > 245 && green > 245 && blue <= 10) {
					colorMatrix[i][j] = ColorKekz.YELLOW;
					continue;
				} else if (red >= 245 && green <= 10 && blue >= 245) {
					colorMatrix[i][j] = ColorKekz.MAGENTA;
					continue;
				} else if (red <= 10 && green >= 245 && blue >= 245) {
					colorMatrix[i][j] = ColorKekz.LIGHT_BLUE;
					continue;
				}
				
				int lauf = 0;
				// calculate the similarity between the given rgbVector and every color available as a wool color
				double[] similarites = new double[ColorKekz.values().length];
				for (ColorKekz color : ColorKekz.values()) {
					
					if (color == ColorKekz.BLACK)
						if (red >= 50 || green >= 50 || blue >= 50) {
							// MAX_VALUE because we currently search for the smallest distance
							similarites[lauf] = Integer.MAX_VALUE;
							lauf++;
							continue;
						}
					
					similarites[lauf] = rgbSimilarity(rgbArray, enumRGBVectors[lauf]);
					lauf++;
					
				}
				// choose the smallest value, in other words the vector of the given wool colors
				// that has the smallest distance from our current rgbValue
				colorMatrix[i][j] = ColorKekz.values()[findSmallestValue(similarites)];

			}
		}
		
		return colorMatrix;
		
	}

}
