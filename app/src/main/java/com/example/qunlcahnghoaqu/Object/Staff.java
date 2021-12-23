package com.example.qunlcahnghoaqu.Object;

public class Staff {
    private String nameStaff;
    private String idStaff;
    private int ageStaff;
    private int phoneStaff;
    private String userStaff;
    private String passwordStaff;
    private byte[] imgStaff;

    public Staff(String idStaff ,String nameStaff,int ageStaff , int phoneStaff , String userStaff , String passwordStaff,byte[] imgStaff) {
        this.idStaff = idStaff;
        this.nameStaff = nameStaff;
        this.ageStaff = ageStaff;
        this.phoneStaff = phoneStaff;
        this.userStaff = userStaff;
        this.passwordStaff = passwordStaff;
        this.imgStaff = imgStaff;
    }
    public byte[] getImgStaff() {
        return imgStaff;
    }

    public void setImgStaff(byte[] imgStaff) {
        this.imgStaff = imgStaff;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public int getAgeStaff() {
        return ageStaff;
    }

    public void setAgeStaff(int ageStaff) {
        this.ageStaff = ageStaff;
    }

    public int getPhoneStaff() {
        return phoneStaff;
    }

    public void setPhoneStaff(int phoneStaff) {
        this.phoneStaff = phoneStaff;
    }

    public String getUserStaff() {
        return userStaff;
    }

    public void setUserStaff(String userStaff) {
        this.userStaff = userStaff;
    }

    public String getPasswordStaff() {
        return passwordStaff;
    }

    public void setPasswordStaff(String passwordStaff) {
        this.passwordStaff = passwordStaff;
    }
}
