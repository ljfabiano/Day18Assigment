package sample;

/**
 * Created by jfabiano on 8/31/2016.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

//        Scanner bankScanner = new Scanner(System.in);
//        //Ask the user for their name
//        System.out.println("Please enter your name:");
//        String nameInput = bankScanner.nextLine();
        //Check if they have an existing list of ToDos
        //Main myRunner = new Main();
        //boolean returningCustomer = myRunner.isCustomerReturning(nameInput);
        //If they do, restore it into the observable list
        //if (returningCustomer == true)
        //{



        //}
        //Start the UI. I think this is the step below...(launch?)
        launch(args);//start is called at some point, and I need to look up what this method is... If I want to know
        //Make sure the ToDos are saved as the user makes changes to them

    }
    public Boolean isCustomerReturning(String input)
    {
        boolean returningCustomer = false;
        try {
            File testFile = new File("src/sample/bank.txt");
            Scanner fileScanner = new Scanner(testFile);
            String[] brokenString = fileScanner.nextLine().split(",");
            for (int index = 0; index < brokenString.length; index++)
            {
                if (brokenString[index].equals(input))
                {
                    returningCustomer = true;
                    break;
                }
                else
                {
                    returningCustomer = false;
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return returningCustomer;
    }
}
