package com.study.alryumin.sleeptrack.helper;

import android.util.Base64;

import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;

import java.util.List;

public class ChartHelper {
    public static String getSleepTimeCoreChart(String elements){

        String color = "#9575cd";
        String columnWidth = "20";
//        elements = "['03/09', 8.36]," +
//                "        ['04/09', 6.45],";

        String content = "<html>" +
                "<head>" +
                "    <script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>" +
                "    <script type='text/javascript'>" +
                "google.charts.load('current', {packages:['corechart']});" +
                "    google.charts.setOnLoadCallback(drawChart);" +
                "    function drawChart() {" +
                "      var data = google.visualization.arrayToDataTable([" +
                "        ['Element', 'Density' ]," + elements +
                "      ]);" +
                
                "      var view = new google.visualization.DataView(data);" +
                "      view.setColumns([0, 1," +
                "                       { calc: 'stringify'," +
                "                         sourceColumn: 1," +
                "                         type: 'string'," +
                "                         role: 'annotation' }" +
                "                      ]);" +
                
                "      var options = {" +
                "        colors: ['" + color + "']," +
                "        legend: {position: 'none'}," +
                "        bar: {" +
                "           groupWidth: "+ columnWidth +
                "}," +
                "   'tooltip' : {" +
                "  trigger: 'none'" +
                "}" +
                "      };" +
                "      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));" +
                "      chart.draw(view, options);" +
                "  }"
                +
                "    </script>" +
                "</head>" +
                "<body>" +
                "<div id='chart_div'></div>" +
                "</body>" +
                "</html>";

        return  Base64.encodeToString(content.getBytes(), Base64.NO_PADDING);
    }
    public static String getSleepTimeCoreChart(List<SleepTime> items){

        String color = "#9575cd";
        String columnWidth = "20";

        String elements = "";
        for(SleepTime sleepTime : items){
            String date = DateFormatHelper.getDateDayMonthFormat(sleepTime.getFinishAt());
            String time = DateFormatHelper.getTimeDoubleFormat(sleepTime.getSleepTime());

            elements += "['" + date + "', " + time + "],";
        }

        String content = "<html>" +
                "<head>" +
                "    <script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>" +
                "    <script type='text/javascript'>" +
                "google.charts.load('current', {packages:['corechart']});" +
                "    google.charts.setOnLoadCallback(drawChart);" +
                "    function drawChart() {" +
                "      var data = google.visualization.arrayToDataTable([" +
                "        ['Element', 'Density' ]," + elements +
                "      ]);" +

                "      var view = new google.visualization.DataView(data);" +
                "      view.setColumns([0, 1," +
                "                       { calc: 'stringify'," +
                "                         sourceColumn: 1," +
                "                         type: 'string'," +
                "                         role: 'annotation' }" +
                "                      ]);" +

                "      var options = {" +
                "        colors: ['" + color + "']," +
                "        legend: {position: 'none'}," +
                "        bar: {" +
                "           groupWidth: "+ columnWidth +
                "}," +
                "   'tooltip' : {" +
                "  trigger: 'none'" +
                "}" +
                "      };" +
                "      var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));" +
                "      chart.draw(view, options);" +
                "  }"
                +
                "    </script>" +
                "</head>" +
                "<body>" +
                "<div id='chart_div'></div>" +
                "</body>" +
                "</html>";

        return  Base64.encodeToString(content.getBytes(), Base64.NO_PADDING);
    }
}
