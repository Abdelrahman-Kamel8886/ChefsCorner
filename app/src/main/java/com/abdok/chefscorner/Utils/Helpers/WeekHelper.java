package com.abdok.chefscorner.Utils.Helpers;

import com.abdok.chefscorner.Data.Models.DateDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeekHelper {

    public static List<DateDTO> getCurrentWeek() {
        List<DateDTO> weekData = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat subDateFormat = new SimpleDateFormat("dd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            String date = dateFormat.format(calendar.getTime());
            String subDate = subDateFormat.format(calendar.getTime());
            String day = dayFormat.format(calendar.getTime());
            weekData.add(new DateDTO(date, subDate, day));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
            return weekData;
        }
}
