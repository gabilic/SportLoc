package hr.foi.air.sportloc.view.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class InternalStorageManager {

    private InternalStorageManager() {
    }

    public static void writeObject(Context context, Object object) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(Constants.USER_DATA_STORAGE, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public static Object readObject(Context context) {
        try {
            FileInputStream fileInputStream = context.openFileInput(Constants.USER_DATA_STORAGE);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public static void clearObject(Context context) {
        context.deleteFile(Constants.USER_DATA_STORAGE);
    }
}
