/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        SceneMainController.java
 * Description: Controller for the main scene, which displays diagrams
 */

package ija.project.uml;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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

    private ClassDiagram classDiagram;
    private final List<VBox> classVboxList = new ArrayList<>();
    Line line;

    public void setClassDiagram (ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
    }

    private Label getLabel(String text) {
        Label label = new Label();
        label.setText(text);

        String cssLabel = "-fx-text-fill: black;\n" +
                "-fx-padding: 5px";

        label.setStyle(cssLabel);

        return label;
    }

    private VBox getAttributeList(List<Attribute> attributeList) {
        final VBox vboxAttributes = new VBox();

        for (Attribute currentAttribute : attributeList) {
            Text attribute = new Text();
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

    private VBox getMethodList(List<Method> methodList) {
        final VBox vboxMethods = new VBox();

        for (Method currentMethod : methodList) {
            Text methods = new Text();
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
            final VBox vbox = new VBox();
            String cssLayoutVbox = "-fx-border-color: black;\n" +
                    "-fx-border-width: 2;\n" +
                    "-fx-border-style: solid;\n" +
                    "-fx-background-color: white;\n";

            vbox.setId(this.generateClassId(currentClass.getName()));
            vbox.setStyle(cssLayoutVbox);
            vbox.getChildren().add(getLabel(currentClass.getName()));
            vbox.getChildren().add(getAttributeList(currentClass.getAttributeList()));
            vbox.getChildren().add(getMethodList(currentClass.getMethodList()));

            vbox.setOnMousePressed(vboxOnMousePressedEventHandler);
            vbox.setOnMouseDragged(vboxOnMouseDraggedEventHandler);
            vbox.setOnMouseClicked(vboxOnMouseClickedEventHandler);

            classVboxList.add(vbox);

            mainPane.getChildren().add(vbox);
        }

        List<UMLRelation> relationList = this.classDiagram.getRelationList();

        for (UMLRelation currentRelation : relationList) {
            for (VBox vbox : this.classVboxList) {
                if (Objects.equals(vbox.getId(), generateClassId(currentRelation.getSource())))
                    currentRelation.setVboxFrom(vbox);
                if (Objects.equals(vbox.getId(), generateClassId(currentRelation.getTarget())))
                    currentRelation.setVboxTo(vbox);
            }

            line = new Line();
            line.setStartX(currentRelation.getVboxFrom().getLayoutX());
            line.setStartY(currentRelation.getVboxFrom().getLayoutY());
            line.setEndX(currentRelation.getVboxTo().getLayoutX());
            line.setEndY(currentRelation.getVboxTo().getLayoutY());

            mainPane.getChildren().add(line);
        }
    }

    private String generateClassId(String className) {
        return className.replaceAll("\\s+", "_").toLowerCase();
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

                mainPane.getChildren().remove(line);
                List<UMLRelation> relationList = classDiagram.getRelationList();

                for (UMLRelation currentRelation : relationList) {
                    VBox source = currentRelation.getVboxFrom();
                    VBox target = currentRelation.getVboxTo();

                    line.setStartX(source.getLayoutX() + source.getWidth()/2 + source.getTranslateX());
                    line.setStartY(source.getLayoutY() + source.getHeight()/2 + source.getTranslateY());
                    line.setEndX(target.getLayoutX() + target.getWidth()/2 + target.getTranslateX());
                    line.setEndY(target.getLayoutY() + target.getHeight()/2 + target.getTranslateY());
                    line.toBack();

                    mainPane.getChildren().add(line);
                }

                ((VBox)(t.getSource())).setTranslateX(newTranslateX);
                ((VBox)(t.getSource())).setTranslateY(newTranslateY);
            }
        };

    EventHandler<MouseEvent> vboxOnMouseClickedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent t) {
                    if(t.getButton().equals(MouseButton.PRIMARY)){
                        if(t.getClickCount() == 2){
                            VBox currentVBox = ((VBox)(t.getSource()));
                            Label currVBoxLabel = (Label)(currentVBox.getChildren().get(0));
                            UMLClass currClass = classDiagram.findClass(currVBoxLabel.getText());
//                            System.out.println(currClass.getName());

                            Parent root;
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/sceneEditUMLClass.fxml"));

                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            SceneEditUMLClassController sceneEditUMLClassController = loader.getController();
                            sceneEditUMLClassController.setUMLClass(currClass);
                            sceneEditUMLClassController.setVBox(currentVBox);

                            Scene scene = new Scene(root);
                            stage.setTitle("Edit UMLClass");
                            stage.setMinWidth(300);
                            stage.setMinHeight(300);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                }
            };

}
