package hr.foi.air.sportloc.view.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Toast;

public final class MessageSender {
    private MessageSender() {
    }

    private static void sendText(Context currentContext, String message, int color) {
        Toast toast = Toast.makeText(currentContext, message, Toast.LENGTH_LONG);
        toast.getView().getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        toast.show();
    }

    public static void sendMessage(Context currentContext, String message) {
        sendText(currentContext, message, Color.GREEN);
    }

    public static void sendError(Context currentContext, String message) {
        sendText(currentContext, message, Color.RED);
    }
}
