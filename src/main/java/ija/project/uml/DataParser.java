package ija.project.uml;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

        } catch (IOException ioException) {
            System.out.println("Error reading input file.");
        } catch (JSONException jsonException) {
            System.out.println("Wrong json format.");
        }

        return classDiagram;
    }
}
