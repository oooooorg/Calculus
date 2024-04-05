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

    /* Enter the function after the arrow with the argument x[0] */
    Operation operation = x -> Math.pow(x[0], 2);

    double scaleSize = 1;
    int accuracy = 20;
    final int SIZE = 60;
    final int WIDTH = 1080;
    final int HEIGHT = 720;
    int N;
    String TYPE;
    final double FROM = -1 * SIZE;
    final double TO = 1 * SIZE;

    public double frac(double x, double fraction) {
        return switch (TYPE) {
            case "start" -> x;
            case "middle" -> x + fraction / 2;
            case "finish" -> x + fraction;
            default -> {
                Random rnd = new Random();
                yield x + (rnd.nextDouble(fraction));
            }
        };
    }

    public int sizeof(int x) {
        return String.valueOf(x).length();
    }

    public void start(Stage primaryStage) throws Exception {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        TYPE = scanner.next();

        Group group = new Group();
        Scene scene = new Scene(group, WIDTH, HEIGHT);

        /* Creating a grid */
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

        /* Size of a fraction */
        double fraction = ((TO - FROM) / N);

        double sum = 0;
        for (double i = FROM; i < TO; i += fraction) {
            double c = frac(i, fraction);

            /* Creating rectangles for splitting */
            Rectangle figure = new Rectangle(
                i + (double) WIDTH / 2, 
                Math.min(HEIGHT / 2, -operation.execute((double) c / SIZE) * SIZE + (double) HEIGHT / 2),
                fraction,
                Math.abs(operation.execute((double) c / SIZE) * SIZE)
            );

            /* Calculating the integral sum */
            sum += (fraction / SIZE) * operation.execute((double) c / SIZE);
            figure.setFill(Color.PINK);
            figure.setStroke(Color.BLACK);
            group.getChildren().add(
                    figure
            );
        }
        System.out.println(sum);

        /* Drawing numbers along the x and y axes */
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

        /* Drawing a graph of a given function */
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

        /* Creating a "character" for interactive */
        Circle player = new Circle(2, Color.GREEN);
        player.setTranslateX((double) WIDTH / 2);
        player.setTranslateY((double) HEIGHT / 2);
        double[] coordX = {0};
        double[] coordY = {0};
        group.getChildren().add(player);
        group.getChildren().addAll(
                new Line(Integer.MIN_VALUE, (double) HEIGHT / 2, Integer.MAX_VALUE, (double) HEIGHT / 2),
                new Line((double) WIDTH / 2, Integer.MIN_VALUE, (double) WIDTH / 2, Integer.MAX_VALUE)
        );

        /* Interactive */
        double acs = (double) accuracy / SIZE;
        scene.setOnKeyPressed(event -> {
                    KeyCode code = event.getCode();
                    switch (code) {
                        /* The movement of the "character" and the screen */
                        case UP:
                            group.setTranslateY(group.getTranslateY() + accuracy);
                            player.setTranslateY(player.getTranslateY() - accuracy);
                            coordY[0] = coordY[0] + acs;
                            break;
                        case DOWN:
                            group.setTranslateY(group.getTranslateY() - accuracy);
                            player.setTranslateY(player.getTranslateY() + accuracy);
                            coordY[0] = coordY[0] - acs;
                            break;
                        case LEFT:
                            group.setTranslateX(group.getTranslateX() + accuracy);
                            player.setTranslateX(player.getTranslateX() - accuracy);
                            coordX[0] = coordX[0] - acs;
                            break;
                        case RIGHT:
                            group.setTranslateX(group.getTranslateX() - accuracy);
                            player.setTranslateX(player.getTranslateX() + accuracy);
                            coordX[0] = coordX[0] + acs;
                            break;

                        /* The movement of the "character" */
                        case W:
                            player.setTranslateY(player.getTranslateY() - accuracy);
                            coordY[0] = coordY[0] + acs;
                            break;
                        case S:
                            player.setTranslateY(player.getTranslateY() + accuracy);
                            coordY[0] = coordY[0] - acs;
                            break;
                        case A:
                            player.setTranslateX(player.getTranslateX() - accuracy);
                            coordX[0] = coordX[0] - acs;
                            break;
                        case D:
                            player.setTranslateX(player.getTranslateX() + accuracy);
                            coordX[0] = coordX[0] + acs;
                            break;

                        /* Showing the coordinates of the "character" */
                        case H:
                            Dialog<String> dialog = new Dialog<>();
                            dialog.setTitle("Player Coordinates");
                            dialog.setHeaderText("Current Coordinates: (" + coordX[0] + ", " + coordY[0] + ")");
                            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                            dialog.showAndWait();
                            break;

                        /* Changing the accuracy of the movement of the "character" */
                        case C:
                            TextInputDialog dialogue = new TextInputDialog(Double.toString((double) accuracy / SIZE));
                            dialogue.setTitle("Set Accuracy");
                            dialogue.setHeaderText("Enter the new accuracy value:");
                            dialogue.showAndWait().ifPresent(newValue -> {
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

                        /* The approach/distance */
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
        primaryStage.setTitle("function: x^2 "  + "[" + FROM / SIZE + "; " +  TO / SIZE + "]  integral sum : " + sum + " number of splits : " + N);
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