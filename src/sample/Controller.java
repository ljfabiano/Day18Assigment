package sample;

/**
 * Created by jfabiano on 8/31/2016.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable{

    final String DATA_FILE_NAME = "todo.dat";

    @FXML
    ListView todoList;//As you are adding items to the list view you are adding .to string for the non string lines in the list, and so you override to get the desired output because we know the list is not strings

    @FXML
    TextField todoText;

    String name;

    UserList myUser = new UserList();

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();//factory method//why interfaces are so powerful
    //we are getting the interface only even though the object being returned iis of a different type, but implements the interface

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scanner bankScanner = new Scanner(System.in);
        //Ask the user for their name
        System.out.println("Please enter your name:");
        String nameInput = bankScanner.nextLine();
        boolean returningCustomer = isCustomerReturning(nameInput);

        User currentUser = new User();
        //currentUser.setName(nameInput);
        name = nameInput;
        readToDoList();
//        if (returningCustomer == true)
//        {
//            //get the file/files, and add it to the
//            //ToDoItem myItem = new ToDoItem(jsonRestore());
//            //currentUser.setToDoList();
//
//        }


        todoList.setItems(todoItems);//two way data binding between observable list and the observable list
    }

    public void addItem() {
        System.out.println("Adding item ...");
        todoItems.add(new ToDoItem(todoText.getText()));
        todoText.setText("");
        //todoItems.add(new ToDoItem("Sample"));
        writeToDoList();
    }

    public void removeItem() {
        //System.out.println("Removing item ...");
        //todoList.
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();//returns the selected item(not just the text, but all of the elements of the item/object). It is cast to the to do item
        System.out.println("Removing " + todoItem.getTodoText() + " ...");
        todoItems.remove(todoItem);
        writeToDoList();
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.setDone(!todoItem.isDone());
            todoList.setItems(null);//refreshing the list
            todoList.setItems(todoItems);
            writeToDoList();
        }
    }

    public void allDone() {
        System.out.println("Marking all items as done ...");
        //todoList.getSelectionModel().getSelectedItem();
        for (ToDoItem todoItem : todoItems)
        {
            todoItem.setDone(true);
            todoList.setItems(null);//refreshing the list
            todoList.setItems(todoItems);
            writeToDoList();

        }

    }

    public void allIncomplete() {
        System.out.println("Marking all items as incomplete ...");
        //ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        for (ToDoItem todoItem : todoItems)
        {
            todoItem.setDone(false);
            todoList.setItems(null);//refreshing the list
            todoList.setItems(todoItems);
        }
        writeToDoList();
    }

    public void toggleAll() {
        System.out.println("Toggling all items ...");
        //ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        for (ToDoItem todoItem : todoItems) {
            //todoItem.isDone = false;

            if (todoItem != null) {
                todoItem.setDone(!todoItem.isDone());
                todoList.setItems(null);//refreshing the list
                todoList.setItems(todoItems);
            }
        }
        writeToDoList();
    }

    public void test() {
        //It does not handle collections well unless they are in a container, so make sure your list of ToDos is in a container class before you ask the JSON serializer to serialize it.

        //An ObservableList is an interface, which means that if your container defines a variable of type ObservableList, the deserializer will not be able to create a new instance of
        //it to populate the right values into it. You need to think of a way that your container can have a list of a concrete type (such as ArrayList) and how you can use it to populate
        //an ObservableList that you can then use with your UI.
        try {
            System.out.println("test()");

            ToDoItem testTD = new ToDoItem("Test Serialization", false);
            System.out.println("test TD = " + testTD);
            saveTD(testTD);

            ToDoItem retrievedTD = restoreTD();
            System.out.println("retrieved TD = " + retrievedTD);
            String jsonTD = jsonSave(testTD);
            System.out.println("JSON ToDo = " + jsonTD);

            ToDoItem restoredFromJSON = jsonRestore(jsonTD);
            System.out.println("Restored from JSON = " + restoredFromJSON);

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public void saveTD(ToDoItem todoToSave) throws IOException {
        FileOutputStream fos = new FileOutputStream(DATA_FILE_NAME);
        ObjectOutput objectOut = new ObjectOutputStream(fos);
        objectOut.writeObject(todoToSave);
        objectOut.flush();
    }

    public ToDoItem restoreTD() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(DATA_FILE_NAME);
        ObjectInputStream objectIn = new ObjectInputStream(fis);
        ToDoItem restoredTD = (ToDoItem)objectIn.readObject();

        return restoredTD;
    }

    public String jsonSave(ToDoItem todoToSave) {//should be called json serializable
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(todoToSave);//get string back of the object

        return jsonString;
    }

    public ToDoItem jsonRestore(String jsonTD) {
        JsonParser toDoItemParser = new JsonParser();
        ToDoItem item = toDoItemParser.parse(jsonTD, ToDoItem.class);//.class represents the blueprint of the class not an instance


        return item;
    }

    public Boolean isCustomerReturning(String input)
    {
        boolean returningCustomer = false;
        try {

            File testFile = new File(input + ".txt");
            if(testFile.exists())
            {
                returningCustomer = true;
            }
            else
            {
                returningCustomer = false;
            }
            //Scanner fileScanner = new Scanner(testFile);
            //String[] brokenString = fileScanner.nextLine().split(",");
//            for (int index = 0; index < brokenString.length; index++)
//            {
//                if (brokenString[index].equals(input))
//                {
//                    returningCustomer = true;
//                    break;
//                }
//                else
//                {
//                    returningCustomer = false;
//                }
//            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return returningCustomer;
    }
    private void writeToDoList()
    {
        try {
            myUser.toDoList = new ArrayList<>();
            //loop through the observable list and add the to do items 1 by 1 to the arraylist
            //myUser.toDoList = (ArrayList<ToDoItem>) todoItems;
            for (ToDoItem currentItem : todoItems)
            {
                //.add(currentItem);
                myUser.toDoList.add(currentItem);
            }
            JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
            //String jsonString = jsonSerializer.serialize(todoItems);//get string back of the object
            // change the todoItems being passed to the .serialize method to an insance of the User class which will contain the arrayList as a member variable
            String jsonString = jsonSerializer.serialize(myUser);
            FileWriter writer = new FileWriter(name + ".json");
            writer.write(jsonString);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("This is handled");
        }
        //return jsonString;
    }
    private void readToDoList()//should probably be broken into 2 methods
    {
        //BufferedReader reader = new BufferedReader(name + ".json");

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(name + ".json"));
            StringBuilder sb = new StringBuilder();//understanding the StringBuilder
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();

            }


            //reader.
            JsonParser toDoItemParser = new JsonParser();
            //ArrayList tempList = toDoItemParser.parse(sb.toString(), ArrayList.class);
            //.class represents the blueprint of the class not an instance//this will be returned to a container item(User)
            //with instantiated array list member variable rather than directly to an array list as seen here...
            myUser = toDoItemParser.parse(sb.toString(), UserList.class);
            System.out.println(myUser);
            for (ToDoItem currentItem : myUser.toDoList)
            {
                //ToDoItem item = new ToDoItem(text, done);
                todoItems.add(currentItem);
            }
//            for (Object obj: tempList)
//            {
//
//                System.out.println(obj);
//                Map map = (Map)obj;
//                String text = (String)map.get("todoText");
//                boolean done = (boolean)map.get("done");
//                ToDoItem item = new ToDoItem(text, done);
 //               todoItems.add(item);
//            }

            //return item;
        }
        catch(IOException e)
        {
            //e.printStackTrace();
        }
    }
    private static class UserList {
        //private String name;
        public static ArrayList<ToDoItem> toDoList = new ArrayList<>();

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

        public ArrayList<ToDoItem> getToDoList() {
            return toDoList;
        }

        public void setToDoList(ArrayList<ToDoItem> toDoListy) {
            toDoList = toDoListy;
        }
    }


}
