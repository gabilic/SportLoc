package hr.foi.air.sportloc.service.model;

public class PrimitiveWrapperModel {
    private boolean success;
    private int userId;

    public PrimitiveWrapperModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
