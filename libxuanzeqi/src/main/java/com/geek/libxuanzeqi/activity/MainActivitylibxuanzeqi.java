/*
 * Copyright (c) 2016-present 贵州纳雍穿青人李裕江<1032694760@qq.com>
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.geek.libxuanzeqi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.geek.libxuanzeqi.R;
import com.geek.libxuanzeqi.custom.AntFortuneLikeProvider;
import com.github.gzuliyujiang.wheelpicker.widget.LinkageWheelLayout;
import com.github.gzuliyujiang.wheelpicker.widget.OptionWheelLayout;
import com.github.gzuliyujiang.wheelview.widget.WheelView;

import java.util.Arrays;

/**
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/6/3 18:18
 */
public class MainActivitylibxuanzeqi extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_main);
        WheelView wheelView = findViewById(R.id.wheel_view);
        wheelView.setData(Arrays.asList("111", "222", "333", "444", "555", "666", "777", "888", "999",
                "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg"));
        OptionWheelLayout optionWheelLayout = findViewById(R.id.wheel_option);
        optionWheelLayout.getWheelView().setData(Arrays.asList("aaa", "bbb", "ccc", "123", "xxx", "yyy", "zzz"));
        LinkageWheelLayout linkageWheelLayout = findViewById(R.id.wheel_linkage);
        linkageWheelLayout.setData(new AntFortuneLikeProvider());
    }

    private void startActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void onDateTimePicker(View view) {
        startActivity(DateTimePickerActivitylibxuanzeqi.class);
    }

    public void onSinglePicker(View view) {
        startActivity(SinglePickerActivitylibxuanzeqi.class);
    }

    public void onLinkagePicker(View view) {
        startActivity(LinkagePickerActivitylibxuanzeqi.class);
    }

    public void onAddressPicker(View view) {
        startActivity(AddressPickerActivitylibxuanzeqi.class);
    }

    public void onColorPicker(View view) {
        startActivity(ColorPickerActivitylibxuanzeqi.class);
    }

    public void onFilePicker(View view) {
        startActivity(FilePickerActivitylibxuanzeqi.class);
    }

    public void onCalendarPicker(View view) {
        startActivity(CalendarPickerActivitylibxuanzeqi.class);
    }

    public void onImagePicker(View view) {
        startActivity(ImagePickerActivitylibxuanzeqi.class);
    }

}
