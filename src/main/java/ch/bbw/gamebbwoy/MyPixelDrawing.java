package ch.bbw.gamebbwoy;
import java.util.ArrayDeque;
import java.util.Random;

import ch.bbw.gamebbwoy.Classes.Hallway;
import ch.bbw.gamebbwoy.Classes.HallwayLeft;
import ch.bbw.gamebbwoy.Classes.HallwayRight;
import ch.bbw.gamebbwoy.Classes.desk;
import ch.bbw.gamebbwoy.Classes.everythingelse;
import ch.bbw.gamebbwoy.Classes.props;
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
	private boolean powerOn = false;
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
		case 8 -> everythingelse.blackout;
		case 9 -> props.leverOff;
		case 10 -> props.leverOn;
		case 11 -> everythingelse.theGoon;
		case 12 -> everythingelse.gameOver;
		case 13 -> desk.deskEnd;
		case 14 -> everythingelse.uWon;
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
        if (powerOn) {
            backgroundNumber = 13;
        } else {
            hallwayHistory.push(backgroundNumber);
            backgroundNumber = 8;
        }
        return;
    }

    if (backgroundNumber == 8 && button == ButtonListener.GameButton.SPACE) {
        backgroundNumber = 0;
        return;
    }

    if (backgroundNumber == 9 && button == ButtonListener.GameButton.SPACE) {
        powerOn = true;
        backgroundNumber = 10;
        return;
    }

    if (backgroundNumber == 11 && button == ButtonListener.GameButton.SPACE) {
        backgroundNumber = 12;
        return;
    }

    if (backgroundNumber == 13 && button == ButtonListener.GameButton.SPACE) {
        backgroundNumber = 14;
        return;
    }

    if ((backgroundNumber == 12 || backgroundNumber == 14) && button == ButtonListener.GameButton.SPACE) {
        titleScreen = true;
        backgroundNumber = 0;
        powerOn = false;
        hallwayHistory.clear();
        return;
    }

   if (button == ButtonListener.GameButton.DOWN) {
    if (!hallwayHistory.isEmpty()) {
        backgroundNumber = hallwayHistory.pop();

        if (backgroundNumber == 7 && powerOn) {
            backgroundNumber = 13;
        }
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

        if (hallwayRandom.nextInt(12) == 0) {
            backgroundNumber = 11;
            return;
        }

        int randomBackground = hallwayRandom.nextInt(powerOn ? 7 : 8);

        if (randomBackground == 7) {
            backgroundNumber = 9;
        } else {
            backgroundNumber = randomBackground;
        }
    }
}

@Override
public void onButtonRelease(ButtonListener.GameButton button) {
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
