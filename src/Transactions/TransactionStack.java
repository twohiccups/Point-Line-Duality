package Transactions;

import java.util.ArrayList;

public class TransactionStack {
    
    ArrayList<Transaction> undoStack;
    ArrayList<Transaction> redoStack;
    
    public TransactionStack() {
        undoStack = new ArrayList();
        redoStack = new ArrayList();
    }
    
    public void pushUndo(Transaction t) {
        undoStack.add(t);
    }
    
    public Transaction popUndo() {
        if(undoStack.size() > 0) {
            Transaction t = undoStack.remove(undoStack.size()-1);
            t.undo();
            pushRedo(t);
            return t;
        }
        return null;
        
        
    }
    
    public void pushRedo(Transaction t) {
        redoStack.add(t);
    }
    
    public Transaction peekUndo() {
        return undoStack.get(undoStack.size()-1);
    } 
    
     public Transaction peekRedo() {
        return redoStack.get(redoStack.size()-1);
    } 
       
    public Transaction popRedo() {
        if(redoStack.size() > 0) {
            Transaction t = redoStack.remove(redoStack.size() - 1);
            t.redo();
            pushUndo(t);
            return t;
        }
        return null;
    }

    
    
    
}
