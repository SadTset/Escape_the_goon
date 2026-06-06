package ch.bbw.gamebbwoy;
import java.util.ArrayDeque;
import java.util.Random;

import ch.bbw.gamebbwoy.Classes.Hallway;
import ch.bbw.gamebbwoy.Classes.HallwayLeft;
import ch.bbw.gamebbwoy.Classes.HallwayRight;
import ch.bbw.gamebbwoy.Classes.desk;
import ch.bbw.gamebbwoy.Classes.everythingelse;
import ch.bbw.gamebbwoy.api.ButtonListener;
import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;
import ch.bbw.gamebbwoy.internal.GameBbwoy;

public class MyPixelDrawing implements PixelDrawing, ButtonListener {
	private int backgroundNumber = 0;
	private boolean titleScreen = true;
	private final Random hallwayRandom = new Random();
 	private final ArrayDeque<Integer> hallwayHistory = new ArrayDeque<>();
	public static void main(String[] args) throws Throwable {
		GameBbwoy.playGame(new MyPixelDrawing());
		
	}

	@Override
	public void tick(PixelDisplay graphic) {
    graphic.clear();

	if (titleScreen) {
		drawScaledSprite(
			graphic,
			everythingelse.title,
			0,
			0,
			graphic.getPixelWidth(),
			graphic.getPixelHeight()
		);
		return;
	}

    int[][] background = switch (backgroundNumber) {
        case 0 -> Hallway.hallway;
        case 1 -> Hallway.hallway2;
        case 2 -> Hallway.hallway3;
        case 3 -> HallwayRight.hallwayRight;
        case 4 -> HallwayLeft.hallwayLeft;
		case 5 -> Hallway.hallwayCross;
		case 6 -> Hallway.hallwayDeadEnd;
		case 7 -> desk.deskStart;
        default -> Hallway.hallway;
    };

    drawScaledSprite(
            graphic,
            background,
            0,
            0,
            graphic.getPixelWidth(),
            graphic.getPixelHeight()
    );
}


	@Override
public void onButtonPress(ButtonListener.GameButton button) {

	if (titleScreen && button == ButtonListener.GameButton.SPACE) {
		titleScreen = false;
		backgroundNumber = 7;
		return;
	}

	if (backgroundNumber == 7 && button == ButtonListener.GameButton.SPACE) {
		backgroundNumber = 0;
		return;
	}

    if (button == ButtonListener.GameButton.DOWN) {
        if (!hallwayHistory.isEmpty()) {
            backgroundNumber = hallwayHistory.pop();
        }
        return;
    }

    boolean canMove =
            (button == ButtonListener.GameButton.UP && (backgroundNumber == 0 || backgroundNumber == 1 || backgroundNumber == 2))
            || (button == ButtonListener.GameButton.RIGHT && backgroundNumber == 3)
            || (button == ButtonListener.GameButton.LEFT && backgroundNumber == 4)
            || ((button == ButtonListener.GameButton.LEFT || button == ButtonListener.GameButton.RIGHT) && backgroundNumber == 5);

    if (canMove) {
        hallwayHistory.push(backgroundNumber);
        backgroundNumber = hallwayRandom.nextInt(7);
    }
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
