/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        DataParser.java
 * Description: Parse JSON data and store them to app datastructures.
 */

package ija.project.uml;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

/**
 * Parse JSON data.
 */
public class DataParser {
    /**
     * Parse input data file and save json object.
     * 
     * @param f input data file.
     */
    private static JSONObject parse (File f) throws IOException, JSONException {
        String jsonData = Files.readString(f.toPath());
        return new JSONObject(jsonData);
    }

    /**
     * Parse class diagram
     *
     * @param dataFile input file
     * @return created class diagram or null.
     */
    public static ClassDiagram parseClassDiagram (File dataFile) {
        JSONObject jsonObject;
        ClassDiagram classDiagram;

        try {
            jsonObject = DataParser.parse(dataFile);

            // Create class diagram
            String CDType = jsonObject.getString("type");

            if (!CDType.equals("ClassDiagram")) {
                throw new JSONException("wrong diagram type");
            }
            
            String CDName = jsonObject.getString("name");
            classDiagram = new ClassDiagram(CDName);

            // Create classes and add to the diagram
            JSONArray classList = jsonObject.getJSONArray("classes");

            for (int i = 0; i < classList.length(); i++) {
                JSONObject CData = classList.getJSONObject(i);

                // Create class
                String CName = CData.getString("name");
                UMLClass umlClass = new UMLClass(CName);

                // Store class position
                double CPositionX = CData.getDouble("positionX");
                double CPositionY = CData.getDouble("positionY");
                umlClass.setPositionX(CPositionX);
                umlClass.setPositionY(CPositionY);

                // Add attributes and methods
                JSONArray itemList = CData.getJSONArray("items");

                for (int j = 0; j < itemList.length(); j++) {
                    JSONObject item = itemList.getJSONObject(j);
                    String itemType = item.getString("item_type");

                    if (Objects.equals(itemType, "Attribute")) {
                        String AVisibility = item.getString("visibility");
                        String AName = item.getString("name");
                        String AType = item.getString("type");
                        umlClass.addAttribute(new Attribute(AName, AType, AVisibility));
                    } else if (Objects.equals(itemType, "Method")) {
                        String MVisibility = item.getString("visibility");
                        String MName = item.getString("name");
                        String MType = item.getString("type");
                        Method classMethod = new Method(MName, MType, MVisibility);
                        JSONArray methodParams = item.getJSONArray("params");

                        // Add method parameters
                        for (int k = 0; k < methodParams.length(); k++) {
                            JSONObject methodParam = methodParams.getJSONObject(k);
                            String PName = methodParam.getString("name");
                            String PType = methodParam.getString("type");
                            classMethod.addAttribute(new Attribute(PName, PType));
                        }

                        umlClass.addMethod(classMethod);
                    }
                }

                classDiagram.addClass(umlClass);
            }

            // Create relations and add to the diagram

            JSONArray relationList = jsonObject.getJSONArray("relations");

            for (int i = 0; i < relationList.length(); i++) {
                JSONObject relation = relationList.getJSONObject(i);
                String RName = relation.getString("name");
                String RType = relation.getString("type");
                String RSource = relation.getString("source");
                String RTarget = relation.getString("target");
                String RCardFrom = relation.getString("cardinality_from");
                String RCardTo = relation.getString("cardinality_to");
                classDiagram.addRelation(new UMLRelation(
                    RName,
                    RType,
                    RSource,
                    RTarget,
                    RCardFrom,
                    RCardTo
                ));
            }
        } catch (IOException ioException) {
            System.out.println("Error reading input file.");
            return null;
        } catch (JSONException jsonException) {
            System.out.println(jsonException.getMessage());
            return null;
        }

        return classDiagram;
    }

    /**
     * Parse sequence diagram.
     *
     * @param dataFile input file
     * @return created sequence diagram or null.
     */
    public static SequenceDiagram parseSequenceDiagram (File dataFile) {
        JSONObject jsonObject;
        SequenceDiagram sequenceDiagram;

        try {
            jsonObject = DataParser.parse(dataFile);

            // Create sequence diagram
            String SDType = jsonObject.getString("type");

            if (!SDType.equals("SequenceDiagram")) {
                throw new JSONException("wrong diagram type");
            }

            String SDName = jsonObject.getString("name");
            sequenceDiagram = new SequenceDiagram(SDName);

            // Add objects to the diagram
            JSONArray objectList = jsonObject.getJSONArray("objects");

            for (int i = 0; i < objectList.length(); i++) {
                sequenceDiagram.addSequenceObject(objectList.getString(i));
            }

            // Add messages to the diagram
            JSONArray messageList = jsonObject.getJSONArray("messages");

            for (int i = 0; i < messageList.length(); i++) {
                JSONObject SDMessage = messageList.getJSONObject(i);
                String MName = SDMessage.getString("name");
                String MType = SDMessage.getString("message_type");
                String MSource = SDMessage.getString("source");
                String MTarget = SDMessage.getString("target");
                sequenceDiagram.addMessage(new Message(MName, MType, MSource, MTarget));
            }
        } catch (IOException ioException) {
            System.out.println("Error reading input file.");
            return null;
        } catch (JSONException jsonException) {
            System.out.println(jsonException.getMessage());
            return null;
        }

        return sequenceDiagram;
    }

    /**
     * Convert and save class diagram to JSON file.
     *
     * @param classDiagram class diagram.
     * @param stage stage to open file chooser.
     */
    public static void saveClassDiagram(ClassDiagram classDiagram, Stage stage) throws IOException {
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
}
