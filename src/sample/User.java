package sample;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jfabiano on 8/31/2016.
 */
public class User {
    //private String name;
    private static ArrayList<ToDoItem> toDoList;

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public ArrayList<ToDoItem> getToDoList() {
        return toDoList;
    }

    public static void setToDoList(ArrayList<ToDoItem> toDoListy) {
        toDoList = toDoListy;
    }
}
