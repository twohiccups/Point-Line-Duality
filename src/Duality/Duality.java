/*
 * @author Mark Koshkin
 */

package Duality;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Duality extends Application {

    
    public static Space currentSpace;

    
    public static double scaleX = 100;
    public static double scaleY = 100;
    public static boolean drawLine = false;
    public static Color primalColor = Color.BLUE;
    public static Color dualColor = Color.RED;
    
    public static double strokeWidth = 4;
    
    public static double farStartX = -5, farEndX = 5;

    public static int windowHeight = 700;
    public static int windowWidth = 700;
    public static int pointRadius = 5;
    public static Stage primaryStage;
    Space s;
    
    public Circle lineStart, lineEnd;
    public Line tempLine;
    
    ArrayList<Space> spaces;

    @Override
    public void start(Stage primaryStage) {
        
        spaces = new ArrayList();
        
        
        s = new Space();  
        spaces.add(s);
        currentSpace = s;
        
        
        Scene scene = new Scene(s.getPrimal(), windowWidth, windowHeight);
        Scene scene2 = new Scene(s.getDual(), windowWidth, windowHeight);
        

        scene.setOnMousePressed(e -> {
                Space.startX = toModalX(e.getX());
                Space.startY = toModalY(e.getY());
                if (drawLine) {
                    // make visible where line is drawn
                }
        });
        scene.setOnMouseDragged(e -> {});
        
        scene.setOnMouseReleased(e -> {
                Space.endX = toModalX(e.getX());
                Space.endY = toModalY(e.getY());
        });
        scene2.setOnMousePressed(scene.getOnMousePressed());
        scene2.setOnMouseReleased(scene.getOnMouseReleased());
       


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {;
                drawLine = !drawLine;
            } else if (e.getCode() == KeyCode.S) {
            } else if (e.getCode() == KeyCode.D) {
                currentSpace.getTransactions().popUndo();
            } else if (e.getCode() == KeyCode.W) {
                s.reset();
            }
        });
        scene2.setOnKeyPressed(scene.getOnKeyPressed());

        this.primaryStage = primaryStage;
        primaryStage.setTitle("primal");
        primaryStage.setScene(scene);
        primaryStage.setX(10);

        primaryStage.show();
        
        Stage dualStage = new Stage();
        dualStage.setTitle("dual");
        dualStage.setScene(scene2);

        dualStage.setX(primaryStage.getX() + primaryStage.getWidth() + 5);
        dualStage.setY(primaryStage.getY());
        dualStage.show();
        ControlWindow.createControlWindow();
        
    }

    public void connect(Circle a, Circle b, Color c) {

        Line line = new Line(a.getCenterX(), a.getCenterY(),
                b.getCenterX(), b.getCenterY());
        line.setStrokeWidth(2);
      //  line.setStroke(lineColor);

         s.getPrimal().getChildren().add(line);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static double toModalX(double x) {
        return (x - windowWidth / 2) / scaleX;
    }

    public static double toModalY(double y) {
        return (y * (-1) + windowHeight / 2) / scaleY;
    }

    public static double toPlanarX(double x) {
        return x * scaleX + windowWidth / 2;
    }

    public static double toPlanarY(double y) {
        return windowHeight / 2 - scaleY * y;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Space getCurrentSpace() {
        return currentSpace;
    }
    
    
    
    
    
    

}
