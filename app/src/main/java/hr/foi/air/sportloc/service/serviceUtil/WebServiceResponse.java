package hr.foi.air.sportloc.service.serviceUtil;

import com.google.gson.annotations.SerializedName;

public class WebServiceResponse {
    
    @SerializedName("success")
    private boolean successful;

    public WebServiceResponse(boolean success) {
        this.successful = success;
    }

    public boolean isSuccessful() {
        return successful;
    }
}