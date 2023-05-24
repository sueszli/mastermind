import codedraw.*;

import java.awt.Color;
import java.util.Arrays;

public class Aufgabe1 {
    private static final int NUMBER_OF_TURNS = 10;
    private static final int CODE_LENGTH = 4;
    private static final int NUMBER_OF_COLUMNS = CODE_LENGTH * 2 + 1;
    private static final Color[] COLORS = new Color[]{Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY, Color.RED, Color.PINK, Color.YELLOW};

    private static int[][] playField = null;
    private static int[][] tips = null;
    private static int turn = 0;
    private static int pin = 0;
    private static int[] solution = null;

    // initializes all global variables for the game
    private static void initGame() {
        playField = new int[NUMBER_OF_TURNS][CODE_LENGTH];
        tips = new int[NUMBER_OF_TURNS][CODE_LENGTH]; // 1 == red; 2 == white
        turn = 0;
        pin = 0;
        solution = generateCode();
        //for testing to print the solution
        System.out.println(Arrays.toString(solution));
    }

    // generates array with length CODE_LENGTH and random numbers from 1 to COLORS.length
    private static int[] generateCode() {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        return null;
    }

    // calculates red and white tip pins
    private static void updateTips() {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
    }

    // draws game to screen
    private static void drawGame(CodeDraw myDrawObj) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
    }

    private static void clearBoard(CodeDraw myDrawObj) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
    }

    private static void showMessage(CodeDraw myDrawObj, String text, Color color) {
        int rectWidth = myDrawObj.getWidth() / 2;
        int rectHeight = myDrawObj.getHeight() / 6;
        int xPos = myDrawObj.getWidth() / 4;
        int yPos = myDrawObj.getHeight() / 2 - rectHeight / 2;

        myDrawObj.setColor(Palette.LIGHT_GRAY);
        myDrawObj.fillRectangle(xPos, yPos, rectWidth, rectHeight);
        myDrawObj.setColor(Palette.BLACK);
        myDrawObj.drawRectangle(xPos, yPos, rectWidth, rectHeight);
        myDrawObj.setColor(color);

        // set font for text
        TextFormat font = new TextFormat();
        font.setFontSize(40);
        font.setFontName("Arial");
        font.setTextOrigin(TextOrigin.CENTER);
        font.setBold(true);
        myDrawObj.setTextFormat(font);

        myDrawObj.drawText(myDrawObj.getWidth() / 2, myDrawObj.getHeight() / 2, text);
        myDrawObj.show(3000);
    }

    private static void playGame(CodeDraw myDrawObj, EventScanner myEventSC) {
        boolean gameActive = true;
        while (!myDrawObj.isClosed() && gameActive) {
            if (myEventSC.hasKeyPressEvent()) {
                if (myEventSC.nextKeyPressEvent().getChar() == 'q') {
                    gameActive = false;
                }
            } else if (myEventSC.hasMouseClickEvent()) {
                MouseClickEvent currentClick = myEventSC.nextMouseClickEvent();
                int mouseX = currentClick.getX();
                int mouseY = currentClick.getY();

                //*****************************************************************************************************
                // TODO: Implementieren Sie hier Ihre Lösung für die Methode
                //****************************************************************************************************
                drawGame(myDrawObj);
            }
            else {
                myEventSC.nextEvent();
            }
        }
        myDrawObj.close();
    }

    public static void main(String[] args) {

        int height = 800;
        int width = height + height / (COLORS.length + 1);

        CodeDraw myDrawObj = new CodeDraw(width, height);
        myDrawObj.setTitle("MasterMind Game");
        EventScanner myEventSC = myDrawObj.getEventScanner();

        initGame();
        drawGame(myDrawObj);
        playGame(myDrawObj, myEventSC);
    }
}



