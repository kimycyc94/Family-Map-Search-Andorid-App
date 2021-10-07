package Serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Serializer {
    public static Object deserialize (String fileBody, Object obj) {
        Object object = null;
        try {
            Gson gson = new Gson();
            object = gson.fromJson(fileBody, obj.getClass());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String serialize (Object obj) {
        String returnVal = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            returnVal = gson.toJson(obj);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return returnVal;
    }
}
