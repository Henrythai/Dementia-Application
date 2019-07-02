package me.trung.projectdemotwo.Model;

public class User {
    private int Id;
    private String Email;
    private String Password;
    private String Location;
    private int Age;
    private String Gender;
    private int isDementia;
    private byte[] imageName;
    private String education_level;

    public User() {

    }


    public User(String email, String password, String location, int age, String gender, int isDementia, byte[] imageName, String education_level) {
        Email = email;
        Password = password;
        Location = location;
        Age = age;
        Gender = gender;
        this.isDementia = isDementia;
        this.imageName = imageName;
        this.education_level = education_level;
    }

    public User(String location, int age, String gender, int isDementia, byte[] imageName, String education_level) {
        Location = location;
        Age = age;
        Gender = gender;
        this.isDementia = isDementia;
        this.imageName = imageName;
        this.education_level = education_level;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getIsDementia() {
        return isDementia;
    }

    public void setIsDementia(int isDementia) {
        this.isDementia = isDementia;
    }

    public byte[] getImageName() {
        return imageName;
    }

    public void setImageName(byte[] imageName) {
        this.imageName = imageName;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }

}
