package hr.foi.air.sportloc.service.model;

import java.io.Serializable;

import hr.foi.air.sportloc.view.util.DateTimeHelper;

public class UserModel implements Serializable {
    public static final long serialVersionUID = 1L;

    private int userId;
    private int upvote;
    private int downvote;
    private String karma;
    private boolean gender;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String description;
    private String dob;
    private String age;

    //--------novo moje
    private String genderString;
    //--------

    public UserModel() {
    }

    private String resolveNull(String string) {
        if (string == null) {
            string = "";
        }
        return string;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return resolveNull(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return resolveNull(surname);
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return resolveNull(username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return resolveNull(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return resolveNull(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        return gender?"Muško":"Žensko";
    }

    public void setGenderString(String genderString) {
        this.genderString = genderString;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDescription() {
        return resolveNull(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUpvote() {
        return upvote;
    }

    public void setUpvote(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvote() {
        return downvote;
    }

    public void setDownvote(int downvote) {
        this.downvote = downvote;
    }

    public String getKarma() {
        return String.valueOf(upvote - downvote);
    }

    public void setKarma(String karma) {
        this.karma = karma;
    }

    public String getAge() {

        return dob!=null && !dob.isEmpty()? DateTimeHelper.calculateAge(dob):"";

    }

    public void setAge(String age) {
        this.age = age;
    }

}
