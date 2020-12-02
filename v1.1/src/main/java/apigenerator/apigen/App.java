package apigenerator.apigen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

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
        TextField authorTextField = new TextField();
        TextField entityNameTextField = new TextField();
        TextField inputParamsTextField = new TextField();
        Label deisgnerLabel = new Label("Designed by Marius Pop (marius1.pop@btrl.ro)");
        deisgnerLabel.setTextFill(Color.GRAY);
        Label generatedClassesLbl = new Label("Empty label");
        generatedClassesLbl.setVisible(false);
        vBox.getChildren().addAll(
                deisgnerLabel,
                new Label("Your Full Name"),
                authorTextField,
                new Label("Entity Name"),
                entityNameTextField,
                new Label("Sql query"),
                sqlTextField,
                new Label("Number of input params"),
                inputParamsTextField,
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
            System.out.println(sqlTextField.getText());
            System.out.println(authorTextField.getText());
            System.out.println(entityNameTextField.getText());
            System.out.println(inputParamsTextField.getText());
            if (sqlTextField.getText().equals("")
                    || authorTextField.getText().equals("")
                    || inputParamsTextField.getText().equals("")
                    || entityNameTextField.getText().equals("")) {
                generatedClassesLbl.setVisible(true);
                generatedClassesLbl.setTextFill(Color.RED);
                generatedClassesLbl.setText("Please fill in all the fields");
            } else {
                try {
                    ControlPanel.generateClasses(
                            sqlTextField.getText(),
                            entityNameTextField.getText(),
                            authorTextField.getText(),
                            Integer.parseInt(inputParamsTextField.getText()));
                    generatedClassesLbl.setVisible(true);
                    generatedClassesLbl.setTextFill(Color.GREEN);
                    generatedClassesLbl.setText("Classes have been generated");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("ApiScript v1.0");
        primaryStage.setAlwaysOnTop(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}