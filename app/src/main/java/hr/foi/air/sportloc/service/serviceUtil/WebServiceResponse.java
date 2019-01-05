package hr.foi.air.sportloc.service.serviceUtil;

import com.google.gson.annotations.SerializedName;

public class WebServiceResponse {
    
    @SerializedName("success")
    private boolean successful;
    @SerializedName("message")
    private String message;

    public WebServiceResponse(boolean success, String message) {
        this.successful = success;
        this.message = message;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}