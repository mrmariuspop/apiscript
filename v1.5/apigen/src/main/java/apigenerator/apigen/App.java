package apigenerator.apigen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static apigenerator.apigen.Utils.containsOnlyAlpha;

public class App extends Application {
    private StackPane root = new StackPane();
    private Stage stage;

    @Override
    public void init() {
        Button button = new Button("OPEN");
        VBox vBox = new VBox();

        vBox.setSpacing(8);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        TextArea sqlTextField = new TextArea();
        Button generateBtn = new Button("GENERATE");
        TextField entityNameTextField = new TextField();
        Label deisgnerLabel = new Label("Designed by Marius Pop (marius1.pop@btrl.ro)");
        deisgnerLabel.setTextFill(Color.GRAY);
        Label generatedClassesLbl = new Label("Empty label");
        generatedClassesLbl.setVisible(false);
        CheckBox createDirectoriesCheckBox = new CheckBox("Create directories");
        vBox.getChildren().addAll(
                deisgnerLabel,
                new Label("Entity Name"),
                entityNameTextField,
                new Label("Sql query"),
                sqlTextField,
                createDirectoriesCheckBox,
                generateBtn,
                generatedClassesLbl);
        root.getChildren().addAll(vBox);

        button.setOnAction(actionEvent -> {
            if (stage != null) {
                stage.requestFocus();
                return;
            }
            stage = new Stage();
            StackPane stackPane = new StackPane();
            stage.setScene(new Scene(stackPane, 200, 200));
            stage.show();
        });

        generateBtn.setOnAction(actionEvent -> {
            Set<String> queryParams = Utils.extractQueryParams(sqlTextField.getText());
            List<String> listOfParams = Utils.formatQueryParams(Utils.getAttributesFromSet(queryParams));

            //validations on entity field
            if (sqlTextField.getText().equals("")
                    || entityNameTextField.getText().equals("")) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Please fill in all the fields");
            } else if (entityNameTextField.getText().contains(" ")) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Entity name can not have spaces");
            } else if (entityNameTextField.getText().charAt(0) == Character.toLowerCase(entityNameTextField.getText().charAt(0))) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Entity name must start with capital");
            } else if (!containsOnlyAlpha(entityNameTextField.getText())) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Entity name must have only alpha numeric values");
            }
            //validations on sql field
            else if (!sqlTextField.getText().toUpperCase().contains("SELECT")) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Not a valid sql statement");
            } else {
                try {
                    ControlPanel.generateClasses(
                            sqlTextField.getText(),
                            entityNameTextField.getText(),
                            Utils.getAuthorFromPrincipal(System.getProperty("user.name")),
                            queryParams.size(),
                            queryParams,
                            listOfParams,
                            createDirectoriesCheckBox.isSelected());
                    generatedClassesLbl.setVisible(true);
                    generatedClassesLbl.setTextFill(Color.GREEN);
                    generatedClassesLbl.setText("Classes have been generated");

                } catch (Exception e) {
                    generatedClassesLbl.setVisible(true);
                    generatedClassesLbl.setTextFill(Color.RED);
                    generatedClassesLbl.setText("Classes not generated. Errors in query");
                }
            }

        });
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("ApiScript v1.3");
        primaryStage.setAlwaysOnTop(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}