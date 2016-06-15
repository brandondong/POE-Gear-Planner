package Util;

import Model.BuildsModel;

import java.io.*;

/**
 * Created by Brandon on 2016-06-14.
 *
 * Manages the serialization of the {@link Model.BuildsModel}
 */
public class ModelSerializationManager {

    private static final String FILE_NAME = "model.txt";

    public static void serializeModel(BuildsModel model) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(model);
        } catch (IOException e) {
            Logger.addError("Failed to save model", e);
        }
    }

    public static BuildsModel deserializeModel() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             ObjectInputStream ois = new ObjectInputStream(fis)){
            return (BuildsModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new BuildsModel();
        }
    }
}
