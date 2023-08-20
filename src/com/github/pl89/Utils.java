package com.github.pl89;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	public static void createDataFile(String fileName, double[] data) {
		try {
			File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			for (int i = 0; i < data.length; ++i) {
				fileWriter.write(String.format("%.2f%n", data[i]));
			}
			fileWriter.close();
			System.out.printf("Successfully wrote file '%s'.%n", fileName);
		} catch (IOException e) {
			System.out.printf("An error occurred when attempting to create file '%s'.%n", fileName);
			e.printStackTrace();
		}
	}

	public static void createGraphFile(String fileName, BufferedImage bufferedImage) {
		try {
			File graphFile = new File(fileName);
			ImageIO.write(bufferedImage, "png", graphFile);
		} catch (IOException e) {
			System.out.println("An error occurred creating graph image file.");
			e.printStackTrace();
		}
	}
}
