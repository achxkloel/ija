/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    private Pane mainPane;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private Stage stage;

    private ClassDiagram classDiagram;
    private ContextMenu contextMenu;
    private final List<VBox> classVboxList = new ArrayList<>();

    public void setClassDiagram (ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
        this.classDiagram.setDiagramView(this.mainPane);
    }

    private Label getLabel(UMLClass umlClass) {
        Label label = new Label();
        umlClass.setNameView(label);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }

    private VBox getAttributeList(UMLClass umlClass) {
        List<Attribute> attributeList = umlClass.getAttributeList();
        final VBox vboxAttributes = new VBox();
        umlClass.setAttributeView(vboxAttributes);

        for (Attribute currentAttribute : attributeList) {
            Text attribute = new Text();
            currentAttribute.setTextView(attribute);
            attribute.setText(currentAttribute.toString());
            vboxAttributes.getChildren().add(attribute);
        }

        String cssLayoutNestedVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid none none none;\n" +
                "-fx-background-color: white;\n" +
                "-fx-padding: 3px";
        vboxAttributes.setStyle(cssLayoutNestedVbox);
        return vboxAttributes;
    }

    private VBox getMethodList(UMLClass umlClass) {
        List<Method> methodList = umlClass.getMethodList();
        final VBox vboxMethods = new VBox();
        umlClass.setMethodView(vboxMethods);

        for (Method currentMethod : methodList) {
            Text methods = new Text();
            currentMethod.setTextView(methods);
            methods.setText(currentMethod.toString());
            vboxMethods.getChildren().add(methods);
        }

        String cssLayoutNestedVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid none none none;\n" +
                "-fx-background-color: white;\n" +
                "-fx-padding: 3px";
        vboxMethods.setStyle(cssLayoutNestedVbox);

        return vboxMethods;
    }

    /**
     * method to display a string, that was passed to it
     */
    public void displayVbox() {
        List<UMLClass> classList = this.classDiagram.getClassList();

        for (UMLClass currentClass : classList) {
            classVboxList.add(this.createClassVBox(currentClass));
        }

        for (VBox vbox : this.classVboxList)
            mainPane.getChildren().add(vbox);

        mainPane.setOnMouseClicked(mainPaneOnMouseClicked);
    }

    public VBox createClassVBox (UMLClass umlClass) {
        final VBox vbox = new VBox();
        umlClass.setClassView(vbox);

        String cssLayoutVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setId(this.generateClassId(umlClass.getName()));
        vbox.setStyle(cssLayoutVbox);
        vbox.getChildren().add(getLabel(umlClass));
        vbox.getChildren().add(getAttributeList(umlClass));
        vbox.getChildren().add(getMethodList(umlClass));

        vbox.setOnMousePressed(vboxOnMousePressedEventHandler);
        vbox.setOnMouseDragged(vboxOnMouseDraggedEventHandler);
        vbox.setOnMouseClicked(vboxOnMouseClickedEventHandler);

        vbox.toFront();
        vbox.setTranslateX(umlClass.getPositionX());
        vbox.setTranslateY(umlClass.getPositionY());

        return vbox;
    }

    private String generateClassId(String className) {
        return className.replaceAll("\\s+", "_").toLowerCase();
    }

    @FXML
    private void addClassWindow () {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneAddUMLClass.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneAddUMLClassController sceneAddUMLClassController = loader.getController();
        sceneAddUMLClassController.setMainController(this);
        sceneAddUMLClassController.setParentDiagram(classDiagram);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Add new class");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private void editClassWindow (UMLClass currClass) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditUMLClass.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneEditUMLClassController sceneEditUMLClassController = loader.getController();
        sceneEditUMLClassController.setUMLClass(currClass, classDiagram);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Edit UMLClass");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void editDiagramWindow () {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditDiagram.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneEditDiagramController sceneEditDiagramController = loader.getController();
        sceneEditDiagramController.setDiagram(classDiagram, this.stage);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Rename diagram");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private void addAttributeWindow (UMLClass currClass) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneAddAttribute.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneAddAttributeController sceneAddAttributeController = loader.getController();
        sceneAddAttributeController.setUMLClass(currClass);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Add class attribute");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private void addMethodWindow (UMLClass currClass) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneAddMethod.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneAddMethodController sceneAddMethodController = loader.getController();
        sceneAddMethodController.setUMLClass(currClass);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Add class method");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private void editAttributeWindow (Attribute attr, UMLClass parentClass) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditAttribute.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneEditAttributeController sceneEditAttributeController = loader.getController();
        sceneEditAttributeController.setAttribute(attr, parentClass);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Edit attribute");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private void editMethodWindow (Method method, UMLClass parentClass) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditMethod.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneEditMethodController sceneEditMethodController = loader.getController();
        sceneEditMethodController.setMethod(method, parentClass);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Edit method");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    public void closeDiagram() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneWelcome.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        stage.show();
    }

    @FXML
    private void saveClassDiagram () throws IOException {
        JSONObject classDiagramObject = new JSONObject();
        classDiagramObject.put("type", "ClassDiagram");
        classDiagramObject.put("name", classDiagram.getName());

        JSONArray umlClassArray = new JSONArray();

        for (UMLClass umlClass : classDiagram.getClassList()) {
            JSONObject umlClassObject = new JSONObject();
            umlClassObject.put("name", umlClass.getName());

            VBox classVBox = umlClass.getClassView();
            umlClassObject.put("positionX", classVBox.getTranslateX());
            umlClassObject.put("positionY", classVBox.getTranslateY());

            JSONArray umlClassItemsArray = new JSONArray();

            for (Attribute attribute : umlClass.getAttributeList()) {
                JSONObject umlClassItemObject = new JSONObject();
                umlClassItemObject.put("item_type", "Attribute");
                umlClassItemObject.put("visibility", attribute.getVisibility());
                umlClassItemObject.put("name", attribute.getName());
                umlClassItemObject.put("type", attribute.getType());
                umlClassItemsArray.put(umlClassItemObject);
            }

            for (Method method : umlClass.getMethodList()) {
                JSONObject umlClassItemObject = new JSONObject();
                umlClassItemObject.put("item_type", "Method");
                umlClassItemObject.put("visibility", method.getVisibility());
                umlClassItemObject.put("name", method.getName());
                umlClassItemObject.put("type", method.getType());

                JSONArray methodParamsArray = new JSONArray();
                for (Attribute methodParam : method.getAttributeList()) {
                    JSONObject methodParamObject = new JSONObject();
                    methodParamObject.put("name", methodParam.getName());
                    methodParamObject.put("type", methodParam.getType());
                    methodParamsArray.put(methodParamObject);
                }

                umlClassItemObject.put("params", methodParamsArray);
                umlClassItemsArray.put(umlClassItemObject);
            }

            umlClassObject.put("items", umlClassItemsArray);
            umlClassArray.put(umlClassObject);
        }

        JSONArray relationsArray = new JSONArray();

        for (UMLRelation relation : classDiagram.getRelationList()) {
            JSONObject relationObject = new JSONObject();
            relationObject.put("source", relation.getSource());
            relationObject.put("target", relation.getTarget());
            relationObject.put("type", relation.getType());
            relationObject.put("name", relation.getName());
            relationObject.put("cardinality_from", relation.getCardinalityFrom());
            relationObject.put("cardinality_to", relation.getCardinalityTo());
            relationsArray.put(relationObject);
        }

        classDiagramObject.put("classes", umlClassArray);
        classDiagramObject.put("relations", relationsArray);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose folder to save diagram");
        fileChooser.setInitialFileName(classDiagram.getName() + ".json");
        fileChooser.setSelectedExtensionFilter( new FileChooser.ExtensionFilter("JSON files", "*.json" ));
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            try (PrintWriter outputFile = new PrintWriter(selectedFile.getAbsolutePath(), StandardCharsets.UTF_8)) {
                outputFile.println(classDiagramObject.toString(2));
            }
        }
    }

    public void quitApplication() {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        List<UMLRelation> relationList = this.classDiagram.getRelationList();

        for (UMLRelation currentRelation : relationList) {
            for (VBox vbox : this.classVboxList) {
                if (Objects.equals(vbox.getId(), generateClassId(currentRelation.getSource())))
                    currentRelation.setVboxFrom(vbox);
                if (Objects.equals(vbox.getId(), generateClassId(currentRelation.getTarget())))
                    currentRelation.setVboxTo(vbox);
            }

            Line line = new Line();
            currentRelation.setLine(line);
            currentRelation.updateCoordinates();

            mainPane.getChildren().add(currentRelation.getLine());

            if (currentRelation.getArrow() != null)
                mainPane.getChildren().add(currentRelation.getArrow());
        }

        for (VBox vbox : this.classVboxList)
            vbox.toFront();
    }

    EventHandler<MouseEvent> vboxOnMousePressedEventHandler =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((VBox)(t.getSource())).getTranslateX();
                orgTranslateY = ((VBox)(t.getSource())).getTranslateY();
            }
        };

    EventHandler<MouseEvent> vboxOnMouseDraggedEventHandler =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                double newTranslateX = orgTranslateX + t.getSceneX() - orgSceneX;
                double newTranslateY = orgTranslateY + t.getSceneY() - orgSceneY;

                double originalX = ((VBox)(t.getSource())).getLayoutX() + ((VBox)(t.getSource())).getWidth() / 2;
                double originalY = ((VBox)(t.getSource())).getLayoutY() + ((VBox)(t.getSource())).getHeight() / 2;

                double paneX = mainPane.getWidth();
                double paneY = mainPane.getHeight();

                if (originalX + newTranslateX < 0) newTranslateX = -originalX;
                if (originalX + newTranslateX > paneX) newTranslateX = paneX - originalX;
                if (originalY + newTranslateY < 0) newTranslateY = -originalY;
                if (originalY + newTranslateY > paneY) newTranslateY = paneY - originalY;

                List<UMLRelation> relationList = classDiagram.getRelationList();

                for (UMLRelation currentRelation : relationList) {
                    if (Objects.equals(currentRelation.getVboxFrom().getId(), ((VBox) (t.getSource())).getId()) ||
                            Objects.equals(currentRelation.getVboxTo().getId(), ((VBox) (t.getSource())).getId())) {
                        if (currentRelation.getArrow() != null)
                            mainPane.getChildren().remove(currentRelation.getArrow());

                        currentRelation.updateCoordinates();
                    }
                    else continue;

                    if (currentRelation.getArrow() != null)
                        mainPane.getChildren().add(currentRelation.getArrow());
                }

                ((VBox)(t.getSource())).toFront();

                ((VBox)(t.getSource())).setTranslateX(newTranslateX);
                ((VBox)(t.getSource())).setTranslateY(newTranslateY);
            }
        };

    EventHandler<MouseEvent> vboxOnMouseClickedEventHandler =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                if (contextMenu != null) contextMenu.hide();
                if (t.getButton().equals(MouseButton.SECONDARY)) {
                    VBox currentVBox = ((VBox)(t.getSource()));
                    Label currVBoxLabel = (Label)(currentVBox.getChildren().get(0));
                    UMLClass currClass = classDiagram.findClass(currVBoxLabel.getText());

                    contextMenu = new ContextMenu();
                    MenuItem itemClassEdit = new MenuItem("Edit class");
                    MenuItem itemAddAttr = new MenuItem("Add attribute");
                    Menu editAttributesMenu = new Menu("Edit Attributes");

                    // Set event handlers to attributes
                    for (Attribute attr : currClass.getAttributeList()) {
                        MenuItem editAttr = new MenuItem(attr.getName());

                        editAttr.setOnAction(e -> editAttributeWindow(attr, currClass));

                        editAttributesMenu.getItems().add(editAttr);
                    }

                    MenuItem itemAddMethod = new MenuItem("Add method");
                    Menu editMethodMenu = new Menu("Edit Methods");

                    // Set event handlers to methods
                    for (Method method : currClass.getMethodList()) {
                        MenuItem editMethod = new MenuItem(method.getName());

                        editMethod.setOnAction(e -> editMethodWindow(method, currClass));

                        editMethodMenu.getItems().add(editMethod);
                    }

                    SeparatorMenuItem attributeSeparator = new SeparatorMenuItem();
                    SeparatorMenuItem methodSeparator = new SeparatorMenuItem();

                    contextMenu.getItems().addAll(
                        itemClassEdit,
                        attributeSeparator,
                        itemAddAttr,
                        editAttributesMenu,
                        methodSeparator,
                        itemAddMethod,
                        editMethodMenu
                    );
                    contextMenu.show(mainPane, t.getScreenX(), t.getScreenY());

                    itemClassEdit.setOnAction(e -> editClassWindow(currClass));

                    itemAddAttr.setOnAction(e -> addAttributeWindow(currClass));

                    itemAddMethod.setOnAction(e -> addMethodWindow(currClass));
                }
            }
        };

    EventHandler<MouseEvent> mainPaneOnMouseClicked =
        new EventHandler<>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getButton().equals(MouseButton.PRIMARY))
                    if (contextMenu != null) contextMenu.hide();
            }
        };

}
