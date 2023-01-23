package ru.elmanov.curmulate.logics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.awt.*;

public class Logics {

    private static Logger logger = LogManager.getLogger(Logics.class);

    private static Integer cursorX;
    private static Integer cursorY;

    public static final int SHIFT = 30;
    public static final int STEP = 2;

    private static Robot robot;
    private static Direction direction;

    public Logics() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
        init();
    }

    private void init() {
        direction = Direction.LEFT;

        Pair<Integer, Integer> screenSize = getScreenSize();
        cursorX = screenSize.getValue0() / 2;
        cursorY = screenSize.getValue1() / 2;
        robot.mouseMove(cursorX, cursorY);
    }

    private Pair<Integer, Integer> getScreenSize() {
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDisplayMode();
        return Pair.with(displayMode.getWidth(), displayMode.getHeight());
    }

    public static void run() {
        try {
            while (true) {
                switch (direction) {
                    case UP:
                        moveCursorStepByStep(0, -STEP);
                        direction = Direction.RIGHT;
                        break;
                    case RIGHT:
                        moveCursorStepByStep(STEP, 0);
                        direction = Direction.DOWN;
                        break;
                    case DOWN:
                        moveCursorStepByStep(0, STEP);
                        direction = Direction.LEFT;
                        break;
                    case LEFT:
                        moveCursorStepByStep(-STEP, 0);
                        direction = Direction.UP;
                        break;
                    default:
                        System.out.println("not found direction");
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            logger.info("FINISH");
        }
    }

    private static void moveCursorStepByStep(Integer stepX, Integer stepY) throws InterruptedException {
        for (int count = 0; count < SHIFT; count += STEP) {
            cursorX += stepX;
            cursorY += stepY;
            robot.mouseMove(cursorX, cursorY);
            Thread.sleep(1000);
        }
    }

}
