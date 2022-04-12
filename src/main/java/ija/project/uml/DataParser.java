/**
 * Project:     IJA, UML Editor
 * Authors:     Lukáš Vincenc <xvince01@vut.cz>
 *              Evgeny Torbin <xtorbi00@vut.cz>
 * File:        DataParser.java
 * Description:
 */

package ija.project.uml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
        System.out.println(jsonData);
        System.out.println(new JSONObject(jsonData));
        return new JSONObject(jsonData);
    }

    public static ClassDiagram parseClassDiagram (File dataFile) {
        JSONObject jsonObject;
        ClassDiagram classDiagram;

        try {
            jsonObject = DataParser.parse(dataFile);
//            System.out.println(jsonObject);

            // Create class diagram
//            String CDType = jsonObject.getString("type");
//
//            if (Objects.equals(CDType, "ClassDiagram")) {
//                throw new JSONException("wrong diagram type");
//            }
            
//            String CDName = jsonObject.getString("name");
            classDiagram = new ClassDiagram("testName");

            // Create classes and add to the diagram
            JSONArray classList = jsonObject.getJSONArray("classes");

            for (int i = 0; i < classList.length(); i++) {
                JSONObject CData = classList.getJSONObject(i);

                // Create class
                String CName = CData.getString("name");
                UMLClass umlClass = new UMLClass(CName);

                // Add attributes and methods
                JSONArray itemList = CData.getJSONArray("items");

                for (int j = 0; j < itemList.length(); j++) {
                    JSONObject item = itemList.getJSONObject(j);
                    String itemType = item.getString("item_type");

                    if (Objects.equals(itemType, "Attribute")) {
                        String AName = item.getString("name");
                        String AType = item.getString("type");
                        umlClass.addAttribute(new Attribute(AName, AType));
                    } else if (Objects.equals(itemType, "Method")) {
                        String MName = item.getString("name");
                        String MType = item.getString("type");
                        String MReturnType = item.getString("return_type");
                        Method classMethod = new Method(MName, MType, MReturnType);
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
                String RDescription = relation.getString("description");
                String RCardFrom = relation.getString("cardinality_from");
                String RCardTo = relation.getString("cardinality_to");
                classDiagram.addRelation(new UMLRelation(
                    RName,
                    RType,
                    RSource,
                    RTarget,
                    RDescription,
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
}
