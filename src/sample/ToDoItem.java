package sample;

import java.io.Serializable;

/**
 * Created by jfabiano on 8/31/2016.
 */

public class ToDoItem implements Serializable {

    private String todoText;
    private boolean done;
    public ToDoItem()
    {

    }

    public ToDoItem(String todoText) {
        this.todoText = todoText;
    }

    public ToDoItem(String todoText, boolean done) {
        this.todoText = todoText;
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTodoText() {

        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String toString() {
        if (isDone()) {
            return todoText + " (done)";
        } else {
            return todoText;
        }
    }
}