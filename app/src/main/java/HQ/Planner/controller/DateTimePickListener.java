package HQ.Planner.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimePickListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    private Context context;
    private TextView sDate;
    private Button setButton;
    private String instruction;
    private Date date;

    private Date setDate;

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int yearFinal;
    private int dayFinal;
    private int monthFinal;
    private int hourFinal;
    private int minuteFinal;


    public DateTimePickListener(Context context
    ) {
        this.context = context;


    }

    //    public DateTimePickListener(Context context,
//                                TextView ssDate
//    ) {
//        this.context = context;
//        sDate = ssDate;
//
//    }
    public DateTimePickListener(Context context,
                                Button setButton, Date setDate, String instruction
    ) {
        this.context = context;
        this.setButton = setButton;
        this.instruction = instruction;
        this.setDate = setDate;

    }

    @Override
    public void onClick(View v) {

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                this,
                year,
                month,
                day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                context,
                this,
                hour,
                minute,
                false);

        timePickerDialog.show();


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        hourFinal = hourOfDay;
        minuteFinal = minute;
        // dd/MM/yyyy h:mm:ss

        String s;
        if (hourFinal < 12) {
            s = " AM";
        } else {
            s = " PM";
        }

        String datetime = dayFinal + "/" +
                monthFinal + "/" +
                yearFinal + " "
                + hourFinal + ":" +
                minuteFinal + ":00";


        System.out.println("setting");
        System.out.println(datetime);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            date = format.parse(datetime);
            System.err.println(date.toString());


            System.err.println("Set time");
        } catch (ParseException e) {
            System.err.print("Time Parse Error.");
        }

        //    sDate.setText(datetime + s);
        setButton.setText(instruction + datetime + s);
        this.setDate = this.date;
    }

    public Date getDate() {
        return date;
    }
}
