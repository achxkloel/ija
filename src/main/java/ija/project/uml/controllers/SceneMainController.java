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
import javafx.scene.shape.Polygon;
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


    /**
     * Setter for the class diagram
     * @param classDiagram new class diagram
     */
    public void setClassDiagram (ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
        this.classDiagram.setDiagramView(this.mainPane);
    }

    /**
     * Generates a label for the class, that is passed to it
     * @param umlClass a class
     * @return label for the class in the parameter
     */
    private Label getLabel(UMLClass umlClass) {
        Label label = new Label();
        umlClass.setNameView(label);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }

    /**
     * Creates a VBox with a list of attributes
     * @param umlClass class, for which we want to get the attribute list
     * @return VBox containing the attributes
     */
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

    /**
     * Creates a VBox with a list of methods
     * @param umlClass class, for which we want to get the method list
     * @return VBox containing the methods
     */
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
     * A method, that calls other methods, which results in displaying the class diagram
     * on the screen.
     */
    public void displayVbox() {
        List<UMLClass> classList = this.classDiagram.getClassList();

        for (UMLClass currentClass : classList)
            this.createClassVBox(currentClass);

        for (VBox vbox : this.classVboxList)
            mainPane.getChildren().add(vbox);

        mainPane.setOnMouseClicked(mainPaneOnMouseClicked);
    }

    /**
     * Creates a VBox for a class, that is passed to it
     * @param umlClass a class, for which we want to get the VBox
     * @return VBox representation of the class
     */
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

    /**
     * Generates an id for a class - no uppercase letters and no white space characters
     * @param className the name of a class, from which we want to create the id
     * @return the processed id
     */
    private String generateClassId(String className) {
        return className.replaceAll("\\s+", "_").toLowerCase();
    }

    /**
     * Opens a window for adding a class.
     */
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

    /**
     * Opens a window for adding a relation.
     */
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

    /**
     * Opens a window for editing a class.
     */
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

    /**
     * Opens a window for adding a diagram name.
     */
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

    /**
     * Opens a window for adding an attribute to a class.
     */
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

    /**
     * Opens a window for adding a method to a class.
     */
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

    /**
     * Opens a window for editing an attribute.
     */
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

    /**
     * Opens a window for editing a method.
     */
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

    /**
     * Opens a window for editing a relation.
     */
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

    /**
     * Clears the diagram.
     */
    @FXML
    private void clearDiagram() {
        mainPane.getChildren().clear();
        classDiagram.clear();
    }

    /**
     * Calls a function in the DataParser, which exports the class diagram to a JSON file.
     * @throws IOException
     */
    @FXML
    private void saveClassDiagram () throws IOException {
        DataParser.saveClassDiagram(classDiagram, stage);
    }

    /**
     * Launches a file chooser and lets you select a JSON file, which represents a sequence diagram.
     */
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

        for (Message message : sequenceDiagram.getMessageList()) {
            positionY += 70;
            Line line = new Line();
            line.setStartX(message.getStartX());
            line.setEndX(message.getEndX());
            line.setStartY(positionY);
            line.setEndY(positionY);

            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(message.getEndX(), positionY,
                    message.getEndX() - 10, positionY - 10,
                    message.getEndX() - 10, positionY + 10);

            Text text = new Text(message.getStartX() + 10, positionY - 10, message.getName());

            sequencePane.getChildren().addAll(line, polygon, text);
        }
    }

    /**
     * Creates a VBox representation of a sequence
     * @param name name of the sequence
     */
    private void createSequenceVBox(String name) {
        final VBox vbox = new VBox();

        String cssLayoutVbox = "-fx-border-color: black;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n" +
                "-fx-background-color: white;\n";

        vbox.setStyle(cssLayoutVbox);
        vbox.getChildren().add(getLabelFromString(name));

        vbox.setTranslateX(positionX);
        vbox.setTranslateY(positionY);
        sequenceVboxList.add(vbox);

        Line line = new Line();
        line.setStartX(positionX + 40);
        line.setStartY(positionY);
        line.setEndX(positionX + 40);
        line.setEndY(sequencePane.getHeight() - positionY - 50);

        for (Message message : sequenceDiagram.getMessageList()) {
            if (Objects.equals(message.getSource(), name))
                message.setStartX(positionX + 40);
            if (Objects.equals(message.getTarget(), name))
                message.setEndX(positionX + 40);
        }

        sequencePane.getChildren().add(line);

        vbox.toFront();
        positionX += 150;
    }

    /**
     * Creates a Label from a string
     * @param string string, from which we want to create a label
     * @return label representation of the string
     */
    private Label getLabelFromString(String string) {
        Label label = new Label();
        label.setText(string);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }

    /**
     * Setter for the stage. Also, this function draws all relations including polygons representing
     * their types.
     * @param stage Stage to be set
     */
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

    /**
     * Moves all VBoxes from classVBoxList to front, so the relations are not covering them
     */
    public void moveVBoxesToFront() {
        for (VBox vbox : this.classVboxList)
            vbox.toFront();
    }

    /**
     * Removes a polygon representing a type from the mainPane
     * @param relation relation, which contains the polygon to be removed
     */
    public void clearPolygon(UMLRelation relation) {
        mainPane.getChildren().remove(relation.getArrow());
    }

    /**
     * Adds a polygon representing a type to the mainPane
     * @param relation relation, which contains the polygon to be added
     */
    public void addPolygon(UMLRelation relation) {
        if (relation.getArrow() != null)
            mainPane.getChildren().add(relation.getArrow());
    }

    /**
     * Handler for what should happen, when a VBox is pressed.
     * Saves some values, that are later needed for calculating the translation axis.
     */
    EventHandler<MouseEvent> vboxOnMousePressedEventHandler = t -> {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        orgTranslateX = ((VBox)(t.getSource())).getTranslateX();
        orgTranslateY = ((VBox)(t.getSource())).getTranslateY();
    };

    /**
     * Handles a situation, when a VBox is dragged around.
     * Makes sure, that everything moves accordingly.
     */
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

    /**
     * Checks for a click with a secondary mouse button.
     * Opens a context menu for editing
     */
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

    /**
     * Handles clicks on main pane.
     */
    EventHandler<MouseEvent> mainPaneOnMouseClicked = t -> {
        if (t.getButton().equals(MouseButton.PRIMARY))
            if (contextMenu != null) contextMenu.hide();
    };
}
