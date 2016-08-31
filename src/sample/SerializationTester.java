package sample;

/**
 * Created by jfabiano on 8/31/2016.
 */
import java.io.*;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

public class SerializationTester {

    final String DATA_FILE_NAME = "todo.dat";

    public static void main(String[] args) {
        System.out.println("Running serialization tester ...");

        new SerializationTester().test();
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

    public String jsonSave(ToDoItem todoToSave) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(todoToSave);//get string back of the object

        return jsonString;
    }

    public ToDoItem jsonRestore(String jsonTD) {
        JsonParser toDoItemParser = new JsonParser();
        ToDoItem item = toDoItemParser.parse(jsonTD, ToDoItem.class);//.class represents the blueprint of the class not an instance


        return item;
    }



}