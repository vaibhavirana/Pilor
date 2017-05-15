package com.barodacoder.pilor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmailNotNull(String email) {

        if (email.trim().length() == 0)
            return false;

        return true;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
        //return true;
    }

    public static boolean validatePasswordNotNull(String password) {

        if (password.trim().length() == 0)
            return false;

        return true;
    }

    public static boolean validateBirthdateNotNull(String birthdate) {

        if (birthdate.trim().length() == 0)
            return false;

        return true;
    }

    public static boolean validateGenderNotNull(String gender) {

        if (gender.trim().length() == 0)
            return false;

        return true;
    }

    public static boolean validateNameNotNull(String name) {


        if (name.trim().length() == 0)
            return false;
        return true;
    }

    public static boolean validateAddressNotNull(String address) {

        if (address.trim().length() == 0)
            return false;

        return true;
    }

	/*public static LatLng addressToLocation(Context context, String address)
	{
        try
        {
	        List<Address> foundGeocode = null;
	        foundGeocode = new Geocoder(context).getFromLocationName(address, 1);
	        return new LatLng(foundGeocode.get(0).getLatitude(), foundGeocode.get(0).getLongitude());
        }
        catch (Exception e)
        {
        	return new LatLng(0, 0);
        }
	}*/

    public static boolean validateValueIsNotZero(String editText){

        if(editText == null) return false;
        if(editText.toString().trim().length() == 0) return false;
        if(!Validator.isNumeric(editText.toString().trim())) return false;
        int val = Integer.parseInt(editText);
        if(val <= 0) return false;

        return true;
    }

    public static boolean isNumeric(String str) {

        if(str == null) return false;
        if(str.trim().isEmpty()) return false;

        try{
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static boolean validateAddress(String address) {

        return false;
    }
}
