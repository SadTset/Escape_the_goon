package ch.bbw.gamebbwoy;
import java.util.Random;

import ch.bbw.gamebbwoy.Classes.Hallway;
import ch.bbw.gamebbwoy.Classes.HallwayLeft;
import ch.bbw.gamebbwoy.Classes.HallwayRight;
import ch.bbw.gamebbwoy.api.ButtonListener;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

public class MyPixelDrawing implements PixelDrawing, ButtonListener {
	private int hallwayNumber = 0;

	public static void main(String[] args) throws Throwable {
		GameBbwoy.playGame(new MyPixelDrawing());
		
	}

	@Override
	public void tick(PixelDisplay graphic) {
		graphic.clear();
		
		

		int[][] activeHallway = Hallway.hallway;
		if (hallwayNumber == 0) {
			activeHallway = Hallway.hallway2;
		} else if (hallwayNumber == 1) {
			activeHallway = Hallway.hallway3;
		} else if (hallwayNumber == 2){
			activeHallway = HallwayRight.hallwayRight;
		} else if (hallwayNumber == 3) {
			activeHallway = HallwayLeft.hallwayLeft;
		} else if (hallwayNumber == 4) {
			activeHallway = Hallway.hallwayCross;
		} 
		drawScaledSprite(graphic, activeHallway, 0, 0, graphic.getPixelWidth(), graphic.getPixelHeight());

	}

	@Override
	public void onButtonPress(ButtonListener.GameButton button) {
		int oldHallwayNumber = hallwayNumber;
		if (button == ButtonListener.GameButton.UP && hallwayNumber == 0) {
			hallwayNumber = 0;
		} else if (button == ButtonListener.GameButton.LEFT && hallwayNumber == 3) {
			hallwayNumber = 3;
		}
		else if (button == ButtonListener.GameButton.RIGHT && hallwayNumber== 2 ) {
			hallwayNumber = 2;
		} else if (button == ButtonListener.GameButton.DOWN && hallwayNumber == 1) {
			hallwayNumber = 1;
		} else if (button == ButtonListener.GameButton.UP && hallwayNumber == 0) {
			hallwayNumber = 0;
		} else if (button == ButtonListener.GameButton.LEFT || button == ButtonListener.GameButton.RIGHT) {
			hallwayNumber = 4;
		}

	Random hallwayRandom = new Random();
		hallwayNumber = ((button == ButtonListener.GameButton.UP && hallwayNumber == 0) || (button == ButtonListener.GameButton.LEFT && hallwayNumber == 3) || (button == ButtonListener.GameButton.RIGHT && hallwayNumber == 2) || (button == ButtonListener.GameButton.DOWN && hallwayNumber == 1) || (button == ButtonListener.GameButton.UP && hallwayNumber == 0)) || ((button == ButtonListener.GameButton.LEFT || button == ButtonListener.GameButton.RIGHT) && oldHallwayNumber == 4) ? hallwayRandom.nextInt(5) : oldHallwayNumber;
	}

	@Override
	public void onButtonRelease(ButtonListener.GameButton button) {
	}

	/*static void drawSprite(PixelDisplay graphic, int [][] sprite, int xOffset, int yOffset) {
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
*/
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
