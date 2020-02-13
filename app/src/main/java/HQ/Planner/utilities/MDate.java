package HQ.Planner.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import HQ.Planner.model.Event;

public class MDate {

    private final static SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);

    private final static SimpleDateFormat dayFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);


    private final static SimpleDateFormat monthYearFormat =
            new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);

    public static String toDate(Date date) {
        String dd = simpleDateFormat.format(date);
        return dd;
    }

    public static Date sToDate(String s) {
        try {
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date toDate(String s) {

        String[] all = s.split(" ");
        String[] date = all[0].split("/");
        String[] time = all[1].split(":");
        Calendar c = Calendar.getInstance();

        try {
            int factor = 0;
            if (all[2].toUpperCase().equals("PM")) {
                factor = 12;
            }
            c.set(
                    toInt(date[2]),
                    toInt(date[1]) - 1,
                    toInt(date[0]),
                    toInt(time[0]) + factor,
                    toInt(time[1]),
                    toInt(time[2])
            );
        } catch (Exception e) {
            System.out.println("---------------------------------Error when transfer to Date.-----------------------------------------");
            e.printStackTrace();
        }
        return c.getTime();
    }

    private static int toInt(String s) {
        try {
            int k = Integer.parseInt(s);
            return k;
        } catch (Exception e) {
            System.out.println("Not An Integer to parse.");
        }
        return -1;
    }

    public static Date getDayDateFromYearMonthDay(
            int year, int month, int day) throws ParseException {
        String date = year + "/" + month + "/" + day;
        return dayFormat.parse(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {

        return dayFormat
                .format(date1)
                .equals(dayFormat.format(date2));
    }

    public static List<Date> allDaysBetweenTwoDates(Date startDate, Date endDate) {

        List<Date> dates = new ArrayList<>();
        try {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(startDate);
            end.setTime(endDate);
            end.add(Calendar.DATE, +1);

            while (start.before(end)) {
                dates.add(start.getTime());
                start.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (Exception e) {

        }
        return dates;
    }

    public static int getDaysCountOfMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static int getDayOfWeekOfMonthFirstDay(Calendar calendar) {
        Calendar c = (Calendar) calendar.clone();
        c.set(Calendar.DAY_OF_MONTH, 1);
        int week = c.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else {
            week = week - 1;
        }
        return week;
    }

    public static int getCurrentMonthMaximumDay(Calendar calendar) {
        Calendar c = (Calendar) calendar.clone();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getNextMonthMaximumDay(Calendar calendar) {
        Calendar c = (Calendar) calendar.clone();
        c.add(Calendar.MONTH, 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getLastMonthMaximumDay(Calendar calendar) {
        Calendar c = (Calendar) calendar.clone();
        c.add(Calendar.MONTH, -1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static List<Date> getDateList(Calendar calendar){

        List<Date> dates = new ArrayList<>();
        Calendar save = (Calendar) calendar.clone();
        save.set(Calendar.DAY_OF_MONTH, 1);
        int week = getDayOfWeekOfMonthFirstDay(save);
        save.add(Calendar.DAY_OF_YEAR, -week);
        for (int i = 0; i < 42; i++) {
            Date tempDate = save.getTime();
            dates.add(tempDate);
            save.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    public static List<List<Event>> generateEventsInCalendar(
            List<Event> events,
            Calendar currentCal
    ) {
        Calendar save = (Calendar) currentCal.clone();
        List<List<Event>> temp = new ArrayList<>();
        currentCal.set(Calendar.DAY_OF_MONTH, 1);
        int week = getDayOfWeekOfMonthFirstDay(currentCal);
        currentCal.add(Calendar.DAY_OF_YEAR, -week);
        for (int i = 0; i < 42; i++) {
            Date tempDate = currentCal.getTime();
            List<Event> tempEventsInDay = new ArrayList<>();
            for (Event e : events) {
                if (isSameDay(e.getStartDate(), tempDate)) {
                    tempEventsInDay.add(e);
                }
            }
            if (tempEventsInDay.size() == 0) {
                tempEventsInDay = null;
            }
            temp.add(tempEventsInDay);
            currentCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        currentCal.setTime(save.getTime());
        return temp;
    }

    public static String getMonthYearString(Calendar calendar) {
        return monthYearFormat.format(calendar.getTime());
    }


    public static boolean DateA_before_DateB(Date a, Date b) {
        Date a1, a2;
        boolean bo = false;
        try {
            a1 = dayFormat.parse(dayFormat.format(a));
            a2 = dayFormat.parse(dayFormat.format(b));
            bo = a1.before(a2);
        } catch (Exception e) {

        }
        return bo;
    }

}