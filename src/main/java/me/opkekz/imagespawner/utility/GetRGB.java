package me.opkekz.imagespawner.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class GetRGB {

	public static int[][] getValues(String imageName) {
		int stepSize = 1;

		try {
			BufferedImage image = ImageIO.read(new File(imageName));
			int width = image.getWidth();
			int height = image.getHeight();
			int[][] rgbValues = new int[height][width];
			
//			if (width > 500 && height > 500) {
//				stepSize = 2;
//				rgbValues = new int[height / 2][width / 2];
//			} else
//				rgbValues = new int[height][width];
			
			System.out.println("Width = " + width + "; Height = " + height);

			int x = 0;
			int y = 0;
			for (int h = 0; h < height; h += stepSize) {
				for (int w = 0; w < width; w+= stepSize) {
					// copy each pixel into the matrix
					rgbValues[h][w] = image.getRGB(w, h);
					
					// discard the first upper 8 bit (0xffffffff -> 0xffffff) to get only the RGB values
					rgbValues[h][w] = rgbValues[h][w] & 0x00ffffff;
				}
			}
			//printMatrix(rgbValues);
			return rgbValues;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong while creating the RGB matrix");
		}
		return null;

	}

	private static void printMatrix(int[][] matrix) {
		for (int h = 0; h < matrix.length; h++) {
			for (int w = 0; w < matrix[h].length; w++) {
				System.out.print(Integer.toHexString(matrix[h][w]));
			}
			System.out.println("");
		}
	}


}
