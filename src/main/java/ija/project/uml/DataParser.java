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
     * @throws IOException
     */
    private static JSONObject parse (File f) throws IOException, JSONException {
        String jsonData = Files.readString(f.toPath());
        return new JSONObject(jsonData);
    }

    public static ClassDiagram parseClassDiagram (File dataFile) {
        JSONObject jsonObject;
        ClassDiagram classDiagram = null;

        try {
            jsonObject = DataParser.parse(dataFile);
            System.out.println(jsonObject);

            // Create class diagram
            String CDType = jsonObject.getString("type");
            
            if (Objects.equals(CDType, "ClassDiagram")) {
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

                // Add attributes and methods
                JSONArray itemList = CData.getJSONArray("items");

                for (int j = 0; j < itemList.length(); j++) {
                    JSONObject item = itemList.getJSONObject(i);
                    String itemType = item.getString("name");

                    if (Objects.equals(itemType, "Attribute")) {

                    } else if (Objects.equals(itemType, "Method")) {

                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error reading input file.");
        } catch (JSONException jsonException) {
            System.out.println("Wrong json format.");
        }

        return classDiagram;
    }
}
