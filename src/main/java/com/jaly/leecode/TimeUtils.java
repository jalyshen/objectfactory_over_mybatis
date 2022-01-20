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
public class TimeUtils {
    public static String PM = "PM";
    public static String AM = "AM";
    public static int ONE_MINUTE = 60;
    public static int ONE_HOUR = 60 * ONE_MINUTE; // all seconds in one hour
    public static int HALF_DAY_SECONDS = 12 * ONE_HOUR; // all seconds in half day
    public static int ONE_DAY_SECONDS = 24 * ONE_HOUR; // all seconds in one day

    /**
     *
     * @param time  12-hour time string with the format "[H]H:MM {AM|PM}"
     * @param increment the number of minutes to add to the time of day represented
     *                  by the first argument
     * @return the new time which combine the 2 argument
     */
    public static String addSeconds(String time, int increment) {
        checkParams(time, increment);
        // 0. if the increment meets one day, the time is same with input
        if (increment < ONE_MINUTE) return time;

        // 1. if the increment meets one day, the time is same with input
        //------------------------------------------------------------
        if (increment % ONE_DAY_SECONDS == 0) return time;

        int length = time.length();
        String timeQuantum = time.substring(length-2);
        // take out the digit part of time
        // 9:13 AM -> pick out 9:13
        String timeDigit = time.split(" ")[0];
        // pick out hour. ex: 9 -> 9; 09 -> 9
        int inputHour = convertIntegerFromString(timeDigit.split(":")[0]);
        // pick out second. ex: 03 -> 3; 13 -> 13
        int inputMinute = convertIntegerFromString(timeDigit.split(":")[1]);

        int totalSeconds = 0;
        int inputSeconds = 0;
        increment = increment % ONE_DAY_SECONDS; // 去掉"天"级别的秒数
        if (timeQuantum.toUpperCase().equals(AM)) {
            inputSeconds =
                    inputHour * ONE_HOUR +
                            inputMinute * ONE_MINUTE;
        } else {
            if (inputHour == 12) {
                inputSeconds =
                        inputHour * ONE_HOUR +
                                inputMinute * ONE_MINUTE;
            }else {
                inputSeconds =
                        (12 + inputHour) * ONE_HOUR +
                                inputMinute * ONE_MINUTE;
            }
        }
        totalSeconds = inputSeconds + increment;

        // 加起来已经"又"超过一天了
        if (totalSeconds > ONE_DAY_SECONDS) {
            totalSeconds = totalSeconds % ONE_DAY_SECONDS;
        }

        if (totalSeconds > HALF_DAY_SECONDS) { // 超半天
            timeQuantum = PM;
        } else {
            timeQuantum = AM;
        }
        int hours = totalSeconds / ONE_HOUR;
        totalSeconds = totalSeconds % ONE_HOUR;
        int minutes = totalSeconds / ONE_MINUTE;

        String minutesStr = minutes < 10 ? "0" + minutes : "" + minutes;
        String hoursStr = hours > 12 ? "" + (hours - 12) : "" + hours;
        return hoursStr + ":" + minutesStr + " " + timeQuantum;

    }

    private static void checkParams(String time, int increment) {
        // Check increment argument
        if (increment > Integer.MAX_VALUE)
            throw new ExceptionInInitializerError("the increment value error: "
                    + increment + " overflows Integer.MAX_VALUE");
        if (increment < 0)
            throw new ExceptionInInitializerError("the increment value error: "
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
        System.out.println(" 9:13 AM + ONE_DAY  => " + addSeconds("9:13 AM", ONE_DAY_SECONDS));
        System.out.println("09:13 AM + ONE_DAY  => " + addSeconds("09:13 AM", ONE_DAY_SECONDS));
        System.out.println("12:13 PM + ONE_DAY  => " + addSeconds("12:13 PM", ONE_DAY_SECONDS));
        System.out.println("09:13 AM + HALF_DAY => " + addSeconds("9:13 AM", HALF_DAY_SECONDS));
        System.out.println("12:13 PM + HALF_DAY => " + addSeconds("12:13 PM", HALF_DAY_SECONDS));
        System.out.println("09:13 AM + ONE_HOUR => " + addSeconds("9:13 AM", ONE_HOUR));
        System.out.println("11:13 AM + ONE_HOUR => " + addSeconds("11:13 AM", ONE_HOUR));
        System.out.println("12:13 PM + ONE_HOUR => " + addSeconds("12:13 PM", ONE_HOUR));

        System.out.println("12:13 PM + OND_DAY + Hours + 349s => " + addSeconds("12:13 PM", ONE_DAY_SECONDS + 349));

        System.out.println("12:13 PM + 56 seconds => " + addSeconds("12:13 PM", 56));
        System.out.println("12:13 PM + 60 seconds => " + addSeconds("12:13 PM", 60));
        System.out.println("11:59 AM + 60 seconds => " + addSeconds("11:59 AM", 60));
        System.out.println("11:59 AM + 62 seconds => " + addSeconds("11:59 AM", 62));
        System.out.println("11:59 PM + 62 seconds => " + addSeconds("11:59 PM", 62));

        System.out.println("11:59 PM + 62 seconds => " + addSeconds("11:59 PMddd", 62));
    }

}
