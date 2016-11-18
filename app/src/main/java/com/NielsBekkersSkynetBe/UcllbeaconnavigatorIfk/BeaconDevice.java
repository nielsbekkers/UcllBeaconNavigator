package com.NielsBekkersSkynetBe.UcllbeaconnavigatorIfk;

/**
 * Created by r0579260 on 18-11-2016.
 */

public class BeaconDevice {
    private static final String KEY_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D ";
    private static final String KEY_NAME = "Icy Marshmallow";
    private static final Integer KEY_MAJOR = 47541;
    private static final Integer KEY_MINOR = 3683;
    private static final String KEY_LOCATION_TITLE = "K103";
    private static final String KEY_LOCATION_DESCRIPTION = "Lokaal van K103 je bent er.";

    public BeaconDevice(){

    }

    public static int getMajor(){
        return KEY_MAJOR;
    }

    public static String getUUID(){
        return KEY_UUID;
    }

    public static String getName(){
        return KEY_NAME;
    }

    public static int getMinor(){
        return KEY_MINOR;
    }

    public static String getKeyLocationTitle(){
        return KEY_LOCATION_TITLE;
    }

    public static String getLocationDescription(){
        return KEY_LOCATION_DESCRIPTION;
    }
}
