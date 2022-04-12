package ija.project.uml;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Parse JSON data.
 */
public class DataParser {
    /**
     * JSON Object.
     */
    private JSONObject jsonObj;

    /**
     * Creates an empty JSON object.
     */
    public DataParser () {
        this.jsonObj = new JSONObject();
    }

    /**
     * Parse input data file and save json object.
     * 
     * @param dataFile input data file.
     * @throws IOException
     */
    public void parse (File dataFile) throws IOException {
        String jsonData = Files.readString(dataFile.toPath());
        System.out.println(jsonData);
        this.jsonObj = new JSONObject(jsonData);
    }
}
