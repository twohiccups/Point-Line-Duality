package Duality;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControlWindow {

    Button undo, redo, invert, clear, toggleLine;
    ColorPicker colorPickerPrimal, colorPickerDual;
    ComboBox lineWidthCombo;

    private ControlWindow() {
        
        undo = new Button("undo");
        undo.setOnAction( e -> {
            Handlers.undo();
        } );
        redo = new Button("redo");
        redo.setOnAction( e-> {
             Handlers.redo();
        });
        invert = new Button("invert");
        invert.setOnAction( e-> {
            Handlers.invert();
        });
        clear = new Button("clear");
        clear.setOnAction( e -> {
            Handlers.clear();
        } );
        toggleLine = new Button("point / line");
        toggleLine.setOnAction( e -> {
            Handlers.toggleLine();
        } );

        colorPickerPrimal = new ColorPicker(Color.BLUE);
        colorPickerPrimal.setOnAction(e-> {
            Duality.primalColor = colorPickerPrimal.getValue();
        });
        
        colorPickerDual = new ColorPicker(Color.RED);
        colorPickerDual.setOnAction(e ->  {
            Duality.dualColor = colorPickerDual.getValue();
        });
        
        ObservableList<Integer> options = FXCollections.observableArrayList(
        1, 2, 3, 4, 5, 6, 7, 8, 10, 12);
        
        lineWidthCombo = new ComboBox(options);
        lineWidthCombo.setOnAction( e-> {
            Handlers.changeLineWidth((Integer)lineWidthCombo.getValue());
        });
    }
    
    
    
    public static void createControlWindow() {
        ControlWindow controls = new ControlWindow();
     
        
        
        
        HBox root = new HBox();
     
        root.getChildren().addAll(
                controls.undo,
                controls.redo,
                controls.invert,
                controls.clear,
                controls.toggleLine,
                controls.colorPickerPrimal,
                controls.lineWidthCombo
               );
        
        
        Scene scene = new Scene(root, 500, 40);
       
        
        Stage controlWindow = new Stage();
        controlWindow.setTitle("Controls");
        controlWindow.setScene(scene);
        controlWindow.setX(Duality.getPrimaryStage().getX());
        controlWindow.setY(Duality.getPrimaryStage().getY()
                + Duality.getPrimaryStage().getHeight());
        controlWindow.show();
        
    }
    
}
