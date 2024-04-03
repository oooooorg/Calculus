package com.example.demo;

import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;

public class App extends Application {

    public double sqrt(double x) {
        return Math.pow(x, 3);
    }

    double scaleSize = 1;
    int accuracy = 20;
    int SIZE = 60;
    final int WIDTH = 1920;
    final int HEIGHT = 1080;
    int N;
    String TYPE;
    final double FROM = -2 * SIZE; 
    final double TO = 2 * SIZE;

    public double frac(double x, double fraction) {
        switch (TYPE) {
            case "start":
                return x;
            case "middle":
                return x + fraction / 2;
            case "finish":
                return x + fraction;
            default: {
                Random rnd = new Random();
                return x + (rnd.nextDouble(fraction));
            }
        }
    }

    public int sizeof(int x) {
        return String.valueOf(x).length();
    }

    public void start(Stage primaryStage) throws Exception {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        TYPE = scanner.next();
        Group group = new Group();

        for (int i = -WIDTH * SIZE / 2; i < WIDTH * SIZE / 2 - 1; i++) {
            if (i % SIZE == 0) {
                Line lineX = new Line(i, Integer.MIN_VALUE, i, Integer.MAX_VALUE);
                lineX.setStroke(Color.LIGHTGRAY);
                group.getChildren().add(
                        lineX
                );
            }
        }

        for (int i = HEIGHT * SIZE / 2; i > -HEIGHT * SIZE; i--) {
            if (i % SIZE == 0) {
                Line lineY = new Line(Integer.MIN_VALUE, i, Integer.MAX_VALUE, i);
                lineY.setStroke(Color.LIGHTGRAY);
                group.getChildren().add(
                        lineY
                );
            }
        }

        Operation operation = x -> sqrt(x[0]);

        double fraction = ((TO - FROM) / N);
        double sum = 0;

        for (double i = FROM; i < TO; i += fraction) {
            double c = frac(i, fraction);
            Rectangle figure = new Rectangle(
                i + (double) WIDTH / 2, 
                Math.min(HEIGHT / 2, -operation.execute((double) c / SIZE) * SIZE + (double) HEIGHT / 2),
                fraction,
                Math.abs(operation.execute((double) c / SIZE) * SIZE)
            );
            sum += (fraction / SIZE) * operation.execute((double) c / SIZE);
            figure.setFill(Color.AQUAMARINE);
            figure.setStroke(Color.BLACK);
            group.getChildren().add(
                    figure
            );
        }
        System.out.println(sum);

        for (int i = -WIDTH * SIZE / 2; i < WIDTH * SIZE / 2 - 1; i = i + SIZE) {
            if (-1 <= i / SIZE && i / SIZE <= 1 || (i / SIZE) % 5 == 0) {
                Text text = new Text((double) WIDTH / 2 + i - (double) sizeof(i) * 2, (double) HEIGHT / 2 + 15, i / SIZE + "");
                group.getChildren().add(text);
            }
        }

        for (int i = HEIGHT * SIZE / 2; i > -HEIGHT * SIZE; i = i - SIZE) {
            if (((-1 <= i / SIZE) && (i / SIZE <= 1)) || ((i / SIZE % 5 == 0) && (i / SIZE != 0))) {
                if (i / SIZE != 0) {
                    Text text = new Text((double) WIDTH / 2 + 2, (double) HEIGHT / 2 + i + 12, -i / SIZE + "");
                    if (SIZE < 5) {
                        text.setFont(new Font(SIZE));
                    }
                    group.getChildren().add(text);
                }
            }
        }

        for (double  i = FROM; i < TO; i++) {
            Line figure = new Line(
                    i + (double) WIDTH / 2,
                    -operation.execute((double) i / SIZE) * SIZE + (double) HEIGHT / 2,
                    i + 1 + (double) WIDTH / 2,
                    -operation.execute((double) (i + 1) / SIZE) * SIZE + (double) HEIGHT / 2
            );
            figure.setStroke(Color.RED);
            group.getChildren().add(
                    figure
            );
        }

        Scene scene = new Scene(group, WIDTH, HEIGHT);


        Circle player = new Circle(2, Color.GREEN); // Создание круглого объекта (персонажа)
        player.setTranslateX((double) WIDTH / 2); // Установка начальной позиции персонажа по горизонтали
        player.setTranslateY((double) HEIGHT / 2); // Установка начальной позиции персонажа по вертикали

        double[] coordX = {0};
        double[] coordY = {0};

        group.getChildren().add(player);

        group.getChildren().addAll(
                new Line(Integer.MIN_VALUE, (double) HEIGHT / 2, Integer.MAX_VALUE, (double) HEIGHT / 2),
                new Line((double) WIDTH / 2, Integer.MIN_VALUE, (double) WIDTH / 2, Integer.MAX_VALUE)
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