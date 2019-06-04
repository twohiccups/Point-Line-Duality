package Duality;

public class Handlers {

    static void undo() {
        Duality.getCurrentSpace().getTransactions().popUndo();  
    }

    static void redo() {
        Duality.getCurrentSpace().getTransactions().popRedo();
    }

    static void invert() {
        Duality.getCurrentSpace().invertColors(
        Duality.getCurrentSpace().getDual());
    }

    static void changeLineWidth(int width) {
        Duality.getCurrentSpace().changeLineWidth(width);
    }

    public static void clear() {
        Duality.getCurrentSpace().reset();
    }

    public static void toggleLine() {
        Duality.drawLine = !Duality.drawLine;
    }
}
