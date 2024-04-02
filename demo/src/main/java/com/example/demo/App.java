package com.example.demo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

public class App extends Application {

    public double sqrt(double x) {
        return x * x - 2 * x * SIZE + SIZE * SIZE;
    }

    double scaleSize = 1;
    int accuracy = 20;
    int SIZE = 20;
    final int WIDTH = 1080;
    final int HEIGHT = 720;
    public int sizeof(int x) {
       return String.valueOf(x).length();
    }
    public void start(Stage primaryStage) throws Exception {

        Group group = new Group();

        for(int i = -WIDTH * SIZE / 2; i < WIDTH * SIZE /2 - 1; i++) {
            if (i % SIZE == 0) {
                Line lineX= new Line(i, Integer.MIN_VALUE, i, Integer.MAX_VALUE);
                lineX.setStroke(Color.LIGHTGRAY);
                group.getChildren().add(
                        lineX
                );
            }
        }

        for(int i = HEIGHT * SIZE / 2; i > - HEIGHT * SIZE; i--) {
            if (i % SIZE == 0) {
                Line lineY = new Line(Integer.MIN_VALUE, i, Integer.MAX_VALUE, i);
                lineY.setStroke(Color.LIGHTGRAY);
                group.getChildren().add(lineY);
            }
        }

        for (int i = -WIDTH * SIZE /2; i < WIDTH * SIZE /2 - 1; i = i + SIZE) {
            if (-1 <= i / SIZE && i / SIZE <= 1 || (i / SIZE) % 5 == 0) {
                Text text = new Text((double) WIDTH / 2+i- (double) sizeof(i)*2, (double) HEIGHT /2 + 15, i / SIZE + "");
                group.getChildren().add(text);
            }
        }

        for (int i = HEIGHT * SIZE / 2; i > - HEIGHT * SIZE; i = i - SIZE) {
            if (((-1 <= i/ SIZE) && (i / SIZE <= 1)) || ((i / SIZE % 5 == 0) && (i / SIZE != 0))) {
                if (i / SIZE != 0) {
                    Text text = new Text((double) WIDTH / 2 + 2, (double) HEIGHT / 2 + i + 12, -i / SIZE + "");
                    if (SIZE < 5) {
                        text.setFont(new Font(SIZE));
                    }
                    group.getChildren().add(text);
                }
            }
        }

        Operation operation = x -> sqrt(x[0]) / SIZE;

        for(int i = -WIDTH * SIZE; i < WIDTH * SIZE; i++) {
            Line figure = new Line(
                    i + WIDTH / 2,
                    -operation.execute(i) + HEIGHT / 2,
                    i + 1 + WIDTH / 2,
                    -operation.execute(i + 1) + HEIGHT / 2
            );
            figure.setStroke(Color.RED);
            group.getChildren().add(
                    figure
            );
        }

        Scene scene = new Scene(group, WIDTH, HEIGHT);


        Circle player = new Circle(2, Color.GREEN); // Создание круглого объекта (персонажа)
        player.setTranslateX(WIDTH/2); // Установка начальной позиции персонажа по горизонтали
        player.setTranslateY(HEIGHT/2); // Установка начальной позиции персонажа по вертикали

        double[] coordX = {0};
        double[] coordY = {0};

        group.getChildren().add(player);

        group.getChildren().addAll(
                new Line(Integer.MIN_VALUE, HEIGHT /2, Integer.MAX_VALUE, HEIGHT /2),
                new Line(WIDTH /2, Integer.MIN_VALUE, WIDTH /2, Integer.MAX_VALUE)
        );


        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            switch (code) {
                case UP:
                    group.setTranslateY(group.getTranslateY() + accuracy); // Движение вверх
                    player.setTranslateY(player.getTranslateY() - accuracy);
                    coordY[0] = coordY[0] + (double) accuracy / SIZE;
                    break;
                case DOWN:
                    group.setTranslateY(group.getTranslateY() - accuracy); // Движение вниз
                    player.setTranslateY(player.getTranslateY() + accuracy);
                    coordY[0] = coordY[0] - (double) accuracy / SIZE;
                    break;
                case LEFT:
                    group.setTranslateX(group.getTranslateX() + accuracy); // Движение влево
                    player.setTranslateX(player.getTranslateX() - accuracy);
                    coordX[0] = coordX[0] - (double) accuracy / SIZE;
                    break;
                case RIGHT:
                    group.setTranslateX(group.getTranslateX() - accuracy); // Движение вправо
                    player.setTranslateX(player.getTranslateX() + accuracy);
                    coordX[0] = coordX[0] + (double) accuracy / SIZE;
                    break;


                case W:
                    player.setTranslateY(player.getTranslateY() - accuracy);
                    coordY[0] = coordY[0] + (double) accuracy / SIZE;
                    break;
                case S:
                    player.setTranslateY(player.getTranslateY() + accuracy);
                    coordY[0] = coordY[0] - (double) accuracy / SIZE;
                    break;
                case A:
                    player.setTranslateX(player.getTranslateX() - accuracy);
                    coordX[0] = coordX[0] - (double) accuracy / SIZE;
                    break;
                case D:
                    player.setTranslateX(player.getTranslateX() + accuracy);
                    coordX[0] = coordX[0] + (double) accuracy / SIZE;
                    break;

                case H:
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Player Coordinates");
                    dialog.setHeaderText("Current Coordinates: (" + coordX[0] + ", " + coordY[0] + ")");

                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

                    dialog.showAndWait();
                    break;

                case C:
                    TextInputDialog dialogue = new TextInputDialog(Double.toString((double) accuracy / SIZE));
                    dialogue.setTitle("Set Accuracy");
                    dialogue.setHeaderText("Enter the new accuracy value:");
                    dialogue.showAndWait().ifPresent(newValue -> {
                        // Проверка и присвоение нового значения accuracyValue
                        try {
                            accuracy = (int) (Double.parseDouble(newValue) * SIZE);
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Invalid input. Please enter a valid number.");
                            alert.showAndWait();
                        }
                    });
                    break;
                case X:
                    Dialog dialog1 = new Dialog();
                    dialog1.setTitle("Set scale");
                    dialog1.setHeaderText("Current scale:" + scaleSize);
                    Slider slider = new Slider(0.1, 2, scaleSize);

                    dialog1.getDialogPane().setContent(slider);
                    dialog1.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    dialog1.setResultConverter(buttonType -> {
                        if (buttonType == ButtonType.OK) {
                            scaleSize = slider.getValue();
                            group.setScaleX(scaleSize);
                            group.setScaleY(scaleSize);
                        }
                        return null;
                    });

                    dialog1.showAndWait();
                    break;
                }
            }
        );
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FunctionalInterface
    public interface Operation {
        double execute(double... nums);
    }

    public static void main(String[] args) {
        launch(args);

    }
}