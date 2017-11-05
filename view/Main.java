package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Groupable;
import models.MainModel;
import controlers.Controller;

//import java.awt.*;
import java.util.*;

import static view.GroupableViews.buildSelectionMenu;

public class Main extends Application implements Observer {
    private static HashMap<String, StackPane> checkListBtns = new HashMap<>(); // <Name of object, Rectangle + Text>
    private static Controller controller;
    private static Stage mainStage;
    private static GroupableViews groupableViews;
    private Scene mainScene = new Scene(buildMainMenu());

    public Main(){
        controller = new Controller();
        controller.addObserver(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage; // Allows manipulation of the Stage in other methods

        primaryStage.setTitle("Travlr");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    /**
     * Builds the main menu screen that is shown on launch of the app
     * @return a BorderPane containing the main menu
     */
    public static BorderPane buildMainMenu(){
        BorderPane main = new BorderPane();
        main.setPrefSize(500, 750);

        // Top of BP

        main.setTop(title());

        // Center of BP

        VBox boxContainer = new VBox();
        boxContainer.setSpacing(10);
        ScrollPane checkList = new ScrollPane(boxContainer);

        for(StackPane i : checkListBtns.values()){
            boxContainer.getChildren().add(i);
        }

        main.setCenter(checkList);

        // Bottom of BP

        main.setBottom(buildContrlPanel());

        return main;
    }

    /**
     * The title at the top of each scene
     * @return A HBox that contains the title
     */
    public static HBox title(){
        Text title = new Text("Travlr");
        title.setFont(new Font("Verdana", 50));
        HBox topBox = new HBox(title);

        return topBox;
    }

    /**
     * Builds a control panel that edits the main check list
     * @return And HBox that represents control panel
     */
    private static HBox buildContrlPanel() {
        HBox main = new HBox();

        Button addEntry = new Button("Add Entry");
        addEntry.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event ->
                    switchScene(new Scene(new GroupableViews(controller).buildIndivView(null, null)))
                );

        Button addGroup = new Button("Add Group");
        addGroup.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event ->
                    switchScene(new Scene(new GroupableViews(controller).buildGroupView(null)))
                );

        main.getChildren().addAll(addEntry, addGroup);

        return main;
    }


    /**
     * Makes the stage switch scenes
     * @param newScene The scene to switch to
     */
    public static void switchScene(Scene newScene){
        mainStage.setScene(newScene);
    }

    /**
     * Generates an alert box
     * @param alertType The type of alert
     * @param title The title of the alert
     * @param header The header of the alert
     * @param content The content of the alert
     * @return An alert with the specified info
     */
    public static Alert createAlert(Alert.AlertType alertType, String title, String header, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }


    /**
     * Getter for the checkListBtns object
     * @return the list of all buttons associated with the checklist
     */
    public static HashMap<String, StackPane> getcheckListBtns(){
        return checkListBtns;
    }

    /**
     * Adds a stackpane to the hash that represents an entry
     * @param name Name of entry
     * @param quantity The amount of the entry in your possession
     */
    public static void addEntryBtn(String name, String quantity){
        controller.addEntry(name, Integer.parseInt(quantity));

        Rectangle recBtn = new Rectangle(450, 100, Paint.valueOf("Blue"));
        Text txt = new Text(name + "\nQuantity: " + quantity);

        StackPane stack = new StackPane(recBtn, txt);
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> switchScene(new Scene(buildSelectionMenu(name, quantity))));

        checkListBtns.put(name, stack);
    }

    /**
     * Adds a stackpane to the hash that represents an entry, for an object that already exists
     * @param name Name of entry
     * @param quantity The amount of the entry in your possession
     */
    public static void editEntryBtn(String name, String quantity){
        Rectangle recBtn = new Rectangle(450, 100, Paint.valueOf("Blue"));
        Text txt = new Text(name + "\nQuantity: " + quantity);

        StackPane stack = new StackPane(recBtn, txt);
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> switchScene(new Scene(buildSelectionMenu(name, quantity))));

        checkListBtns.put(name, stack);
    }


    /**
     * Adds a stackpane to the hash that represents a group
     * @param name Name of the group
     */
    public static void addGroupBtn(String name){
        controller.addGroup(name);
        Rectangle recBtn = new Rectangle(450, 100, Paint.valueOf("Red"));
        Text nametxt = new Text(name);

        StackPane stack = new StackPane(recBtn,nametxt);
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> switchScene(new Scene(buildSelectionMenu(name, null))));

        checkListBtns.put(name, stack);
    }

    /**
     * Adds a stackpane to the hash that represents a group, for an object that already exists
     * @param name Name of the group
     */
    public static void editGroupBtn(String name){
        Rectangle recBtn = new Rectangle(450, 100, Paint.valueOf("Red"));
        Text nametxt = new Text(name);

        StackPane stack = new StackPane(recBtn,nametxt);
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> switchScene(new Scene(buildSelectionMenu(name, null))));

        checkListBtns.put(name, stack);
    }

    public void update(Observable t, Object o){
        //TODO
        // Check model and allow for manipulation of checklist buttons


    }

    public static void main(String[] args) {
        launch(args);
    }
}
