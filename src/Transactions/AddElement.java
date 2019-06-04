package Transactions;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class AddElement implements Transaction {
    Pane primal, dual;
    Shape primalShape, dualShape;
    

    public AddElement(Pane primal, Shape primalShape) {
        this.primal = primal;
        this.primalShape = primalShape;
    }
    @Override
    public void undo() {
        primal.getChildren().remove(primalShape);
        dual.getChildren().remove(dualShape);
    }

    @Override
    public void redo() {
        primal.getChildren().add(primalShape);
        dual.getChildren().add(dualShape);
    }

    public void setDualShape(Shape dualShape) {
        this.dualShape = dualShape;
    }

    public void setDual(Pane dual) {
        this.dual = dual;
    }
    
    
    
    
    
}
