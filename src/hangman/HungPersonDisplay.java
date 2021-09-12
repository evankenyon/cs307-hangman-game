package hangman;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import runner.Main;

import java.util.HashMap;
import java.util.Map;

public class HungPersonDisplay {
    public static final int BOTTOM_GALLOWS_START_X = Main.SIZE/2 - 200;
    public static final int BOTTOM_GALLOWS_END_X = Main.SIZE/2 - 100;
    public static final int BOTTOM_GALLOWS_Y = Main.SIZE/2 + 100;
    public static final int VERTICAL_GALLOWS_X = (BOTTOM_GALLOWS_START_X + BOTTOM_GALLOWS_END_X)/2;
    public static final int VERTICAL_GALLOWS_END_Y = BOTTOM_GALLOWS_Y - 250;
    public static final int TOP_GALLOWS_END_X = VERTICAL_GALLOWS_X + 100;
    public static final int ROPE_GALLOWS_END_Y = VERTICAL_GALLOWS_END_Y + 50;
    public static final int HEAD_OFFSET_Y = 25;
    public static final int BODY_TOP_OFFSET_Y = 50;
    public static final int ARM_OFFSET_Y = 65;
    public static final int LEG_OFFSET_Y = 125;
    public static final int LIMB_END_OFFSET = 25;
    public static final int LETTER_X_OFFSET_X_ONE = 10;
    public static final int LETTER_X_OFFSET_X_TWO = 5;
    public static final int LETTER_X_OFFSET_Y_ONE = 15;
    public static final int LETTER_X_OFFSET_Y_TWO = 25;

    private Map<Integer, Node> guessesToBodyPart;

    /**
     * Purpose: Construct a HungPersonDisplay object that consists
     * Assumptions:
     */
    public HungPersonDisplay() {
        guessesToBodyPart = new HashMap<>();
        guessesToBodyPart.put(7, new Circle(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + HEAD_OFFSET_Y, HEAD_OFFSET_Y));
        guessesToBodyPart.put(6, new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + BODY_TOP_OFFSET_Y, TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + BODY_TOP_OFFSET_Y + 75));
        guessesToBodyPart.put(5, new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y, TOP_GALLOWS_END_X - LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y + LIMB_END_OFFSET));
        guessesToBodyPart.put(4, new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y, TOP_GALLOWS_END_X + LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + ARM_OFFSET_Y + LIMB_END_OFFSET));
        guessesToBodyPart.put(3, new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y, TOP_GALLOWS_END_X - LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y + LIMB_END_OFFSET));
        guessesToBodyPart.put(2, new Line(TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y, TOP_GALLOWS_END_X + LIMB_END_OFFSET, ROPE_GALLOWS_END_Y + LEG_OFFSET_Y + LIMB_END_OFFSET));
        produceLeftX();
        produceRightX();
    }

    public Node getBodyPart(int numGuessesLeft) {
        return guessesToBodyPart.get(numGuessesLeft);
    }

    public Group setupGallowsDisplay() {
        Line bottomGallows = new Line(BOTTOM_GALLOWS_START_X, BOTTOM_GALLOWS_Y, BOTTOM_GALLOWS_END_X, BOTTOM_GALLOWS_Y);
        Line verticalGallows = new Line(VERTICAL_GALLOWS_X, BOTTOM_GALLOWS_Y, VERTICAL_GALLOWS_X, VERTICAL_GALLOWS_END_Y);
        Line topGallows = new Line(VERTICAL_GALLOWS_X, VERTICAL_GALLOWS_END_Y, TOP_GALLOWS_END_X, VERTICAL_GALLOWS_END_Y);
        Line ropeGallows = new Line(TOP_GALLOWS_END_X, VERTICAL_GALLOWS_END_Y, TOP_GALLOWS_END_X, ROPE_GALLOWS_END_Y);
        return new Group(bottomGallows, verticalGallows, topGallows, ropeGallows);
    }

    private void produceLeftX() {
        Line leftXOne = new Line(TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE, TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO);
        Line leftXTwo = new Line(TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO, TOP_GALLOWS_END_X - LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE);
        leftXOne.setStroke(Color.RED);
        leftXTwo.setStroke(Color.RED);
        guessesToBodyPart.put(1, new Group(leftXOne, leftXTwo));
    }

    private void produceRightX() {
        Line rightXOne = new Line(TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE, TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO);
        Line rightXTwo = new Line(TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_TWO, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_TWO, TOP_GALLOWS_END_X + LETTER_X_OFFSET_X_ONE, ROPE_GALLOWS_END_Y + LETTER_X_OFFSET_Y_ONE);
        rightXOne.setStroke(Color.RED);
        rightXTwo.setStroke(Color.RED);
        guessesToBodyPart.put(0, new Group(rightXOne, rightXTwo));

    }


}
