import codedraw.*;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Aufgabe1 {
    private static final int MAX_ROUNDS = 10;
    private static final int CODE_LENGTH = 4;

    private static final Color[] COLORS = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY, Color.RED, Color.PINK, Color.YELLOW};

    private static void renderMessage(CodeDraw cd, String text, Color color) {
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

    private static void render(CodeDraw cd, int[][] guesses, int[][] hints) {
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

        // draw guess circles
        final int circleRadius = Math.min(blockWidth, blockHeight) / 2;
        final int circleSpacing = circleRadius * 2;
        IntStream.range(0, MAX_ROUNDS).forEach(row -> {
            IntStream.range(0, CODE_LENGTH).forEach(col -> {
                // outer circle
                cd.setColor(Palette.BLACK);
                cd.fillCircle(circleRadius + col * circleSpacing, circleRadius + row * circleSpacing, circleRadius);

                // inner circle
                if (guesses[row][col] == -1) {
                    cd.setColor(Palette.WHITE);
                } else {
                    cd.setColor(COLORS[guesses[row][col]]);
                }
                cd.fillCircle(circleRadius + col * circleSpacing, circleRadius + row * circleSpacing, circleRadius - 1);
            });
        });

        // draw hint circles (before current round)
        final int currentRound = IntStream.range(0, MAX_ROUNDS).filter(i -> guesses[i][0] != -1).max().orElse(0);
        boolean inProgress = IntStream.range(0, CODE_LENGTH).anyMatch(i -> guesses[currentRound][i] == -1);
        Function<Integer, Color> hintToColor = hint -> {
            if (hint == 0) {
                return Palette.LIGHT_GRAY;
            } else if (hint == 1) {
                return Palette.WHITE;
            } else {
                return Palette.RED;
            }
        };
        final int sizeReduction = 25;
        IntStream.range(0, inProgress ? currentRound : currentRound + 1).forEach(row -> {
            IntStream.range(0, CODE_LENGTH).forEach(col -> {
                cd.setColor(hintToColor.apply(hints[row][col]));
                cd.fillCircle(circleRadius + col * circleSpacing + (double) availableWidth / 2, circleRadius + row * circleSpacing, circleRadius - sizeReduction);
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

        Supplier<int[]> solutionGenerator = () -> {
            int[] s = IntStream.generate(() -> (int) (Math.random() * COLORS.length)).limit(CODE_LENGTH).toArray();
            System.out.println("generated solution: " + Arrays.toString(s));
            return s;
        };

        int[] solution = solutionGenerator.get();
        int[][] guesses = new int[MAX_ROUNDS][CODE_LENGTH]; // -1: empty, 0-8: colors
        Arrays.setAll(guesses, i -> Arrays.stream(guesses[i]).map(j -> -1).toArray());
        int[][] hints = new int[MAX_ROUNDS][CODE_LENGTH]; // 0: empty, 1: white, 2: red

        render(cd, guesses, hints);

        int currentRound = 0;
        int guessPosition = 0;
        while (true) {
            // leave game loop
            boolean isClosed = cd.isClosed();
            boolean isQuit = es.hasKeyPressEvent() && es.nextKeyPressEvent().getChar() == 'q';
            if (isClosed || isQuit) {
                break;
            }

            // skip event
            boolean isMouseClick = es.hasMouseClickEvent();
            if (!isMouseClick) {
                es.nextEvent();
                continue;
            }

            // check if user input is valid
            var click = es.nextMouseClickEvent();
            final int rectWidth = cd.getWidth() / (CODE_LENGTH * 2 + 1);
            boolean isValidClick = click.getX() >= cd.getWidth() - rectWidth;
            if (!isValidClick) {
                System.out.println("invalid click");
                continue;
            }

            // update guess array
            final int rectHeight = cd.getHeight() / (COLORS.length + 1);
            final int choice = click.getY() / rectHeight;
            if (choice == COLORS.length) {
                boolean isDeletePossible = guessPosition > 0;
                if (!isDeletePossible) {
                    System.out.println("delete not possible");
                    continue;
                }
                // delete color
                guesses[currentRound][guessPosition - 1] = -1;
                guessPosition--;

            } else {
                boolean isColorInGuess = Arrays.stream(guesses[currentRound]).anyMatch(x -> x == choice);
                if (isColorInGuess) {
                    System.out.println("color already in guess");
                    continue;
                }
                // add color
                guesses[currentRound][guessPosition] = choice;
                guessPosition++;
            }

            // update hint array
            final int cr = currentRound;
            int[] s = solution;
            hints[cr] = IntStream.range(0, CODE_LENGTH).map(i -> guesses[cr][i] == s[i] ? 2 : (Arrays.stream(s).anyMatch(x -> x == guesses[cr][i]) ? 1 : 0)).toArray();
            System.out.println("guess: " + Arrays.toString(guesses[cr]) + " --> hint: " + Arrays.toString(hints[cr]) + " (round " + currentRound + ")");

            // render
            render(cd, guesses, hints);

            // check if round is over
            if (guessPosition != CODE_LENGTH) {
                continue;
            }

            // check if game is over
            boolean solved = IntStream.range(0, CODE_LENGTH).allMatch(i -> hints[cr][i] == 2);
            boolean lastRound = currentRound == MAX_ROUNDS - 1;
            if (!solved && !lastRound) {
                guessPosition = 0;
                currentRound++;
                System.out.println("starting next round --");
                continue;
            }

            // restart game
            if (solved) {
                renderMessage(cd, "You won!", Color.GREEN);
            } else {
                renderMessage(cd, "You lost!", Color.RED);
            }
            IntStream.iterate(MAX_ROUNDS - 1, i -> i - 1).limit(MAX_ROUNDS).forEach(i -> {
                Arrays.fill(guesses[i], -1);
                Arrays.fill(hints[i], 0);
                render(cd, guesses, hints);
                cd.show(400);
            });
            currentRound = 0;
            guessPosition = 0;
            System.out.println("restarting game --");
        }
        cd.close();
    }
}
