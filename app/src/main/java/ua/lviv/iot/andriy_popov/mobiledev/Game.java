package ua.lviv.iot.andriy_popov.mobiledev;

import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("name")
    private String name;
    @SerializedName("browser")
    private String browser;
    @SerializedName("date_register")
    private String dateRegister;
    @SerializedName("date_start_play")
    private String dateStartPlay;
    @SerializedName("status")
    private String status;
    @SerializedName("creator")
    private String creator;
    @SerializedName("image")
    private String image;

    public Game(String name, String browser, String dateRegister, String dateStartPlay, String status, String creator, String image) {
        this.name = name;
        this.browser = browser;
        this.dateRegister = dateRegister;
        this.dateStartPlay = dateStartPlay;
        this.status = status;
        this.creator = creator;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getDateStartPlay() {
        return dateStartPlay;
    }

    public void setDateStartPlay(String dateStartPlay) {
        this.dateStartPlay = dateStartPlay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

