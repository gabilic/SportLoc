package hr.foi.air.sportloc.view.util;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.Map;

public final class IntentManager {
    private IntentManager() {
    }

    public static void startActivity(Context currentContext, Class<?> destination) {
        Intent intent = new Intent(currentContext, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.startActivity(intent);
    }

    public static void startActivity(Context currentContext, Class<?> destination, String extraName, Object extraObject) {
        Intent intent = new Intent(currentContext, destination);
        putExtra(intent, extraName, extraObject);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.startActivity(intent);
    }

    public static void startActivity(Context currentContext, Class<?> destination, Map<String, Object> extraMap) {
        Intent intent = new Intent(currentContext, destination);

        for (Map.Entry<String, Object> entry : extraMap.entrySet()){
            putExtra(intent, entry.getKey(), entry.getValue());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.startActivity(intent);
    }

    private static void putExtra(Intent intent, String key, Object value) {
        if (value instanceof Boolean) {
            intent.putExtra(key, (Boolean) value);
        } else if(value instanceof String) {
            intent.putExtra(key, (String) value);
        } else if (value instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) value);
        } else if (value instanceof Serializable) {
            intent.putExtra(key, (Serializable) value);
        } else {
            Log.w(IntentManager.class.getName(), "Intent extra was not set for all requested values");
        }
    }

}
