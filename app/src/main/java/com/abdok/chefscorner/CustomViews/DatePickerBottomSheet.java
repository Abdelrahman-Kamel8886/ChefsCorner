package com.abdok.chefscorner.CustomViews;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abdok.chefscorner.Models.DateDTO;
import com.abdok.chefscorner.Utils.Helpers.WeekHelper;
import com.abdok.chefscorner.databinding.BottomSheetDatePickerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatePickerBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetDatePickerBinding binding;

    private OnDateSelectedListener onDateSelectedListener;

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetDatePickerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<DateDTO> weekData = WeekHelper.getCurrentWeek();

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());


        try {
            Calendar minCalendar = Calendar.getInstance();
            Calendar maxCalendar = Calendar.getInstance();

            minCalendar.setTime(sdf.parse(weekData.get(0).getDate()));
            maxCalendar.setTime(sdf.parse(weekData.get(6).getDate()));

            binding.datePicker.setMinDate(minCalendar.getTimeInMillis());
            binding.datePicker.setMaxDate(maxCalendar.getTimeInMillis());

            binding.datePicker.setOnDateChangeListener((view1, year, month, day) -> {

                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, month, day);

                String date = sdf.format(selectedCalendar.getTime());
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                SimpleDateFormat subDateFormat = new SimpleDateFormat("dd", Locale.getDefault());

                String subDate = subDateFormat.format(selectedCalendar.getTime());
                String dayText = dayFormat.format(selectedCalendar.getTime());

                DateDTO selectedDateDTO = new DateDTO(date, subDate, dayText);

                if (onDateSelectedListener != null) {
                    onDateSelectedListener.onDateSelected(selectedDateDTO);
                }

                dismiss();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface OnDateSelectedListener {
        void onDateSelected(DateDTO dateDTO);
    }

}
