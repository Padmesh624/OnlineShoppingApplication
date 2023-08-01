package com.hyperloop_passenger_booking;

public class InputValidationUtil {

    public void validateCommand(String[] input) throws Exception {
        if(input[0].equals("INIT") && input.length==3)
        {
            if(isNotNumeric(input[1])  ||  isNotAlpha(input[2]))
            {
                throw new Exception("Invalid argument :: Check the given values");
            }
        }
        else if(input[0].equals("ADD_PASSENGER") && input.length==2)
        {
            if(isNotNumeric(input[1]))
            {
                throw new Exception("Invalid argument :: Provide number");
            }
        }
        else if(input[0].equals("START_POD") && input.length==2)
        {
            if(isNotNumeric(input[1]))
            {
                throw new Exception("Invalid argument :: Provide number");
            }
        }

    }

    public void validateRoute(String[] route) throws Exception {
        if(isNotAlpha(route[0]) || isNotAlpha(route[1]) || isNotNumeric(route[2]))
        {
            throw new Exception("Invalid format. Enter a valid Route");
        }
    }

    public void validatePassenger(String[] passenger) throws Exception {
        if(isNotNumeric(passenger[1]) || isNotAlpha(passenger[2]) )
        {
            throw new Exception("Invalid format. Enter a valid details");
        }
    }
    public boolean isNotAlpha(String a) {
        return (int)a.charAt(0)<65 || (int)a.charAt(0)>90;
    }
    public boolean isNotNumeric(String n) {
        return (int)n.charAt(0)<48 || (int)n.charAt(0)>57;

    }
    public static  void main(String[]args)
    {

    }
}
