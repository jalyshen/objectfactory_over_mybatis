package com.jaly.leecode;

/**
 * <p>
 * Without using any built-in date or time functions,
 * write a function or method that accepts two mandatory arguments:
 * the first argument is a 12-hour time string with the format "[H]H:MM {AM|PM}",
 * and the second argument is a (signed) integer.
 *
 * The second argument is the number of minutes to add to the time of day represented
 * by the first argument. The return value should be a string of the same format as
 * the first argument. For example, AddMinutes("9:13 AM", 200) would return "12:33 PM".
 *
 * The exercise isn't meant to be too hard or take very long;
 * we just want to see how you code. Use any mainstream language you want,
 * though Java and Scala are particularly relevant to us.
 *
 * Please include any test cases that you write.
 * </p>
 */
public class TimeUtils2 {
    public static String PM = "PM";
    public static String AM = "AM";
    public static int ONE_HOUR_MINUTES = 60 ; // all minutes in one hour
    public static int HALF_DAY_MINUTES = 12 * ONE_HOUR_MINUTES; // all minutes in half day
    public static int ONE_DAY_MINUTES = 2 * HALF_DAY_MINUTES; // all minutes in one day

    /**
     *
     * @param time  12-hour time string with the format "[H]H:MM {AM|PM}"
     * @param increment the number of minutes to add to the time of day represented
     *                  by the first argument
     * @return the new time which combine the 2 argument
     */
    public static String addMinutes(String time, int increment) {
        checkParams(time, increment);

        // 1. if the increment meets one day, the time is same with input
        //------------------------------------------------------------
        if (increment % ONE_DAY_MINUTES == 0) return time;

        int length = time.length();
        String timeQuantum = time.substring(length-2);
        // take out the digit part of time
        // 9:13 AM -> pick out 9:13
        String timeDigit = time.split(" ")[0];
        // pick out hour. ex: 9 -> 9; 09 -> 9
        int inputHour = convertIntegerFromString(timeDigit.split(":")[0]);
        // pick out second. ex: 03 -> 3; 13 -> 13
        int inputMinute = convertIntegerFromString(timeDigit.split(":")[1]);

        int totalMinutes = 0;
        int inputMinutes = 0;
        increment = increment % ONE_DAY_MINUTES; // 去掉"天"级别的秒数
        if (timeQuantum.toUpperCase().equals(AM)) {
            inputMinutes = inputHour * ONE_HOUR_MINUTES + inputMinute;
        } else {
            if (inputHour == 12) {
                inputMinutes = inputHour * ONE_HOUR_MINUTES + inputMinute;
            }else {
                inputMinutes = (12 + inputHour) * ONE_HOUR_MINUTES + inputMinute;
            }
        }
        totalMinutes = inputMinutes + increment;

        // 加起来已经"又"超过一天了
        if (totalMinutes > ONE_DAY_MINUTES) {
            totalMinutes = totalMinutes % ONE_DAY_MINUTES;
        }

        if (totalMinutes > HALF_DAY_MINUTES) { // 超半天
            timeQuantum = PM;
        } else {
            timeQuantum = AM;
        }
        int hours = totalMinutes / ONE_HOUR_MINUTES;
        int minutes = totalMinutes % ONE_HOUR_MINUTES;
        //int minutes = totalMinutes / ONE_HOUR_MINUTES;

        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        String hoursStr = hours > 12 ? "" + (hours - 12) : "" + hours;
        return hoursStr + ":" + minutesStr + " " + timeQuantum;

    }

    private static void checkParams(String time, int increment) {
        // Check increment argument
        if (increment > Integer.MAX_VALUE)
            throw new ExceptionInInitializerError("the increment minutes value error: "
                                                  + increment + " overflows Integer.MAX_VALUE");
        if (increment < 0)
            throw new ExceptionInInitializerError("the increment minutes value error: "
                                                  + increment + " must be greater than 0 ");

        if (time == null) throw new ExceptionInInitializerError("the time value error: time '"
                                                                + time + "' is Null. Please input a time and try again... ");
        // trim the tail space
        time = time.trim();
        int length = time.length();
        if (length > 8) throw new ExceptionInInitializerError("the time value error: time '"
                                                              + time + "' format is wrong. Please input following this format '[H]H:MM {AM|PM}' ");

        // check the time quantum, PM or AM or others
        String timeQuantum = time.substring(length-2);
        if (!timeQuantum.toUpperCase().equals(PM) && !timeQuantum.toUpperCase().equals(AM))
            throw new ExceptionInInitializerError("the time value error: time '"
                                                  + time + "' format is wrong. Please input following this format '[H]H:MM {AM|PM}' ");
    }

    /**
     * @param digitString
     * @return
     */
    private static int convertIntegerFromString(String digitString) {
        String result = digitString;
        if (digitString.length() == 2) {
            if (digitString.substring(0,1).equals("0")) {
                result = digitString.substring(1);
            }
        }
        return Integer.valueOf(result);
    }

    public static void main(String[] args) {
        System.out.println(" 9:13 AM + ONE_DAY  => " + addMinutes("9:13 AM", ONE_DAY_MINUTES));
        System.out.println("09:13 AM + ONE_DAY  => " + addMinutes("09:13 AM", ONE_DAY_MINUTES));
        System.out.println("12:13 PM + ONE_DAY  => " + addMinutes("12:13 PM", ONE_DAY_MINUTES));
        System.out.println("09:13 AM + HALF_DAY => " + addMinutes("9:13 AM", HALF_DAY_MINUTES));
        System.out.println("12:13 PM + HALF_DAY => " + addMinutes("12:13 PM", HALF_DAY_MINUTES));
        System.out.println("08:13 AM + ONE_HOUR => " + addMinutes("8:13 AM", ONE_HOUR_MINUTES));
        System.out.println("09:13 AM + ONE_HOUR => " + addMinutes("9:13 AM", ONE_HOUR_MINUTES));
        System.out.println("11:13 AM + ONE_HOUR => " + addMinutes("11:13 AM", ONE_HOUR_MINUTES));
        System.out.println("12:13 PM + ONE_HOUR => " + addMinutes("12:13 PM", ONE_HOUR_MINUTES));

        System.out.println("12:13 PM + OND_DAY + 34 minutes => " + addMinutes("12:13 PM", ONE_DAY_MINUTES + 34));

        System.out.println("12:13 PM + 56 minutes => " + addMinutes("12:13 PM", 56));
        System.out.println("12:13 PM + 1 minute => " + addMinutes("12:13 PM", 1));
        System.out.println("11:59 AM + 1 minute => " + addMinutes("11:59 AM", 1));
        System.out.println("11:59 AM + 2 minutes => " + addMinutes("11:59 AM", 2));
        System.out.println("11:59 PM + 2 minutes => " + addMinutes("11:59 PM", 2));

        System.out.println("11:59 PM + 2 minutes => " + addMinutes("11:59 PMddd", 2));
    }

}
