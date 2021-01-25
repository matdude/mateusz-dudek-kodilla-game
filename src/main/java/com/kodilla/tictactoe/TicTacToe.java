package com.kodilla.tictactoe;

import com.sun.source.doctree.SeeTree;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class TicTacToe extends Application {

    private boolean isTurnX = true;
    private final Random random = new Random();
    private String winner = "";
    private int index = 0;
    private boolean isEndGame = false;

    private final List<Button> tiles = new LinkedList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints(200);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setStyle("-fx-background-color: white;");
                button.setPrefHeight(198);
                button.setPrefWidth(198);
                button.setGraphic(null);
                button.setOnMouseClicked(event -> {
                    button.setDisable(true);
                    isTurnX = !isTurnX;
                    if (isTurnX) {
                        button.setText("X");
                    } else {
                        button.setText("O");
                    }
                    checkWinner();
                    makeComputerMove();
                    checkWinner();
                });
                tiles.add(button);
                gridPane.add(button, i, j);

            }

        }

        gridPane.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void checkWinner() {
        int XCount = 0;
        int OCount = 0;

        if (isEndGame) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j = j + 3) {
                String text = tiles.get(i + j).getText();
                if ("X".equals(text)) {
                    XCount++;
                }
                if ("O".equals(text)) {
                    OCount++;
                }
            }
            if (XCount == 3) {
                System.out.print("Wygrał x");
                winner = "X";
                endOfGame();
            }
            if (OCount == 3) {
                System.out.print("Wygrał O");
                winner = "O";
                endOfGame();
            }
            XCount = 0;
            OCount = 0;
        }
        for (int i = 0; i < 9; i = i + 3) {
            String text1 = tiles.get(i).getText();
            String text2 = tiles.get(i + 1).getText();
            String text3 = tiles.get(i + 2).getText();
//            System.out.println("Text1 " + text1);
//            System.out.println("Text2 " + text2);
//            System.out.println("Text3 " + text3);

            if (text1 != null && !text1.isEmpty() && text1.equals(text2) && text3 != null && text3.equals(text1)) {
                System.out.print("Wygrał " + text1);
                winner = text1;
                endOfGame();
            }
        }
        String text7 = tiles.get(0).getText();
        String text8 = tiles.get(4).getText();
        String text9 = tiles.get(8).getText();

        if (text7 != null && !text7.isEmpty() && text7.equals(text8) && text9 != null && text9.equals(text7)) {
            System.out.print("Wygrał " + text7);
            winner = text7;
            endOfGame();
        }
        String text4 = tiles.get(2).getText();
        String text5 = tiles.get(4).getText();
        String text6 = tiles.get(6).getText();

        if (text4 != null && !text4.isEmpty() && text4.equals(text5) && text6 != null && text6.equals(text4)) {
            System.out.print("Wygrał " + text4);
            winner = text4;
            endOfGame();
        }
//        for (int i=0; i<9; i++) {
//            System.out.println(i + "=" + tiles.get(i).getText());
//        }
    }

    public void makeComputerMove() {
        Set<Integer> moveIndex = new HashSet<>();
        do {
            int tileIndex = random.nextInt(9);
            moveIndex.add(tileIndex);
            if (tiles.get(tileIndex).getText().equals("")) {
                tiles.get(tileIndex).setText("X");
                isTurnX = !isTurnX;
                tiles.get(tileIndex).setDisable(true);
            }
        } while (!isTurnX && moveIndex.size() != 9);
        if (moveIndex.size() == 9) {
            index = 9;
            endOfGame();
        }
    }

    public void saver() {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("rank.txt"));
            out.write("Game won by " + winner);
            out.close();
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }

    public void endOfGame() {
        if (isEndGame) {
            return;
        } else {
            isEndGame = true;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of game");
        String message;
        alert.setHeaderText("Thank you for playing");
        if (index == 9) {
            message = "Draw";
        } else {
            message = "Winner is " + winner;
        }
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        saver();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            System.out.print("Canceled");
        }

    }
}