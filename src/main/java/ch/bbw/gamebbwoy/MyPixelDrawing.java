package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.ButtonListener;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;
import ch.bbw.gamebbwoy.Classes.backgrounds;

public class MyPixelDrawing implements PixelDrawing, ButtonListener {
	private int hallwayNumber = 0;

	public static void main(String[] args) throws Throwable {
		GameBbwoy.playGame(new MyPixelDrawing());
		
	}

	@Override
	public void tick(PixelDisplay graphic) {
		graphic.clear();
		
		

		int[][] activeHallway;
		if (hallwayNumber == 0) {
			activeHallway = backgrounds.hallway;
		} else if (hallwayNumber == 1) {
			activeHallway = backgrounds.hallway2;
		} else {
			activeHallway = backgrounds.hallway3;
		} 
		drawScaledSprite(graphic, activeHallway, 0, 0, graphic.getPixelWidth(), graphic.getPixelHeight());

	}

	@Override
	public void onButtonPress(ButtonListener.GameButton button) {
		if (button == ButtonListener.GameButton.UP) {
			hallwayNumber = 1;
		}
	}

	@Override
	public void onButtonRelease(ButtonListener.GameButton button) {
	}

	static void drawSprite(PixelDisplay graphic, int [][] sprite, int xOffset, int yOffset) {
		for (int y = 0; y < sprite.length; y++) {
			for (int x = 0; x < sprite[y].length; x++) {
				var colorNumber = sprite[y][x];
				if (colorNumber == 4) {
					continue;
				}
				var color = PixelColor.fromValue((colorNumber));
				graphic.setPixel(x + xOffset, y + yOffset, color);
			}
		}
	}

	static void drawScaledSprite(PixelDisplay graphic, int[][] sprite, int xOffset, int yOffset, int targetWidth, int targetHeight) {
		for (int y = 0; y < targetHeight; y++) {
			int sourceY = y * sprite.length / targetHeight;
			for (int x = 0; x < targetWidth; x++) {
				int sourceX = x * sprite[sourceY].length / targetWidth;
				var colorNumber = sprite[sourceY][sourceX];
				if (colorNumber == 4) {
					continue;
				}
				var color = PixelColor.fromValue(colorNumber);
				graphic.setPixel(x + xOffset, y + yOffset, color);
			}
		}
	}
}
