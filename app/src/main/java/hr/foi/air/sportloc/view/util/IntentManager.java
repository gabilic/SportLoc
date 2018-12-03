package hr.foi.air.sportloc.view.util;

import android.content.Context;
import android.content.Intent;

public final class IntentManager {
    private IntentManager() {
    }

    public static void startActivity(Context currentContext, Class<?> destination) {
        Intent intent = new Intent(currentContext, destination);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentContext.startActivity(intent);
    }
}
