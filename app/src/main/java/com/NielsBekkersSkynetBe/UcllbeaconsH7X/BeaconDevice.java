package com.NielsBekkersSkynetBe.UcllbeaconsH7X;

/**
 * Created by r0579260 on 18-11-2016.
 */

public class BeaconDevice {
    private String KEY_UUID;
    private String KEY_NAME;
    private String KEY_MAJOR;
    private String KEY_MINOR;
    private String KEY_LOCATION_TITLE;
    private String KEY_LOCATION_DESCRIPTION;

    public BeaconDevice(){

    }

    public void setMajor(String major){this.KEY_MAJOR=major;}

    public void setMinor(String KEY_MINOR) {
        this.KEY_MINOR = KEY_MINOR;
    }

    public void setName(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public void setUUID(String KEY_UUID) {
        this.KEY_UUID = KEY_UUID;
    }

    public void setLocationTitle(String KEY_LOCATION_TITLE) {
        this.KEY_LOCATION_TITLE = KEY_LOCATION_TITLE;
    }

    public void setLocationDescription(String KEY_LOCATION_DESCRIPTION) {
        this.KEY_LOCATION_DESCRIPTION = KEY_LOCATION_DESCRIPTION;
    }

    public String getMajor(){
        return KEY_MAJOR;
    }

    public String getUUID(){
        return KEY_UUID;
    }

    public String getName(){
        return KEY_NAME;
    }

    public String getMinor(){
        return KEY_MINOR;
    }

    public String getKeyLocationTitle(){
        return KEY_LOCATION_TITLE;
    }

    public String getLocationDescription(){
        return KEY_LOCATION_DESCRIPTION;
    }
}
