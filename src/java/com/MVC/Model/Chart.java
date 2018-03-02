/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MVC.Model;

import be.ceau.chart.BarChart;
import be.ceau.chart.LineChart;
import be.ceau.chart.PieChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.data.LineData;
import be.ceau.chart.data.PieData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.dataset.PieDataset;
import java.util.ArrayList;

/**
 *
 * @author Rey Christian
 */
public class Chart {

    public String getBarChart() {

        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(4.3);
        doubleFigures.add(4.6);
        doubleFigures.add(6.9);
        
        ArrayList<Double> doubleFigures2 = new ArrayList();
        doubleFigures2.add(9.4);
        doubleFigures2.add(8.1);
        doubleFigures2.add(5.5);
        
        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Sample 1");
        stringLabels.add("Sample 2");
        stringLabels.add("Sample 3");
        
        BarDataset dataset = new BarDataset();
        dataset.setLabel("DATASET");
        for(int i = 0; i < doubleFigures.size(); i++){
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
        }
        dataset.setBorderWidth(2);
        
        BarDataset dataset2 = new BarDataset();
        dataset2.setLabel("CHOCOLATE");
        for(int i = 0; i < doubleFigures2.size(); i++){
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addBackgroundColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);
        
        BarData data = new BarData();
        for(int j = 0; j < stringLabels.size(); j++){
            data.addLabel(stringLabels.get(j));
        }
        
        data.addDataset(dataset);
        data.addDataset(dataset2);
        
        return new BarChart(data).toJson();
    }
    
    public String getLineChart(){
        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(5.3);
        doubleFigures.add(4.6);
        doubleFigures.add(6.9);
        
        ArrayList<Double> doubleFigures2 = new ArrayList();
        doubleFigures2.add(9.4);
        doubleFigures2.add(8.1);
        doubleFigures2.add(3.1);
        
        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        
        LineDataset dataset = new LineDataset();
        dataset.setLabel("DATASET");
        for(int i = 0; i < doubleFigures.size(); i++){
            dataset.addData(doubleFigures.get(i));
            dataset.addPointBackgroundColor(Color.LIGHT_BLUE);
            dataset.setBorderColor(Color.LIGHT_BLUE);
        }
        dataset.setBorderWidth(2);
        
        LineDataset dataset2 = new LineDataset();
        dataset2.setLabel("CHOCOLATE");
        for(int i = 0; i < doubleFigures2.size(); i++){
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addPointBackgroundColor(Color.CHOCOLATE);
            dataset2.setBorderColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);
        
        LineData data = new LineData();
        for(int j = 0; j < stringLabels.size(); j++){
            data.addLabel(stringLabels.get(j));
        }
        
        data.addDataset(dataset);
        data.addDataset(dataset2);
        
        return new LineChart(data).toJson();
    }
    
    public String getPieChart(){
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(40);
        doubleFigures.add(30);
        doubleFigures.add(30);
        
        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        
        PieDataset dataset = new PieDataset();
        dataset.setLabel("DATASET");
        for(int i = 0; i < doubleFigures.size(); i++){
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.random());
        }
        dataset.setBorderWidth(2);
        
        
        PieData data = new PieData();
        for(int j = 0; j < stringLabels.size(); j++){
            data.addLabel(stringLabels.get(j));
        }
        
        data.addDataset(dataset);
        
        
        return new PieChart(data).toJson();
    }
    
    
}
