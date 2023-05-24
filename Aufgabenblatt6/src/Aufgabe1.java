import codedraw.*;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Aufgabe1 {
    private static final int MAX_ROUNDS = 10;
    private static final int CODE_LENGTH = 4;

    private static final Color[] COLORS = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY, Color.RED, Color.PINK, Color.YELLOW};
    private static final int[] SOLUTION = IntStream.generate(() -> (int) (Math.random() * COLORS.length)).limit(CODE_LENGTH).toArray();

    private static void showMessage(CodeDraw cd, String text, Color color) {
        int rectWidth = cd.getWidth() / 2;
        int rectHeight = cd.getHeight() / 6;
        int xPos = cd.getWidth() / 4;
        int yPos = cd.getHeight() / 2 - rectHeight / 2;

        cd.setColor(Palette.LIGHT_GRAY);
        cd.fillRectangle(xPos, yPos, rectWidth, rectHeight);
        cd.setColor(Palette.BLACK);
        cd.drawRectangle(xPos, yPos, rectWidth, rectHeight);
        cd.setColor(color);

        TextFormat font = new TextFormat();
        font.setFontSize(40);
        font.setFontName("Arial");
        font.setTextOrigin(TextOrigin.CENTER);
        font.setBold(true);
        cd.setTextFormat(font);

        cd.drawText((double) cd.getWidth() / 2, (double) cd.getHeight() / 2, text);
        cd.show(3000);
    }

    private static void drawState(CodeDraw cd) {
        // background
        cd.setColor(Palette.LIGHT_GRAY);
        cd.fillRectangle(0, 0, cd.getWidth(), cd.getHeight());

        // draw color selection
        final int rectWidth = cd.getWidth() / (CODE_LENGTH * 2 + 1);
        final int rectHeight = cd.getHeight() / (COLORS.length + 1);
        final int xPos = cd.getWidth() - rectWidth;
        IntStream.range(0, COLORS.length).forEach(i -> {
            final int yPos = rectHeight * i;
            cd.setColor(COLORS[i]);
            cd.fillRectangle(xPos, yPos, rectWidth, rectHeight);
        });
        final var path = "./Aufgabenblatt6/src/back_button.png";
        final var img = Image.fromFile(path);
        cd.drawImage(xPos, rectHeight * COLORS.length, rectWidth, rectHeight, img);

        // calculate space for remaining blocks
        final int availableWidth = cd.getWidth() - rectWidth;
        final int availableHeight = cd.getHeight();
        final int blockWidth = availableWidth / (CODE_LENGTH * 2);
        final int blockHeight = availableHeight / MAX_ROUNDS;

        // draw user input circles
        final int circleRadius = Math.min(blockWidth, blockHeight) / 2;
        final int circleSpacing = circleRadius * 2;
        IntStream.range(0, MAX_ROUNDS).forEach(row -> {
            IntStream.range(0, CODE_LENGTH).forEach(col -> {
                // outer circle
                cd.setColor(Palette.BLACK);
                cd.fillCircle(circleRadius + col * circleSpacing, circleRadius + row * circleSpacing, circleRadius);

                // inner circle
                cd.setColor(Palette.WHITE);
                cd.fillCircle(circleRadius + col * circleSpacing, circleRadius + row * circleSpacing, circleRadius - 1);
            });
        });

        // draw hint circles
        final int sizeReduction = 25;
        IntStream.range(0, MAX_ROUNDS).forEach(row -> {
            IntStream.range(0, CODE_LENGTH).forEach(col -> {
                cd.setColor(Palette.BLACK);
                cd.fillCircle(circleRadius + col * circleSpacing + availableWidth / 2, circleRadius + row * circleSpacing, circleRadius - sizeReduction);
            });
        });


        cd.show();
    }


    public static void main(String[] args) {
        int height = 800;
        int width = height + height / (COLORS.length + 1);
        var cd = new CodeDraw(width, height);
        cd.setTitle("master mind");
        var es = cd.getEventScanner();


        drawState(cd);
        System.out.println("solution: " + Arrays.toString(SOLUTION));


        int[][] guesses = new int[MAX_ROUNDS][CODE_LENGTH]; // -1: empty, 0-8: colors
        int[][] hints = new int[MAX_ROUNDS][CODE_LENGTH]; // 0: empty, 1: white, 2: red

        int currentRound = 0;
        int guessPosition = 0;
        while (true) {
            // leave game loop
            boolean isClosed = cd.isClosed();
            boolean isQuit = es.hasKeyPressEvent() && es.nextKeyPressEvent().getChar() == 'q';
            boolean noRoundsLeft = currentRound == MAX_ROUNDS;
            if (isClosed || isQuit || noRoundsLeft) {
                break;
            }

            // skip event
            boolean isMouseClick = es.hasMouseClickEvent();
            if (!isMouseClick) {
                es.nextEvent();
                continue;
            }

            // get user input
            var click = es.nextMouseClickEvent();
            int mouseX = click.getX();
            int mouseY = click.getY();

            // check if valid
            boolean isValidClick = true;
            if (!isValidClick) {
                System.out.println("invalid click detected");
                continue;
            }
            System.out.println("valid click detected");
            guessPosition = (guessPosition + 1) % CODE_LENGTH;
            currentRound++;

            // update state based on user input
            // click on delete -> delete
            // click on color -> check if color is already in guess, if not add color to guess

            int[] guess = new int[CODE_LENGTH];
            int[] hint = IntStream.range(0, CODE_LENGTH).map(i -> guess[i] == SOLUTION[i] ? 2 :
                    (Arrays.stream(SOLUTION).anyMatch(x -> x == guess[i]) ? 1 : 0))
                    .toArray();

//        Supplier<Boolean> isSolved = () -> IntStream.range(0, CODE_LENGTH).allMatch(i -> evaluations[currentRound][i]);

        }
        cd.close();
    }
}



