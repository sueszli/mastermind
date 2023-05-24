import codedraw.*;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class Aufgabe1 {
    private static final int MAX_ROUNDS = 10;
    private static final int CODE_LENGTH = 4;

    private static final Color[] COLORS = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY, Color.RED, Color.PINK, Color.YELLOW};
    private static final int[] SOLUTION = IntStream.generate(() -> (int) (Math.random() * COLORS.length)).limit(CODE_LENGTH).toArray(); // -1: empty, 0-8: colors

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

        // set font for text
        TextFormat font = new TextFormat();
        font.setFontSize(40);
        font.setFontName("Arial");
        font.setTextOrigin(TextOrigin.CENTER);
        font.setBold(true);
        cd.setTextFormat(font);

        cd.drawText(cd.getWidth() / 2, cd.getHeight() / 2, text);
        cd.show(3000);
    }

    private static void initView(CodeDraw cd) {
        int rectWidth = cd.getWidth() / (CODE_LENGTH * 2 + 1);
        int rectHeight = cd.getHeight() / (COLORS.length + 1);
        int xPos = cd.getWidth() - rectWidth;

        // draw color selection
        IntStream.range(0, COLORS.length).forEach(i -> {
            int yPos = rectHeight * i;
            cd.setColor(COLORS[i]);
            cd.fillRectangle(xPos, yPos, rectWidth, rectHeight);
        });

        Path path = Paths.get("back_button.png");
        File file = path.toFile();
        if (!file.exists()) {
            System.out.println("OH NO");
        }

//        Image img = Image.fromFile("src/back_button.png");
//        cd.drawImage(xPos, rectHeight * COLORS.length, rectWidth, rectHeight, img);


        cd.show();
    }

    private static void updateView(CodeDraw cd, int currentRound, int[] guess, boolean[] evaluation) {

    }

    public static void main(String[] args) {
        int height = 800;
        int width = height + height / (COLORS.length + 1);
        var cd = new CodeDraw(width, height);
        cd.setTitle("master mind");
        var es = cd.getEventScanner();

        initView(cd);

        int currentRound = 0;
        int guessPosition = 0;
        while (true) {
            // leave game loop
            boolean isClosed = cd.isClosed();
            boolean isQuit = es.hasKeyPressEvent() && es.nextKeyPressEvent().getChar() == 'q';
            boolean isLastRound = currentRound == MAX_ROUNDS;
            if (isClosed || isQuit || isLastRound) {
                break;
            }

            // skip event
            boolean isMouseClick = es.hasMouseClickEvent();
            if (!isQuit && !isMouseClick) {
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

        }
        cd.close();

//        final var guess = new int[CODE_LENGTH];
//        final var evaluation = new boolean[CODE_LENGTH];
//        final var isSolved = IntStream.range(0, CODE_LENGTH).allMatch(i -> evaluations[currentRound][i]);
    }
}



