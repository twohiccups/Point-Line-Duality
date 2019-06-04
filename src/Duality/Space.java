package Duality;
import static Duality.Duality.toPlanarX;
import static Duality.Duality.toPlanarY;
import static Duality.Duality.windowHeight;
import static Duality.Duality.windowWidth;
import Transactions.AddElement;
import Transactions.TransactionStack;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Space {
    
    TransactionStack transactions;
       Pane primal, dual;
       Line axisXprimal, axisYprimal, axisXdual, axisYdual;
       
       public static double startX, startY, endX, endY;
       boolean invertMode;
       double lineWidth;

    public Space() {
        
        
        lineWidth = Duality.strokeWidth;
        transactions = new TransactionStack();
        primal = new Pane();
        dual = new Pane();
        
        setAxis();

        primal.setOnMouseClicked(e -> { 
            Color color =  Duality.primalColor;
            if (Duality.drawLine) {
                
                makeLine(primal, dual, color);
            }
            else {
                makePoint(primal, dual, color);
            }  
        });
        
        dual.setOnMouseClicked(e -> { 
            Color color =  Duality.dualColor;
            if (Duality.drawLine) {
                makeLine(dual, primal, color);
            }
            else {
                makePoint(dual, primal, color);
            }  
        });
    }
    
    
    
    
    
    public void makePoint(Pane primal, Pane dual, Color color) {
        Circle c = new Circle(toPlanarX(endX), toPlanarY(endY), Duality.pointRadius);
        c.setFill(color);
        if (invertMode) {
            if (primal == this.dual) {
                c.setFill(color.invert());
            }
        }
        primal.getChildren().add(c);
        
        transactions.pushUndo(new AddElement(primal, c));
        
        makeDualLine(dual, color);
        
        
        
        
    }

    private void makeDualLine(Pane dual, Color color) {

        double lineStartY, lineEndY;
        lineStartY = startX * Duality.farStartX - startY;
        lineEndY = endX * Duality.farEndX - endY;
        Line line = new Line(Duality.toPlanarX(Duality.farStartX),
                Duality.toPlanarY(lineStartY),
                Duality.toPlanarX(Duality.farEndX),
                Duality.toPlanarY(lineEndY));
        
        line.setStroke(color);
        if (invertMode) {
            if (dual == this.dual) {
                line.setStroke(color.invert());
            }
        }
        
        line.setStrokeWidth(lineWidth);
        dual.getChildren().add(line);
        
        AddElement t = (AddElement) transactions.peekUndo();
        t.setDual(dual);
        t.setDualShape(line);
        
    }

    public void makeLine(Pane primal, Pane dual, Color color) {
        
        double slope = (endY - startY) / (endX - startX);
        double yIntercept = startY - startX * slope;
      
        double lineStartY, lineEndY;
        lineStartY = slope * Duality.farStartX + yIntercept;
        lineEndY = slope * Duality.farEndX + yIntercept;
        Line line = new Line(
                Duality.toPlanarX(Duality.farStartX),
                Duality.toPlanarY(lineStartY),
                Duality.toPlanarX(Duality.farEndX),
                Duality.toPlanarY(lineEndY)
        );
        line.setStroke(color);
        if (invertMode) {
            if (primal == this.dual) {
                line.setStroke(color.invert());
            }
        }
        
        
        line.setStrokeWidth(lineWidth);
        primal.getChildren().add(line);
        
        transactions.pushUndo(new AddElement(primal, line));
        
        makeDualPoint(dual, color);
        
        
    }

    public void makeDualPoint(Pane dual, Color color) {
        
        double slope = (endY - startY) / (endX - startX);
        double yIntercept = -1*(startY - startX * slope); 

        Circle c = new Circle(Duality.toPlanarX(slope), Duality.toPlanarY(yIntercept), 5);
        
        c.setFill(color);
        if (invertMode) {
            if (dual == this.dual) {
                c.setFill(color.invert());
            }
        }
        dual.getChildren().add(c);
        
        AddElement t = (AddElement) transactions.peekUndo();
        t.setDual(dual);
        t.setDualShape(c);
        
    }
    
    public void invertColors(Pane pane) {
        
        
        pane.getChildren().forEach(element -> {
            if (element instanceof Circle) {
                Circle c =  (Circle) element;
                c.setFill(((Color)c.getFill()).invert());
            }
            else if (element instanceof Line) {
                Line l = (Line) element;
                l.setStroke(((Color)l.getStroke()).invert());
            }
        });
        invertMode = !invertMode;
        
        if (invertMode) pane.setStyle("-fx-background-color: black;");
        else  pane.setStyle("-fx-background-color: white;");
    }

    public void reset() {
        dual.getChildren().remove(2, dual.getChildren().size());
        primal.getChildren().remove(2, primal.getChildren().size());
    }

    public Pane getDual() {
        return dual;
    }

    public Pane getPrimal() {
        return primal;
    }

    public TransactionStack getTransactions() {
        return transactions;
    }

    public void changeLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
        primal.getChildren().forEach(element -> {
            if (element instanceof Line) {
                Line line = ((Line) element);
                if (line != axisXprimal && line != axisYprimal)
                    line.setStrokeWidth(lineWidth);
            }
        });
        
        dual.getChildren().forEach(element -> {
            if (element instanceof Line) {
                Line line = ((Line) element);
                if (line != axisXdual && line != axisYdual)
                    line.setStrokeWidth(lineWidth);
            }
        });
        
    }
    
    
    public void setAxis() {
        axisYprimal = new Line(windowWidth / 2, 0, windowWidth / 2, windowHeight);
        axisYprimal.setStroke(Color.BLACK);
        primal.getChildren().add(axisYprimal);
        
        axisXprimal = new Line(0, windowHeight / 2, windowWidth, windowHeight / 2);
        axisXprimal.setStroke(Color.GRAY);
        primal.getChildren().add(axisXprimal);
        
        axisYdual = new Line(windowWidth / 2, 0, windowWidth / 2, windowHeight);
        axisYdual.setStroke(Color.BLACK);
        dual.getChildren().add(axisYdual);
        
        axisXdual = new Line(0, windowHeight / 2, windowWidth, windowHeight / 2);
        axisXdual.setStroke(Color.GRAY);
        dual.getChildren().add(axisXdual);
    }

    
    
    
    

    
    
    
    
    
    
    
    


}