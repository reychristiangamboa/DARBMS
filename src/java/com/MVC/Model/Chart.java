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
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
        }
        dataset.setBorderWidth(2);

        BarDataset dataset2 = new BarDataset();
        dataset2.setLabel("CHOCOLATE");
        for (int i = 0; i < doubleFigures2.size(); i++) {
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addBackgroundColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);

        BarData data = new BarData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(dataset2);

        return new BarChart(data).toJson();
    }

    public String getLineChart() {
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
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addPointBackgroundColor(Color.LIGHT_BLUE);
            dataset.setBorderColor(Color.LIGHT_BLUE);
        }
        dataset.setBorderWidth(2);

        LineDataset dataset2 = new LineDataset();
        dataset2.setLabel("CHOCOLATE");
        for (int i = 0; i < doubleFigures2.size(); i++) {
            dataset2.addData(doubleFigures2.get(i));
            dataset2.addPointBackgroundColor(Color.CHOCOLATE);
            dataset2.setBorderColor(Color.CHOCOLATE);
        }
        dataset2.setBorderWidth(2);

        LineData data = new LineData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(dataset2);

        return new LineChart(data).toJson();
    }

    public String getPieChart() {
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
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.random());
        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getPieChartGender(ArrayList<ARB> arbGender) {

        ArrayList<ARB> dataMale = new ArrayList();
        ArrayList<ARB> dataFemale = new ArrayList();

        for (int i = 0; i < arbGender.size(); i++) {
            if (arbGender.get(i).getGender().equalsIgnoreCase("M")) {
                dataMale.add(arbGender.get(i));
            } else if (arbGender.get(i).getGender().equalsIgnoreCase("F")) {
                dataFemale.add(arbGender.get(i));
            }
        }
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(dataMale.size());
        doubleFigures.add(dataFemale.size());

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Male");
        stringLabels.add("Female");

        PieDataset dataset = new PieDataset();
        dataset.setLabel("DATASET");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.AQUA);
            dataset.addBackgroundColor(Color.PINK);

        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartEducation(ArrayList<ARB> arbEducChart) {
        int noGrade = 0;
        int primary = 0;
        int intermediate = 0;
        int elementaryGrad = 0;
        int hsLevel = 0;
        int hsGrad = 0;
        int collegeLevel = 0;
        int collegeGrad = 0;
        int gradsStud = 0;
        int vocational = 0;

        for (int i = 0; i < arbEducChart.size(); i++) {
            if (arbEducChart.get(i).getEducationLevel() == 0) {
                noGrade++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 1) {
                primary++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 2) {
                intermediate++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 3) {
                elementaryGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 4) {
                hsLevel++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 5) {
                hsGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 6) {
                collegeLevel++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 7) {
                collegeGrad++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 8) {
                gradsStud++;
            }
            if (arbEducChart.get(i).getEducationLevel() == 9) {
                vocational++;
            }
        }
        ArrayList<Integer> doubleFigures = new ArrayList();
        doubleFigures.add(noGrade);
        doubleFigures.add(primary);
        doubleFigures.add(intermediate);
        doubleFigures.add(elementaryGrad);
        doubleFigures.add(hsLevel);
        doubleFigures.add(hsGrad);
        doubleFigures.add(collegeLevel);
        doubleFigures.add(collegeGrad);
        doubleFigures.add(gradsStud);
        doubleFigures.add(vocational);

        ArrayList<Integer> primaryEduc = new ArrayList();
        primaryEduc.add(primary);

        ArrayList<Integer> intermediateEduc = new ArrayList();
        intermediateEduc.add(intermediate);

        ArrayList<Integer> elementaryGradEduc = new ArrayList();
        elementaryGradEduc.add(elementaryGrad);

        ArrayList<Integer> hsLevelEduc = new ArrayList();
        hsLevelEduc.add(hsLevel);

        ArrayList<Integer> hsGradEduc = new ArrayList();
        hsGradEduc.add(hsGrad);

        ArrayList<Integer> collegeLevelEduc = new ArrayList();
        collegeLevelEduc.add(collegeLevel);

        ArrayList<Integer> collegeGradEduc = new ArrayList();
        collegeGradEduc.add(collegeGrad);

        ArrayList<Integer> gradsStudEduc = new ArrayList();
        gradsStudEduc.add(gradsStud);

        ArrayList<Integer> vocationalEduc = new ArrayList();
        vocationalEduc.add(vocational);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Educational Level");

        BarDataset dataset = new BarDataset();
        dataset.setLabel("No Formal Level");
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
        }
        dataset.setBorderWidth(2);

        BarDataset datasetPrimaryEduc = new BarDataset();
        datasetPrimaryEduc.setLabel("Primary");
        for (int i = 0; i < primaryEduc.size(); i++) {
            datasetPrimaryEduc.addData(primaryEduc.get(i));
            datasetPrimaryEduc.addBackgroundColor(Color.RED);
        }
        datasetPrimaryEduc.setBorderWidth(2);

        BarDataset datasetsIntermediateEduc = new BarDataset();
        datasetsIntermediateEduc.setLabel("Intermediate");
        for (int i = 0; i < intermediateEduc.size(); i++) {
            datasetsIntermediateEduc.addData(intermediateEduc.get(i));
            datasetsIntermediateEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsIntermediateEduc.setBorderWidth(2);

        BarDataset datasetsElementaryGradEduc = new BarDataset();
        datasetsElementaryGradEduc.setLabel("Elementary Graduate");
        for (int i = 0; i < elementaryGradEduc.size(); i++) {
            datasetsElementaryGradEduc.addData(elementaryGradEduc.get(i));
            datasetsElementaryGradEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsElementaryGradEduc.setBorderWidth(2);

        BarDataset datasetsHsLevelEduc = new BarDataset();
        datasetsHsLevelEduc.setLabel("High School Level");
        for (int i = 0; i < hsLevelEduc.size(); i++) {
            datasetsHsLevelEduc.addData(hsLevelEduc.get(i));
            datasetsHsLevelEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsHsLevelEduc.setBorderWidth(2);

        BarDataset datasetsHsGradEduc = new BarDataset();
        datasetsHsGradEduc.setLabel("High School Graduate");
        for (int i = 0; i < hsGradEduc.size(); i++) {
            datasetsHsGradEduc.addData(hsGradEduc.get(i));
            datasetsHsGradEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsHsGradEduc.setBorderWidth(2);

        BarDataset datasetsCollegeLevelEduc = new BarDataset();
        datasetsCollegeLevelEduc.setLabel("College Level");
        for (int i = 0; i < collegeLevelEduc.size(); i++) {
            datasetsCollegeLevelEduc.addData(collegeLevelEduc.get(i));
            datasetsCollegeLevelEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsCollegeLevelEduc.setBorderWidth(2);
        
        BarDataset datasetsCollegeGradEduc = new BarDataset();
        datasetsCollegeGradEduc.setLabel("College Graduate");
        for (int i = 0; i < collegeGradEduc.size(); i++) {
            datasetsCollegeGradEduc.addData(collegeGradEduc.get(i));
            datasetsCollegeGradEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsCollegeGradEduc.setBorderWidth(2);

        BarDataset datasetsGradsStudEduc = new BarDataset();
        datasetsGradsStudEduc.setLabel("Graduate Studies");
        for (int i = 0; i < gradsStudEduc.size(); i++) {
            datasetsGradsStudEduc.addData(gradsStudEduc.get(i));
            datasetsGradsStudEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsGradsStudEduc.setBorderWidth(2);
        
        BarDataset datasetsVocationalEduc = new BarDataset();
        datasetsVocationalEduc.setLabel("Vocational");
        for (int i = 0; i < vocationalEduc.size(); i++) {
            datasetsVocationalEduc.addData(vocationalEduc.get(i));
            datasetsVocationalEduc.addBackgroundColor(Color.AQUA);
        }
        datasetsVocationalEduc.setBorderWidth(2);

        BarData data = new BarData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
        data.addDataset(datasetPrimaryEduc);
        data.addDataset(datasetsIntermediateEduc);
        data.addDataset(datasetsElementaryGradEduc);
        data.addDataset(datasetsHsLevelEduc);
        data.addDataset(datasetsHsGradEduc);
        data.addDataset(datasetsCollegeLevelEduc);
        data.addDataset(datasetsCollegeGradEduc);
        data.addDataset(datasetsGradsStudEduc);
        data.addDataset(datasetsVocationalEduc);

        return new BarChart(data).toJson();
    }

}
