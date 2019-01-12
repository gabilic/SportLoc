package hr.foi.air.sportloc.service.model;

public class ActiveUserModel {
    private UserModel activeUser;
    private static ActiveUserModel instance;

    private ActiveUserModel() {
    }

    public UserModel getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(UserModel activeUser) {
        this.activeUser = activeUser;
    }

    public static synchronized ActiveUserModel getInstance() {
        if (instance == null) {
            instance = new ActiveUserModel();
        }
        return instance;
    }
}
