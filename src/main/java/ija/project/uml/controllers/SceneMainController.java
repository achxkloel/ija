/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml.controllers;

import ija.project.uml.*;
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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Controller for the main scene, which displays diagrams.
 */
public class SceneMainController {

    @FXML
    private Pane mainPane;

    @FXML
    private Pane sequencePane;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab classTab;

    @FXML
    private Tab sequenceTab;

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private Stage stage;

    private ClassDiagram classDiagram;
    private SequenceDiagram sequenceDiagram;
    private ContextMenu contextMenu;
    private final List<VBox> classVboxList = new ArrayList<>();
    private final List<VBox> sequenceVboxList = new ArrayList<>();

    private double positionX = 50;
    private double positionY = 50;


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
     * method to display the diagram
     */
    public void displayVbox() {
        List<UMLClass> classList = this.classDiagram.getClassList();

        for (UMLClass currentClass : classList)
            this.createClassVBox(currentClass);

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

        classVboxList.add(vbox);

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

    @FXML
    private void addRelationWindow () {
        if (classDiagram.getClassList().size() < 2) {
            return;
        }

        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneAddUMLRelation.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneAddUMLRelationController sceneAddUMLRelationController = loader.getController();
        sceneAddUMLRelationController.setParentDiagram(classDiagram, mainPane, this);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Add new relation");
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

    private void editRelationWindow (UMLRelation relation) {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditUMLRelation.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SceneEditUMLRelationController sceneEditUMLRelationController = loader.getController();
        sceneEditUMLRelationController.setRelation(relation, classDiagram, this);

        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Edit relation");
        stage.setMinWidth(300);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clearDiagram() {
        mainPane.getChildren().clear();
        classDiagram.clear();
    }

    @FXML
    private void saveClassDiagram () throws IOException {
        DataParser.saveClassDiagram(classDiagram, stage);
    }

    @FXML
    private void openSequenceDiagram() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Sequence Diagram File");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) return;

        sequenceDiagram = DataParser.parseSequenceDiagram(selectedFile);

        if (sequenceDiagram == null) return;

        sequenceTab.setDisable(false);
        tabPane.getSelectionModel().select(sequenceTab);

        for (String object : sequenceDiagram.getSequenceObjects())
            createSequenceVBox(object);

        for (VBox vbox : sequenceVboxList)
            sequencePane.getChildren().add(vbox);
    }

    private void createSequenceVBox(String name) {
        VBox vbox = getSequenceVBox(name);
        vbox.setTranslateX(positionX);
        vbox.setTranslateY(positionY);
        positionX += 150;
        sequenceVboxList.add(vbox);
    }

    public VBox getSequenceVBox(String name) {
        final VBox vbox = new VBox();

        String cssLayoutVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setStyle(cssLayoutVbox);
        vbox.getChildren().add(getLabelFromString(name));

        vbox.toFront();

        return vbox;
    }

    private Label getLabelFromString(String string) {
        Label label = new Label();
        label.setText(string);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
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

            addPolygon(currentRelation);
        }

        moveVBoxesToFront();
    }

    public void moveVBoxesToFront() {
        for (VBox vbox : this.classVboxList)
            vbox.toFront();
    }

    public void clearPolygon(UMLRelation relation) {
        mainPane.getChildren().remove(relation.getArrow());
    }

    public void addPolygon(UMLRelation relation) {
        if (relation.getArrow() != null)
            mainPane.getChildren().add(relation.getArrow());
    }

    EventHandler<MouseEvent> vboxOnMousePressedEventHandler = t -> {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((VBox)(t.getSource())).getTranslateX();
        orgTranslateY = ((VBox)(t.getSource())).getTranslateY();
    };

    EventHandler<MouseEvent> vboxOnMouseDraggedEventHandler = t -> {
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
                    clearPolygon(currentRelation);

                currentRelation.updateCoordinates();
            }
            else continue;

            addPolygon(currentRelation);
        }

        ((VBox)(t.getSource())).toFront();

        ((VBox)(t.getSource())).setTranslateX(newTranslateX);
        ((VBox)(t.getSource())).setTranslateY(newTranslateY);
    };

    EventHandler<MouseEvent> vboxOnMouseClickedEventHandler = t -> {
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

            Menu editRelationMenu = new Menu("Edit relations");

            // Set event handlers to relations
            for (UMLRelation relation : classDiagram.findRelationsForClass(currClass.getName())) {
                StringBuilder menuItemText = new StringBuilder("with ");

                if (relation.getTarget().equals(currClass.getName())) {
                    menuItemText.append(relation.getSource());
                } else {
                    menuItemText.append(relation.getTarget());
                }

                MenuItem editRelation = new MenuItem(menuItemText.toString());

                editRelation.setOnAction(e -> editRelationWindow(relation));

                editRelationMenu.getItems().add(editRelation);
            }

            SeparatorMenuItem attributeSeparator = new SeparatorMenuItem();
            SeparatorMenuItem methodSeparator = new SeparatorMenuItem();
            SeparatorMenuItem relationSeparator = new SeparatorMenuItem();

            contextMenu.getItems().addAll(
                itemClassEdit,
                attributeSeparator,
                itemAddAttr,
                editAttributesMenu,
                methodSeparator,
                itemAddMethod,
                editMethodMenu,
                relationSeparator,
                editRelationMenu
            );
            contextMenu.show(mainPane, t.getScreenX(), t.getScreenY());

            itemClassEdit.setOnAction(e -> editClassWindow(currClass));

            itemAddAttr.setOnAction(e -> addAttributeWindow(currClass));

            itemAddMethod.setOnAction(e -> addMethodWindow(currClass));
        }
    };

    EventHandler<MouseEvent> mainPaneOnMouseClicked = t -> {
        if (t.getButton().equals(MouseButton.PRIMARY))
            if (contextMenu != null) contextMenu.hide();
    };
}
