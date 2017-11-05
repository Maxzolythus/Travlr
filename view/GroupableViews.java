package view;

import controlers.Controller;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Group;
import models.Groupable;
import models.MainModel;

import java.util.*;

/**
 * The views that allow to add, edit, and view Groupable objects
 * Created by Maxzolythus on 2/16/2017.
 */
public class GroupableViews {
    private static Controller controller;
    private static HashMap<String, StackPane> checkListBtns = Main.getcheckListBtns();

    public GroupableViews(Controller controller){
        this.controller = controller;
    }

    /**
     * Builds the individual view of an entry used in adding and editing.
     * @return a borderpane containing the view
     */
    public static BorderPane buildIndivView(String name, String quantity){
        BorderPane main = new BorderPane();
        main.setPrefSize(500, 750);

        // Top of BP

        main.setTop(Main.title());

        // Center of BP

        VBox textContainer = new VBox();

        Label nameTxt = new Label("Name: ");
        TextField nameField = new TextField();

        Label quantityTxt = new Label("Quantity: ");
        TextField quantityField = new TextField();

        if(name != null && quantity != null){ // Then this is being called as an editing view
            nameField.setText(name);
            quantityField.setText(quantity);
        }

        textContainer.getChildren().addAll(nameTxt, nameField, quantityTxt, quantityField);
        main.setCenter(textContainer);

        // Bottom of BP

        Button submit = new Button("Submit");
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    if(quantityField.getText().equals("") || nameField.getText().equals("")){
                        Alert alert = Main.createAlert(Alert.AlertType.WARNING, "Warning", "One or more fields are empty",
                                "Please fill out ALL fields");

                        alert.showAndWait();
                    }
                    else{
                        if(java.util.regex.Pattern.matches("\\d+",quantityField.getText())){
                            if(name == null && quantity == null) { // Then we must create a new button
                                if(!controller.getCheckList().containsKey(nameField.getText())) {
                                    Main.addEntryBtn(nameField.getText(), quantityField.getText());
                                }
                                else{
                                    Alert alert = Main.createAlert(Alert.AlertType.ERROR, "Error", "Name is not unique",
                                            "Name MUST be unique");

                                    alert.showAndWait();

                                    return; // Stops auto-navigation to main menu
                                }
                            }
                            else{ // You're accessing this view to edit
                                controller.rename(name, nameField.getText());

                                // Edits the button on the main menu
                                checkListBtns.remove(name);
                                Main.editEntryBtn(nameField.getText(), quantityField.getText());

                                controller.changeQuantity(nameField.getText(), Integer.parseInt(quantityField.getText()));
                            }

                            Main.switchScene(new Scene(Main.buildMainMenu())); // Returns you to main menu
                        }
                        else{
                            Alert alert = Main.createAlert(Alert.AlertType.ERROR, "Error", "Quantity can't be non-numeric",
                                    "Please enter a number");

                            alert.showAndWait();
                        }
                    }
                });

        Button mainMenu = new Button("Return to Checklist");
        mainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event ->
                        Main.switchScene(new Scene(Main.buildMainMenu()))
        );

        HBox botBox = new HBox(submit, mainMenu);

        main.setBottom(botBox);


        return main;
    }

    /**
     * Builds the individual view of an entry used in adding and editing.
     * @return a borderpane containing the view
     */
    public static BorderPane buildGroupView(String name){
        BorderPane main = new BorderPane();
        main.setPrefSize(500, 750);

        // Top of BP

        main.setTop(Main.title());

        // Center of BP

        Label nameTxt = new Label("Name: ");
        TextField nameField = new TextField();

        if(name != null){ // Then this is being called as an editing view
            nameField.setText(name);
        }

        main.setCenter(new VBox(nameTxt, nameField));

        // Bottom of BP

        Button submit = new Button("Submit");
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    if(nameField.getText().equals("")){
                        Alert alert = Main.createAlert(Alert.AlertType.WARNING, "Warning", "One or more fields are empty",
                                "Please fill out ALL fields");

                        alert.showAndWait();
                    }
                    else{
                        if(name == null) {
                            if(!controller.getCheckList().containsKey(nameField.getText())) {
                                Main.addGroupBtn(nameField.getText());
                            }
                            else{
                                Alert alert = Main.createAlert(Alert.AlertType.ERROR, "Error", "Name is not unique",
                                        "Name MUST be unique");

                                alert.showAndWait();

                                return; // Stops auto-navigation to main menu
                            }
                        }
                        else{ //Editing option
                            controller.rename(name, nameField.getText());

                            // Changes button on main menu
                            checkListBtns.remove(name);
                            Main.editGroupBtn(nameField.getText());
                        }

                        Main.switchScene(new Scene(Main.buildMainMenu())); // Returns you to the main menu
                    }
                });

        HBox botBox = new HBox(submit, returnToMainMenu());

        main.setBottom(botBox);

        return main;
    }

    /**
     * Builds the menu that groupable objects show when clicked
     * @return BorderPane that contains the selection menu
     */
    public static BorderPane buildSelectionMenu(String name, String quantity){
        BorderPane main = new BorderPane();
        main.setPrefSize(500, 750);
        // Top of BP

        main.setTop(Main.title());

        // Center of BP

        Text editTxt = new Text("Edit");
        Rectangle editBtn = new Rectangle(500,50, Paint.valueOf("white"));

        Text moveTxt = new Text("Move");
        Rectangle moveBtn = new Rectangle(500,50, Paint.valueOf("white"));

        Text delTxt = new Text("Delete");
        Rectangle delBtn = new Rectangle(500,50, Paint.valueOf("white"));

        StackPane editStack = new StackPane(editBtn, editTxt);
        editStack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    if(quantity == null){ // Then it's a group
                        Main.switchScene(new Scene(buildGroupView(name)));
                    }
                    else{
                        Main.switchScene(new Scene(buildIndivView(name, quantity)));
                    }
                });

        StackPane moveStack = new StackPane(moveBtn, moveTxt);
        moveStack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    Main.switchScene(new Scene(buildMoveView(name)));
                });

        StackPane delStack = new StackPane(delBtn, delTxt);
        delStack.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    checkListBtns.remove(name);
                    controller.delete(name);

                    Main.switchScene(new Scene(Main.buildMainMenu()));
                });

        VBox buttonContainer = new VBox(editStack, moveStack, delStack);
        buttonContainer.setSpacing(10);

        main.setCenter(buttonContainer);

        // Bottom of BP

        main.setBottom(returnToMainMenu());

        return main;
    }

    /**
     * Builds the menu that allows you to move groupable objects
     * @return BorderPane that contains the moving menu
     */
    public static BorderPane buildMoveView(String name){
        BorderPane main = new BorderPane();
        main.setPrefSize(500, 750);

        //Top of BP

        main.setTop(Main.title());

        //Center of BP
        VBox cenContainer = new VBox();
        Text subtitle = new Text("Choose where to move\n" + name + ":");
        subtitle.setFont(new Font("Verdana", 40));

        cenContainer.getChildren().addAll(subtitle, buildOptions());

        main.setCenter(cenContainer);

        // A list of groups

        // Bottom of BP
        HBox botContainer = new HBox();

        botContainer.getChildren().addAll(returnToMainMenu());

        main.setBottom(botContainer);
        // Submit button and returnToMainMenu()

        return main;
    }

    private static ComboBox buildOptions(){
        ComboBox<String> main = new ComboBox<>();

        for(String itemName : controller.getCheckList().keySet()){
            Groupable item = controller.getCheckList().get(itemName);
            if(item instanceof Group){
                main.getItems().add(itemName);
            }
        }
        return main;
    }

    /**
     * Creates a button to return the user to the main menu
     * @return A button that takes you to the main menu
     */
    public static Button returnToMainMenu() {
        Button mainMenu = new Button("Return to Checklist");
        mainMenu.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event ->
                        Main.switchScene(new Scene(Main.buildMainMenu()))
        );

        return mainMenu;
    }
}
