package com.xabaizhong.treemonistor.activity.query_treeOrGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by admin on 2017/3/24.
 */

public class AreaAxisValueFormatter implements IAxisValueFormatter {


    private  BarChart mChart;

    public AreaAxisValueFormatter(){

    }
    public AreaAxisValueFormatter(BarChart mChart) {
        this.mChart = mChart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int areaId = (int) value;
        String cname = "default";
        switch (areaId % 10) {
            case 0:
                cname = "area 1";
                break;
            case 1:
                cname = "area 2";
                break;
            case 2:
                cname = "area 3";
                break;
            case 3:
                cname = "area 4";
                break;
            case 4:
                cname = "area 5";
                break;
            case 5:
                cname = "area 6";
                break;
            case 6:
                cname = "area 7";
                break;
            case 7:
                cname = "area 8";
                break;
            case 8:
                cname = "area 9";
                break;
            case 9:
                cname = "area 10";
                break;
        }
        return cname;
    }
}
