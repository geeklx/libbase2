package com.geek.libxuanzeqi;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gzuliyujiang.wheelpicker.DatimePicker;
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatimePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DatimeWheelLayout;

public class DateTimePickerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker1);
    }

    public void onYearMonthDayTime(View view) {
        DatimePicker picker = new DatimePicker(this);
        final DatimeWheelLayout wheelLayout = picker.getWheelLayout();
        picker.setOnDatimePickedListener(new OnDatimePickedListener() {
            @Override
            public void onDatimePicked(int year, int month, int day, int hour, int minute, int second) {
                String text = year + "-" + month + "-" + day + " " + hour + ":" + minute;
//                String text = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
//                text += wheelLayout.getTimeWheelLayout().isAnteMeridiem() ? " 上午" : " 下午";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY);
//        wheelLayout.setTimeMode(TimeMode.HOUR_12_HAS_SECOND);
        wheelLayout.setRange(DatimeEntity.now(), DatimeEntity.yearOnFuture(10));
        wheelLayout.setDateLabel("年", "月", "日");
        wheelLayout.setTimeLabel("时", "分", "");
        wheelLayout.getSecondLabelView().setVisibility(View.GONE);
        picker.show();
    }
}
