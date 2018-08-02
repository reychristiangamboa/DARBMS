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
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.LineOptions;
import be.ceau.chart.options.Title;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.*;
import com.MVC.DAO.APCPRequestDAO;
import com.MVC.DAO.ARBDAO;
import com.MVC.DAO.ARBODAO;
import com.MVC.DAO.AddressDAO;
import com.MVC.DAO.CAPDEVDAO;
import java.util.ArrayList;
import com.MVC.DAO.CropDAO;
import com.MVC.Model.Crop;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rey Christian
 */
public class Chart {

    Locale pinoy = new Locale("fil", "PH");
    NumberFormat currency = NumberFormat.getCurrencyInstance(pinoy);

    public double getAverage(ArrayList<Double> figures) {
        double sum = 0;
        for (double d : figures) {
            sum += d;
        }
        return sum / figures.size();
    }

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

//        BarDataset dataset2 = new BarDataset();
//        dataset2.setLabel("CHOCOLATE");
//        for (int i = 0; i < doubleFigures2.size(); i++) {
//            dataset2.addData(doubleFigures2.get(i));
//            dataset2.addBackgroundColor(Color.CHOCOLATE);
//        }
//        dataset2.setBorderWidth(2);
        BarData data = new BarData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);
//        data.addDataset(dataset2);

        return new BarChart(data).toJson();
    }

    public String getTotalYearBarChart(ArrayList<APCPRequest> requests) {

        ARBODAO arboDAO = new ARBODAO();
        BarData data = new BarData();
        for (APCPRequest req : requests) {
            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
            BarDataset dataset = new BarDataset();
            dataset.setLabel(arbo.getArboName());
            dataset.setData(req.getTotalReleasedAmountPerRequest());
            dataset.addBackgroundColor(Color.random());
            dataset.setBorderWidth(2);
            data.addDataset(dataset);
        }

        BarOptions options = new BarOptions();
        options.setResponsive(true);

        Title title = new Title();
        title.setDisplay(true);

        Calendar cal = Calendar.getInstance();
        Long l = System.currentTimeMillis();
        Date d = new Date(l);
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);

        title.setText("TOTAL " + year + " RELEASED AMOUNT");
        title.setFontSize(30);
        options.setTitle(title);

        return new BarChart(data, options).toJson();
    }

    public String getTotalRequestedAmountBarChart(ArrayList<APCPRequest> requests) {

        ARBODAO arboDAO = new ARBODAO();
        BarData data = new BarData();
        for (APCPRequest req : requests) {
            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
            BarDataset dataset = new BarDataset();
            dataset.setLabel(arbo.getArboName());
            dataset.setData(req.getLoanAmount());
            dataset.addBackgroundColor(Color.random());
            dataset.setBorderWidth(2);
            data.addDataset(dataset);
        }

        BarOptions options = new BarOptions();
        options.setResponsive(true);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("TOTAL APPROVED AMOUNT");
        title.setFontSize(30);
        options.setTitle(title);

        return new BarChart(data, options).toJson();
    }

    public String getTotalReleasedAmountBarChart(ArrayList<APCPRequest> requests) {

        ARBODAO arboDAO = new ARBODAO();
        BarData data = new BarData();
        for (APCPRequest req : requests) {
            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
            BarDataset dataset = new BarDataset();
            dataset.setLabel(arbo.getArboName());
            dataset.setData(req.getTotalReleasedAmount());
            dataset.addBackgroundColor(Color.random());
            dataset.setBorderWidth(2);
            data.addDataset(dataset);
        }

        BarOptions options = new BarOptions();
        options.setResponsive(true);

        Title title = new Title();
        title.setText("ACCUMULATED RELEASES");
        title.setFontSize(30);
        title.setDisplay(true);
        options.setTitle(title);

        return new BarChart(data, options).toJson();
    }

    public String getCropHistory(ArrayList<Crop> crops, ArrayList<ARB> arbList) throws ParseException {

        ArrayList<Date> dates = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        long l = System.currentTimeMillis();
        Date d = new Date(l);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int year = calendar.get(Calendar.YEAR);

        String lastYear = Integer.toString(year - 1);
        String thisYear = Integer.toString(year);
        String nextYear = Integer.toString(year + 1);

        int month = calendar.get(Calendar.MONTH);
        CropDAO cDAO = new CropDAO();

        java.util.Date javaDate = null;
        java.sql.Date sqlDate = null;
        String date = "";

        if (month >= 7) { // if current month is JULY onwards
            sqlDate = java.sql.Date.valueOf(thisYear + "-07-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-08-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-09-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-10-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-11-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-12-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-01-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-02-28");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-03-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-04-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-05-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(nextYear + "-06-30");
            dates.add(sqlDate);
        } else {
            sqlDate = java.sql.Date.valueOf(lastYear + "-07-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(lastYear + "-08-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(lastYear + "-09-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(lastYear + "-10-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(lastYear + "-11-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(lastYear + "-12-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-01-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-02-28");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-03-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-04-30");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-05-31");
            dates.add(sqlDate);

            sqlDate = java.sql.Date.valueOf(thisYear + "-06-30");
            dates.add(sqlDate);

        }

        LineData data = new LineData();

        for (Crop arbC : crops) { // CROP TYPE
            LineDataset dataset = new LineDataset();
            dataset.setBorderColor(Color.random());
            dataset.setBackgroundColor(Color.TRANSPARENT);
            dataset.setLabel(arbC.getCropTypeDesc());

            for (Date dateSQL : dates) { // LOOP THROUGH DATES
                int totalCount = 0;
                for (ARB arb : arbList) { // PER ARB
                    for (Crop arbCrops : arb.getCrops()) { // ACCESS ARB CROPS
                        // CHECKS IF dateSQL BETWEEN startDate AND endDate AND cropType == arbCropType
                        if (arbCrops.getStartDate().compareTo(dateSQL) * dateSQL.compareTo(arbCrops.getEndDate()) >= 0 && arbC.getCropType() == arbCrops.getCropType()) {
                            totalCount++;
                        }
                    }
                }
                dataset.addData(totalCount);
            }

            data.addDataset(dataset);
        }

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("July");
        stringLabels.add("August");
        stringLabels.add("September");
        stringLabels.add("October");
        stringLabels.add("November");
        stringLabels.add("December");
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        stringLabels.add("April");
        stringLabels.add("May");
        stringLabels.add("June");

        for (String label : stringLabels) {
            data.addLabel(label);
        }

        return new LineChart(data).toJson();
    }

    public String getAPCPRating(ArrayList<Evaluation> apcpEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("APCP Rating");

        Calendar cal = Calendar.getInstance();

        for (Evaluation e : apcpEvaluations) {

            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }

    public String getCAPDEVRating(ArrayList<Evaluation> capdevEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("CAPDEV Rating");

        Calendar cal = Calendar.getInstance();

        for (Evaluation e : capdevEvaluations) {

            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }

    public String getARBRating(ArrayList<Evaluation> arbEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("ARB Rating");

        Calendar cal = Calendar.getInstance();

        for (Evaluation e : arbEvaluations) {

            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
    }

    public String getLINKSFARMRating(ArrayList<Evaluation> arbEvaluations) {

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        LineData data = new LineData();
        LineDataset dataset = new LineDataset();
        dataset.setBorderColor(Color.random());
        dataset.addPointBackgroundColor(Color.DARK_GREEN);
        dataset.setBackgroundColor(Color.TRANSPARENT);
        dataset.setLabel("LINKSFARM Rating");

        Calendar cal = Calendar.getInstance();

        for (Evaluation e : arbEvaluations) {

            data.addLabel(f.format(e.getEvaluationDate()));
            dataset.addData(e.getRating());
        }

        data.addDataset(dataset);

        return new LineChart(data).toJson();
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
        doubleFigures2.add(3.1);

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("January");
        stringLabels.add("February");
        stringLabels.add("March");
        stringLabels.add("June");

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
            datasetsIntermediateEduc.addBackgroundColor(Color.random());
        }
        datasetsIntermediateEduc.setBorderWidth(2);

        BarDataset datasetsElementaryGradEduc = new BarDataset();
        datasetsElementaryGradEduc.setLabel("Elementary Graduate");
        for (int i = 0; i < elementaryGradEduc.size(); i++) {
            datasetsElementaryGradEduc.addData(elementaryGradEduc.get(i));
            datasetsElementaryGradEduc.addBackgroundColor(Color.random());
        }
        datasetsElementaryGradEduc.setBorderWidth(2);

        BarDataset datasetsHsLevelEduc = new BarDataset();
        datasetsHsLevelEduc.setLabel("High School Level");
        for (int i = 0; i < hsLevelEduc.size(); i++) {
            datasetsHsLevelEduc.addData(hsLevelEduc.get(i));
            datasetsHsLevelEduc.addBackgroundColor(Color.random());
        }
        datasetsHsLevelEduc.setBorderWidth(2);

        BarDataset datasetsHsGradEduc = new BarDataset();
        datasetsHsGradEduc.setLabel("High School Graduate");
        for (int i = 0; i < hsGradEduc.size(); i++) {
            datasetsHsGradEduc.addData(hsGradEduc.get(i));
            datasetsHsGradEduc.addBackgroundColor(Color.random());
        }
        datasetsHsGradEduc.setBorderWidth(2);

        BarDataset datasetsCollegeLevelEduc = new BarDataset();
        datasetsCollegeLevelEduc.setLabel("College Level");
        for (int i = 0; i < collegeLevelEduc.size(); i++) {
            datasetsCollegeLevelEduc.addData(collegeLevelEduc.get(i));
            datasetsCollegeLevelEduc.addBackgroundColor(Color.random());
        }
        datasetsCollegeLevelEduc.setBorderWidth(2);

        BarDataset datasetsCollegeGradEduc = new BarDataset();
        datasetsCollegeGradEduc.setLabel("College Graduate");
        for (int i = 0; i < collegeGradEduc.size(); i++) {
            datasetsCollegeGradEduc.addData(collegeGradEduc.get(i));
            datasetsCollegeGradEduc.addBackgroundColor(Color.random());
        }
        datasetsCollegeGradEduc.setBorderWidth(2);

        BarDataset datasetsGradsStudEduc = new BarDataset();
        datasetsGradsStudEduc.setLabel("Graduate Studies");
        for (int i = 0; i < gradsStudEduc.size(); i++) {
            datasetsGradsStudEduc.addData(gradsStudEduc.get(i));
            datasetsGradsStudEduc.addBackgroundColor(Color.random());
        }
        datasetsGradsStudEduc.setBorderWidth(2);

        BarDataset datasetsVocationalEduc = new BarDataset();
        datasetsVocationalEduc.setLabel("Vocational");
        for (int i = 0; i < vocationalEduc.size(); i++) {
            datasetsVocationalEduc.addData(vocationalEduc.get(i));
            datasetsVocationalEduc.addBackgroundColor(Color.random());
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

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }

    // <editor-fold desc="ARB REPORTS">
    // <editor-fold desc="GENDER">
    // <editor-fold desc="COUNT">
    public String getPieChartGender(ArrayList<ARB> arbGender) {

        ArrayList<ARB> dataMale = new ArrayList();
        ArrayList<ARB> dataFemale = new ArrayList();

        for (ARB arb : arbGender) {
            if (arb.getGender().equalsIgnoreCase("M")) {
                dataMale.add(arb);
            } else if (arb.getGender().equalsIgnoreCase("F")) {
                dataFemale.add(arb);
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
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);

        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartARBGenderByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();

        for (Region r : regionList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("M")) {
                    maleTotal++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("F")) {
                    femaleTotal++;
                }
            }
            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(r.getRegDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartARBGenderByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();

        for (Province p : provOfficeList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                    maleTotal++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                    femaleTotal++;
                }
            }
            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(p.getProvDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="APCP RECIPIENT">
    public String getPieChartGenderRecipient(ArrayList<ARB> arbGender) {

        ArrayList<ARB> dataMale = new ArrayList();
        ArrayList<ARB> dataFemale = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();

        for (ARB arb : arbGender) {
            if (dao.checkHasBeenRecipient(arb.getArbID())) {
                if (arb.getGender().equalsIgnoreCase("M")) {
                    dataMale.add(arb);
                } else if (arb.getGender().equalsIgnoreCase("F")) {
                    dataFemale.add(arb);
                }
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
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);

        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartARBGenderRecipientByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();

        for (Region r : regionList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenRecipient(arb.getArbID())) {
                    if (arb.getRegCode() == r.getRegCode() && arb.getGender().equals("M")) {
                        maleTotal++;
                    } else if (arb.getRegCode() == r.getRegCode() && arb.getGender().equals("F")) {
                        femaleTotal++;
                    }
                }

            }
            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(r.getRegDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartARBGenderRecipientByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();
        APCPRequestDAO dao = new APCPRequestDAO();

        for (Province p : provOfficeList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenRecipient(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                        maleTotal++;
                    } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                        femaleTotal++;
                    }
                }
            }

            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(p.getProvDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="CAPDEV PARTICIPANT">
    public String getPieChartGenderParticipant(ArrayList<ARB> arbGender) {

        ArrayList<ARB> dataMale = new ArrayList();
        ArrayList<ARB> dataFemale = new ArrayList();
        CAPDEVDAO dao = new CAPDEVDAO();

        for (ARB arb : arbGender) {
            if (dao.checkHasBeenParticipant(arb.getArbID())) {
                if (arb.getGender().equalsIgnoreCase("M")) {
                    dataMale.add(arb);
                } else if (arb.getGender().equalsIgnoreCase("F")) {
                    dataFemale.add(arb);
                }
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
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);

        }
        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartARBGenderParticipantByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();
        CAPDEVDAO dao = new CAPDEVDAO();

        for (Region r : regionList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenParticipant(arb.getArbID())) {
                    if (arb.getRegCode() == r.getRegCode() && arb.getGender().equals("M")) {
                        maleTotal++;
                    } else if (arb.getRegCode() == r.getRegCode() && arb.getGender().equals("F")) {
                        femaleTotal++;
                    }
                }

            }
            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(r.getRegDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartARBGenderParticipantByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Integer> maleValues = new ArrayList();
        ArrayList<Integer> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO dao = new CAPDEVDAO();

        for (Province p : provOfficeList) {
            int maleTotal = 0;
            int femaleTotal = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenParticipant(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                        maleTotal++;
                    } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                        femaleTotal++;
                    }
                }
            }

            maleValues.add(maleTotal);
            femaleValues.add(femaleTotal);
            data.addLabel(p.getProvDesc());
        }

        for (int maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (int femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="PARTICIPATION RATE">
    public String getPieChartGenderParticipation(ArrayList<ARB> arbs) {

        ArrayList<Double> dataMale = new ArrayList();
        ArrayList<Double> dataFemale = new ArrayList();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        for (ARB arb : arbs) {
            arb.setActivities(capdevDAO.getCAPDEVPlanByARB(arb.getArbID()));
            if (arb.getGender().equalsIgnoreCase("M")) {
                dataMale.add(act.getAttendanceRate(arb.getActivities()));
            } else if (arb.getGender().equalsIgnoreCase("F")) {
                dataFemale.add(act.getAttendanceRate(arb.getActivities()));
            }
        }
        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(getAverage(dataMale));
        doubleFigures.add(getAverage(dataFemale));

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Male");
        stringLabels.add("Female");

        PieDataset dataset = new PieDataset();
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);
        }

        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartGenderParticipationByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        ARBODAO arboDAO = new ARBODAO();

        for (Region r : regionList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                arb.setActivities(capdevDAO.getCAPDEVPlanByARB(arb.getArbID()));
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("M")) {
                    maleTotal += act.getAttendanceRate(arb.getActivities());
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("F")) {
                    femaleTotal += act.getAttendanceRate(arb.getActivities());
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount);
            data.addLabel(r.getRegDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartGenderParticipationByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        for (Province p : provOfficeList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                arb.setActivities(capdevDAO.getCAPDEVPlanByARB(arb.getArbID()));
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                    maleTotal += act.getAttendanceRate(arb.getActivities());
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                    femaleTotal += act.getAttendanceRate(arb.getActivities());
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount++);
            data.addLabel(p.getProvDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="DISBURSEMENT">
    public String getPieChartDisbursementARB(ArrayList<ARB> arbs) {

        ArrayList<Double> dataMale = new ArrayList();
        ArrayList<Double> dataFemale = new ArrayList();

        for (ARB arb : arbs) {
            if (arb.getGender().equalsIgnoreCase("M")) {
                dataMale.add(arb.getCurrentTotalDisbursementAmount());
            } else if (arb.getGender().equalsIgnoreCase("F")) {
                dataFemale.add(arb.getCurrentTotalDisbursementAmount());
            }
        }
        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(getAverage(dataMale));
        doubleFigures.add(getAverage(dataFemale));

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Male");
        stringLabels.add("Female");

        PieDataset dataset = new PieDataset();
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);
        }

        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartDisbursementsByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();

        for (Region r : regionList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("M")) {
                    maleTotal += arb.getCurrentTotalDisbursementAmount();
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("F")) {
                    femaleTotal += arb.getCurrentTotalDisbursementAmount();
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount);
            data.addLabel(r.getRegDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartDisbursementsByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();

        for (Province p : provOfficeList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                    maleTotal += arb.getCurrentTotalDisbursementAmount();
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                    femaleTotal += arb.getCurrentTotalDisbursementAmount();
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount++);
            data.addLabel(p.getProvDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="O/S BALANCE">
    public String getPieChartOSBalanceARB(ArrayList<ARB> arbs) {

        ArrayList<Double> dataMale = new ArrayList();
        ArrayList<Double> dataFemale = new ArrayList();
        APCPRequest r = new APCPRequest();

        for (ARB arb : arbs) {

            if (arb.getGender().equalsIgnoreCase("M")) {
                dataMale.add(r.getTotalARBOSBalance(arb.getArbID()));
            } else if (arb.getGender().equalsIgnoreCase("F")) {
                dataFemale.add(r.getTotalARBOSBalance(arb.getArbID()));
            }
        }
        ArrayList<Double> doubleFigures = new ArrayList();
        doubleFigures.add(getAverage(dataMale));
        doubleFigures.add(getAverage(dataFemale));

        ArrayList<String> stringLabels = new ArrayList();
        stringLabels.add("Male");
        stringLabels.add("Female");

        PieDataset dataset = new PieDataset();
        for (int i = 0; i < doubleFigures.size(); i++) {
            dataset.addData(doubleFigures.get(i));
            dataset.addBackgroundColor(Color.BLUE);
            dataset.addBackgroundColor(Color.DARK_SALMON);
        }

        dataset.setBorderWidth(2);

        PieData data = new PieData();
        for (int j = 0; j < stringLabels.size(); j++) {
            data.addLabel(stringLabels.get(j));
        }

        data.addDataset(dataset);

        return new PieChart(data).toJson();
    }

    public String getBarChartOSBalanceARBByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();
        APCPRequest req = new APCPRequest();

        for (Region r : regionList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("M")) {
                    maleTotal += req.getTotalARBOSBalance(arb.getArbID());
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode() && arb.getGender().equals("F")) {
                    femaleTotal += req.getTotalARBOSBalance(arb.getArbID());
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount);
            data.addLabel(r.getRegDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }

    public String getBarChartOSBalanceARBByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset male = new BarDataset();
        BarDataset female = new BarDataset();
        ArrayList<Double> maleValues = new ArrayList();
        ArrayList<Double> femaleValues = new ArrayList();

        ARBODAO arboDAO = new ARBODAO();
        APCPRequest req = new APCPRequest();

        for (Province p : provOfficeList) {
            double maleTotal = 0;
            int maleCount = 0;
            double femaleTotal = 0;
            int femaleCount = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("M")) {
                    maleTotal += req.getTotalARBOSBalance(arb.getArbID());
                    maleCount++;
                } else if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode() && arb.getGender().equals("F")) {
                    femaleTotal += req.getTotalARBOSBalance(arb.getArbID());
                    femaleCount++;
                }
            }
            maleValues.add(maleTotal / maleCount);
            femaleValues.add(femaleTotal / femaleCount);
            data.addLabel(p.getProvDesc());
        }

        for (double maleValue : maleValues) {
            male.addData(maleValue);
            male.addBackgroundColor(Color.AQUA);
        }

        for (double femaleValue : femaleValues) {
            female.addData(femaleValue);
            female.addBackgroundColor(Color.PINK);
        }

        male.setLabel("MALE");
        female.setLabel("FEMALE");

        data.addDataset(male);
        data.addDataset(female);

        return new BarChart(data).toJson();

    }
    // </editor-fold>
    // </editor-fold>

    // <editor-fold desc="AGE">
    // <editor-fold desc="COUNT">
    public String getBarChartAgeCount(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Integer> values = new ArrayList();
        for (int i = 0; i < labels.size(); i++) {
            int total = 0;
            for (ARB arb : arbList) {
                if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                    total++;
                } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                    total++;
                } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                    total++;
                } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                    total++;
                } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                    total++;
                } else if (i == 5 && arb.getAge() >= 65) {
                    total++;
                }
            }
            values.add(total);
            data.addLabel(labels.get(i));
        }

        for (int value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.DARK_OLIVE_GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeCountByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();

        for (Region r : regionList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt++;
                    }
                }
            }
            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeCountByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();

        for (Province p : provOfficeList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt++;
                    }
                }
            }
            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="RECIPIENTS">
    public String getBarChartAgeRecipient(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Integer> values = new ArrayList();
        APCPRequestDAO dao = new APCPRequestDAO();
        for (int i = 0; i < labels.size(); i++) {
            int total = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenRecipient(arb.getArbID())) {
                    if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                        total++;
                    } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                        total++;
                    } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                        total++;
                    } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                        total++;
                    } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                        total++;
                    } else if (i == 5 && arb.getAge() >= 65) {
                        total++;
                    }
                }
            }
            values.add(total);
            data.addLabel(labels.get(i));
        }

        for (int value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.DARK_OLIVE_GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeRecipientByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        APCPRequestDAO dao = new APCPRequestDAO();

        for (Region r : regionList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenRecipient(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                        if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                            age18to24Ct++;
                        } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                            age25to34Ct++;
                        } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                            age35to44Ct++;
                        } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                            age45to54Ct++;
                        } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                            age55to64Ct++;
                        } else if (arb.getAge() >= 65) {
                            age65greaterCt++;
                        }
                    }
                }
            }
            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeRecipientByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        APCPRequestDAO dao = new APCPRequestDAO();

        for (Province p : provOfficeList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenRecipient(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                        if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                            age18to24Ct++;
                        } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                            age25to34Ct++;
                        } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                            age35to44Ct++;
                        } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                            age45to54Ct++;
                        } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                            age55to64Ct++;
                        } else if (arb.getAge() >= 65) {
                            age65greaterCt++;
                        }
                    }
                }
            }

            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="CAPDEV PARTICIPANT">
    public String getBarChartAgeParticipant(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Integer> values = new ArrayList();
        CAPDEVDAO dao = new CAPDEVDAO();
        for (int i = 0; i < labels.size(); i++) {
            int total = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenParticipant(arb.getArbID())) {
                    if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                        total++;
                    } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                        total++;
                    } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                        total++;
                    } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                        total++;
                    } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                        total++;
                    } else if (i == 5 && arb.getAge() >= 65) {
                        total++;
                    }
                }
            }
            values.add(total);
            data.addLabel(labels.get(i));
        }

        for (int value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.DARK_OLIVE_GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeParticipantByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO dao = new CAPDEVDAO();

        for (Region r : regionList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenParticipant(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                        if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                            age18to24Ct++;
                        } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                            age25to34Ct++;
                        } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                            age35to44Ct++;
                        } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                            age45to54Ct++;
                        } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                            age55to64Ct++;
                        } else if (arb.getAge() >= 65) {
                            age65greaterCt++;
                        }
                    }
                }
            }
            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeParticipantByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO dao = new CAPDEVDAO();

        for (Province p : provOfficeList) {
            int age18to24Ct = 0;
            int age25to34Ct = 0;
            int age35to44Ct = 0;
            int age45to54Ct = 0;
            int age55to64Ct = 0;
            int age65greaterCt = 0;
            for (ARB arb : arbList) {
                if (dao.checkHasBeenParticipant(arb.getArbID())) {
                    if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                        if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                            age18to24Ct++;
                        } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                            age25to34Ct++;
                        } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                            age35to44Ct++;
                        } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                            age45to54Ct++;
                        } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                            age55to64Ct++;
                        } else if (arb.getAge() >= 65) {
                            age65greaterCt++;
                        }
                    }
                }
            }

            age18to24.addData(age18to24Ct);
            age25to34.addData(age25to34Ct);
            age35to44.addData(age35to44Ct);
            age45to54.addData(age45to54Ct);
            age55to64.addData(age55to64Ct);
            age65greater.addData(age65greaterCt);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="PARTICIPATION RATE">
    public String getBarChartAgeParticipation(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Double> values = new ArrayList();

        CAPDEVDAO dao = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        for (int i = 0; i < labels.size(); i++) {
            double total = 0;
            int total2 = 0;
            for (ARB arb : arbList) {

                arb.setActivities(dao.getCAPDEVPlanByARB(arb.getArbID()));

                if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                } else if (i == 5 && arb.getAge() >= 65) {
                    total += act.getAttendanceRate(arb.getActivities());
                    total2++;
                }
            }
            values.add(total / total2);
            data.addLabel(labels.get(i));
        }

        for (double value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeParticipationByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO dao = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        for (Region r : regionList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;

            for (ARB arb : arbList) {

                arb.setActivities(dao.getCAPDEVPlanByARB(arb.getArbID()));

                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += act.getAttendanceRate(arb.getActivities());
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += act.getAttendanceRate(arb.getActivities());
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += act.getAttendanceRate(arb.getActivities());
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += act.getAttendanceRate(arb.getActivities());
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += act.getAttendanceRate(arb.getActivities());
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += act.getAttendanceRate(arb.getActivities());
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeParticipationByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        CAPDEVDAO dao = new CAPDEVDAO();
        CAPDEVActivity act = new CAPDEVActivity();

        for (Province p : provOfficeList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;
            for (ARB arb : arbList) {

                arb.setActivities(dao.getCAPDEVPlanByARB(arb.getArbID()));

                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += act.getAttendanceRate(arb.getActivities());
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += act.getAttendanceRate(arb.getActivities());
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += act.getAttendanceRate(arb.getActivities());
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += act.getAttendanceRate(arb.getActivities());
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += act.getAttendanceRate(arb.getActivities());
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += act.getAttendanceRate(arb.getActivities());
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="DISBURSEMENT">
    public String getBarChartAgeDisbursement(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Double> values = new ArrayList();
        for (int i = 0; i < labels.size(); i++) {
            double total = 0;
            int total2 = 0;
            for (ARB arb : arbList) {
                if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                } else if (i == 5 && arb.getAge() >= 65) {
                    total += arb.getCurrentTotalDisbursementAmount();
                    total2++;
                }
            }
            values.add(total / total2);
            data.addLabel(labels.get(i));
        }

        for (double value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeDisbursementByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();

        for (Region r : regionList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;

            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += arb.getCurrentTotalDisbursementAmount();
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += arb.getCurrentTotalDisbursementAmount();
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += arb.getCurrentTotalDisbursementAmount();
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += arb.getCurrentTotalDisbursementAmount();
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += arb.getCurrentTotalDisbursementAmount();
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += arb.getCurrentTotalDisbursementAmount();
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeDisbursementByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();

        for (Province p : provOfficeList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += arb.getCurrentTotalDisbursementAmount();
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += arb.getCurrentTotalDisbursementAmount();
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += arb.getCurrentTotalDisbursementAmount();
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += arb.getCurrentTotalDisbursementAmount();
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += arb.getCurrentTotalDisbursementAmount();
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += arb.getCurrentTotalDisbursementAmount();
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    // </editor-fold>
    // <editor-fold desc="O/S BALANCE">
    public String getBarChartAgeOSBalance(ArrayList<ARB> arbList) {

        ArrayList<String> labels = new ArrayList();
        labels.add("18-24");
        labels.add("25-34");
        labels.add("35-44");
        labels.add("45-54");
        labels.add("55-64");
        labels.add("> 65");

        BarData data = new BarData();
        BarDataset dataset = new BarDataset();
        ArrayList<Double> values = new ArrayList();
        APCPRequest req = new APCPRequest();
        for (int i = 0; i < labels.size(); i++) {
            double total = 0;
            int total2 = 0;
            for (ARB arb : arbList) {
                if (i == 0 && (arb.getAge() >= 18 && arb.getAge() <= 24)) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                } else if (i == 1 && (arb.getAge() >= 25 && arb.getAge() <= 34)) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                } else if (i == 2 && (arb.getAge() >= 35 && arb.getAge() <= 44)) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                } else if (i == 3 && (arb.getAge() >= 45 && arb.getAge() <= 54)) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                } else if (i == 4 && (arb.getAge() >= 55 && arb.getAge() <= 64)) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                } else if (i == 5 && arb.getAge() >= 65) {
                    total += req.getTotalARBOSBalance(arb.getArbID());
                    total2++;
                }
            }
            values.add(total / total2);
            data.addLabel(labels.get(i));
        }

        for (double value : values) {
            dataset.addData(value);
            dataset.addBackgroundColor(Color.GREEN);
        }

        dataset.setLabel("Age");
        dataset.setBorderWidth(2);
        data.addDataset(dataset);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeOSBalanceByRegion(ArrayList<ARB> arbList, ArrayList<Region> regionList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        APCPRequest req = new APCPRequest();

        for (Region r : regionList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getArboRegion() == r.getRegCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += req.getTotalARBOSBalance(arb.getArbID());
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(r.getRegDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }

    public String getBarChartAgeOSBalanceByProvOffice(ArrayList<ARB> arbList, ArrayList<Province> provOfficeList) {

        BarData data = new BarData();
        BarDataset age18to24 = new BarDataset();
        age18to24.setBackgroundColor(Color.random());
        age18to24.setLabel("18-24");

        BarDataset age25to34 = new BarDataset();
        age25to34.setBackgroundColor(Color.random());
        age25to34.setLabel("25-34");

        BarDataset age35to44 = new BarDataset();
        age35to44.setBackgroundColor(Color.random());
        age35to44.setLabel("35-44");

        BarDataset age45to54 = new BarDataset();
        age45to54.setBackgroundColor(Color.random());
        age45to54.setLabel("45-54");

        BarDataset age55to64 = new BarDataset();
        age55to64.setBackgroundColor(Color.random());
        age55to64.setLabel("55-64");

        BarDataset age65greater = new BarDataset();
        age65greater.setBackgroundColor(Color.random());
        age65greater.setLabel("65>");

        ARBODAO arboDAO = new ARBODAO();
        APCPRequest req = new APCPRequest();

        for (Province p : provOfficeList) {
            double age18to24Ct = 0;
            int age18to24Ct2 = 0;

            double age25to34Ct = 0;
            int age25to34Ct2 = 0;

            double age35to44Ct = 0;
            int age35to44Ct2 = 0;

            double age45to54Ct = 0;
            int age45to54Ct2 = 0;

            double age55to64Ct = 0;
            int age55to64Ct2 = 0;

            double age65greaterCt = 0;
            int age65greaterCt2 = 0;
            for (ARB arb : arbList) {
                if (arboDAO.getARBOByID(arb.getArboID()).getProvOfficeCode() == p.getProvCode()) {
                    if (arb.getAge() >= 18 && arb.getAge() <= 24) {
                        age18to24Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age18to24Ct2++;
                    } else if (arb.getAge() >= 25 && arb.getAge() <= 34) {
                        age25to34Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age25to34Ct2++;
                    } else if (arb.getAge() >= 35 && arb.getAge() <= 44) {
                        age35to44Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age35to44Ct2++;
                    } else if (arb.getAge() >= 45 && arb.getAge() <= 54) {
                        age45to54Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age45to54Ct2++;
                    } else if (arb.getAge() >= 55 && arb.getAge() <= 64) {
                        age55to64Ct += req.getTotalARBOSBalance(arb.getArbID());
                        age55to64Ct2++;
                    } else if (arb.getAge() >= 65) {
                        age65greaterCt += req.getTotalARBOSBalance(arb.getArbID());
                        age65greaterCt2++;
                    }
                }
            }
            age18to24.addData(age18to24Ct / age18to24Ct2);
            age25to34.addData(age25to34Ct / age25to34Ct2);
            age35to44.addData(age35to44Ct / age35to44Ct2);
            age45to54.addData(age45to54Ct / age45to54Ct2);
            age55to64.addData(age55to64Ct / age55to64Ct2);
            age65greater.addData(age65greaterCt / age65greaterCt2);

            data.addLabel(p.getProvDesc());
        }

        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(age18to24);
        datasets.add(age25to34);
        datasets.add(age35to44);
        datasets.add(age45to54);
        datasets.add(age55to64);
        datasets.add(age65greater);

        data.setDatasets(datasets);

        return new BarChart(data).toJson();

    }
    // </editor-fold>
    // </editor-fold>

    // </editor-fold>
    //<editor-fold desc="ARBO REPORTS">
    public String getStackedBarChartARBODaysUnsettled(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ArrayList<String> labels = new ArrayList();

        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("ARBO: Credit Standing");

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;

        }

        APCPRequestDAO dao = new APCPRequestDAO();
        CAPDEVDAO dao2 = new CAPDEVDAO();
        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();
        for (ARBO arbo : arboList) {
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(arbo.getArboName()); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar
            arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
            for (String label : labels) { // REGIONS/PROVINCES
                double count = 0;

                if (b) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        count = dao.getAverageDaysUnsettled(arbo.getRequestList());
                    }
                } else { // PROVINCIAL OFFICE
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        count = dao.getAverageDaysUnsettled(arbo.getRequestList());
                    }
                }

                if (count > 0) {
                    dataset.addData(count);
                }
            }
            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }

        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();
    }
    
    public String getStackedBarChartARBOParticipationRate(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {
        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ARBDAO aDAO = new ARBDAO();
        ArrayList<String> labels = new ArrayList();

        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("ARBO: Participation Rate");

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;

        }

        APCPRequestDAO dao = new APCPRequestDAO();
        CAPDEVDAO dao2 = new CAPDEVDAO();
        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();
        for (ARBO arbo : arboList) {
            
            arbo.setArbList(aDAO.getAllARBsARBO(arbo.getArboID()));
            
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(arbo.getArboName()); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar
            for (String label : labels) { // REGIONS/PROVINCES
                double count = 0;

                if (b) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        count = capdevDAO.getMeanAverageAttendanceRateARBO(arbo.getArbList());
                    }
                } else { // PROVINCIAL OFFICE
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        count = capdevDAO.getMeanAverageAttendanceRateARBO(arbo.getArbList());
                    }
                }

                if (count > 0) {
                    dataset.addData(count);
                }
            }
            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }

        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();
    }

    //</editor-fold>
    // <editor-fold desc="APCP REPORTS">
    public String getStackedBarChartAPCPRequests(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        ArrayList<String> labels = new ArrayList();
        boolean b = true;
        APCPRequestDAO dao = new APCPRequestDAO();

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();
        ArrayList<BarDataset> datasets = new ArrayList();

        BarDataset requested = new BarDataset();
        ArrayList<Integer> requestedValues = new ArrayList();
        BarDataset cleared = new BarDataset();
        ArrayList<Integer> clearedValues = new ArrayList();
        BarDataset endorsed = new BarDataset();
        ArrayList<Integer> endorsedValues = new ArrayList();
        BarDataset approved = new BarDataset();
        ArrayList<Integer> approvedValues = new ArrayList();
        BarDataset forRelease = new BarDataset();
        ArrayList<Integer> forReleaseValues = new ArrayList();
        BarDataset released = new BarDataset();
        ArrayList<Integer> releasedValues = new ArrayList();
        BarOptions options = new BarOptions();

        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("APCP Requests: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int requestedCount = 0;
            int clearedCount = 0;
            int endorsedCount = 0;
            int approvedCount = 0;
            int forReleaseCount = 0;
            int releasedCount = 0;

            for (ARBO arbo : arboList) {

                arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);

                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getRequestStatus() == 1) { // requested
                                requestedCount++;
                            } else if (r.getRequestStatus() == 2) { // cleared
                                clearedCount++;
                            } else if (r.getRequestStatus() == 3) { // endorsed
                                endorsedCount++;
                            } else if (r.getRequestStatus() == 4) { // approved
                                approvedCount++;
                            } else if (r.getRequestStatus() == 5) { // release
                                releasedCount++;
                            } else if (r.getRequestStatus() == 6) { // forRelease
                                forReleaseCount++;
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getRequestStatus() == 1) { // requested
                                requestedCount++;
                            } else if (r.getRequestStatus() == 2) { // cleared
                                clearedCount++;
                            } else if (r.getRequestStatus() == 3) { // endorsed
                                endorsedCount++;
                            } else if (r.getRequestStatus() == 4) { // approved
                                approvedCount++;
                            } else if (r.getRequestStatus() == 5) { // release
                                releasedCount++;
                            } else if (r.getRequestStatus() == 6) { // forRelease
                                forReleaseCount++;
                            }
                        }
                    }
                }
            }

            requestedValues.add(requestedCount);
            clearedValues.add(clearedCount);
            endorsedValues.add(endorsedCount);
            approvedValues.add(approvedCount);
            forReleaseValues.add(forReleaseCount);
            releasedValues.add(releasedCount);

            data.addLabel(labels.get(i));

        }

        for (int value : requestedValues) {
            requested.addData(value);
            requested.addBackgroundColor(Color.RED);
            requested.setLabel("REQUESTED");
        }

        for (int value : clearedValues) {
            cleared.addData(value);
            cleared.addBackgroundColor(Color.ORANGE);
            cleared.setLabel("CLEARED");
        }

        for (int value : endorsedValues) {
            endorsed.addData(value);
            endorsed.addBackgroundColor(Color.YELLOW);
            endorsed.setLabel("ENDORSED");
        }

        for (int value : approvedValues) {
            approved.addData(value);
            approved.addBackgroundColor(Color.GREEN);
            approved.setLabel("APPROVED");
        }

        for (int value : forReleaseValues) {
            forRelease.addData(value);
            forRelease.addBackgroundColor(Color.BLUE);
            forRelease.setLabel("FOR RELEASE");
        }

        for (int value : releasedValues) {
            released.addData(value);
            released.addBackgroundColor(Color.PURPLE);
            released.setLabel("RELEASED");
        }

        data.addDataset(requested);
        data.addDataset(cleared);
        data.addDataset(endorsed);
        data.addDataset(approved);
        data.addDataset(forRelease);
        data.addDataset(released);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getStackedBarChartApprovalRate(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        ArrayList<String> labels = new ArrayList();
        boolean b = true;
        APCPRequestDAO dao = new APCPRequestDAO();
        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        BarDataset requested = new BarDataset();
        ArrayList<Integer> requestedValues = new ArrayList();

        BarDataset completed = new BarDataset();
        ArrayList<Integer> completedValues = new ArrayList();

        BarDataset cleared = new BarDataset();
        ArrayList<Integer> clearedValues = new ArrayList();
        BarDataset endorsed = new BarDataset();
        ArrayList<Integer> endorsedValues = new ArrayList();
        BarDataset approved = new BarDataset();
        ArrayList<Integer> approvedValues = new ArrayList();
        BarDataset forRelease = new BarDataset();
        ArrayList<Integer> forReleaseValues = new ArrayList();

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Approval Rate: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int requestedCount = 0;
            int clearedCount = 0;
            int endorsedCount = 0;
            int approvedCount = 0;
            int forReleaseCount = 0;

            int requestedDays = 0;
            int completedDays = 0;
            int clearedDays = 0;
            int endorsedDays = 0;
            int approvedDays = 0;
            int forReleaseDays = 0;

            for (ARBO arbo : arboList) {
                arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            Calendar startDate = Calendar.getInstance();
                            Calendar endDate = Calendar.getInstance();

                            if (r.getDateRequested() != null && r.getDateCompleted() != null && r.getDateCleared() != null) {
                                requestedCount++;

                                startDate.setTime(r.getDateRequested());
                                endDate.setTime(r.getDateCompleted());
                                requestedDays += (int) daysBetween(startDate, endDate);

                                startDate.setTime(r.getDateCompleted());
                                endDate.setTime(r.getDateCleared());
                                completedDays += (int) daysBetween(startDate, endDate);

                            } else if (r.getDateRequested() != null && r.getDateCompleted() == null) {
                                requestedCount++;
                                startDate.setTime(r.getDateRequested());
                                requestedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateCompleted() != null && r.getDateCleared() == null) {
                                requestedCount++;
                                startDate.setTime(r.getDateCompleted());
                                completedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateCleared() != null && r.getDateEndorsed() != null) {
                                clearedCount++;
                                startDate.setTime(r.getDateCleared());
                                clearedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateCleared() != null && r.getDateEndorsed() == null) {
                                clearedCount++;
                                startDate.setTime(r.getDateCleared());
                                clearedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateEndorsed() != null && r.getDateApproved() != null) {
                                endorsedCount++;
                                startDate.setTime(r.getDateEndorsed());
                                endorsedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateEndorsed() != null && r.getDateApproved() == null) {
                                endorsedCount++;
                                startDate.setTime(r.getDateEndorsed());
                                endorsedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateApproved() != null && r.getDateFirstReleasedPerRequest() != null) {
                                approvedCount++;
                                startDate.setTime(r.getDateApproved());
                                approvedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateApproved() != null && r.getDateFirstReleasedPerRequest() == null) {
                                approvedCount++;
                                startDate.setTime(r.getDateApproved());
                                approvedDays += (int) daysBetween(startDate, current);
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            Calendar startDate = Calendar.getInstance();
                            Calendar endDate = Calendar.getInstance();

                            if (r.getDateRequested() != null && r.getDateCleared() != null) {
                                requestedCount++;

                                startDate.setTime(r.getDateRequested());
                                endDate.setTime(r.getDateCompleted());
                                requestedDays += (int) daysBetween(startDate, endDate);

                                startDate.setTime(r.getDateCompleted());
                                endDate.setTime(r.getDateCleared());
                                completedDays += (int) daysBetween(startDate, endDate);

                            } else if (r.getDateRequested() != null && r.getDateCleared() == null) {
                                requestedCount++;
                                startDate.setTime(r.getDateRequested());
                                requestedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateCleared() != null && r.getDateEndorsed() != null) {
                                clearedCount++;
                                startDate.setTime(r.getDateCleared());
                                clearedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateCleared() != null && r.getDateEndorsed() == null) {
                                clearedCount++;
                                startDate.setTime(r.getDateCleared());
                                clearedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateEndorsed() != null && r.getDateApproved() != null) {
                                endorsedCount++;
                                startDate.setTime(r.getDateEndorsed());
                                endorsedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateEndorsed() != null && r.getDateApproved() == null) {
                                endorsedCount++;
                                startDate.setTime(r.getDateEndorsed());
                                endorsedDays += (int) daysBetween(startDate, current);
                            }

                            if (r.getDateApproved() != null && r.getDateFirstReleasedPerRequest() != null) {
                                approvedCount++;
                                startDate.setTime(r.getDateApproved());
                                approvedDays += (int) daysBetween(startDate, current);
                            } else if (r.getDateApproved() != null && r.getDateFirstReleasedPerRequest() == null) {
                                approvedCount++;
                                startDate.setTime(r.getDateApproved());
                                approvedDays += (int) daysBetween(startDate, current);
                            }
                        }
                    }
                }
            }
            System.out.println(requestedDays);
            if (requestedCount > 0) {
                requestedValues.add(requestedDays / requestedCount);
                completedValues.add(completedDays / requestedCount);
            }
            if (clearedCount > 0) {
                clearedValues.add(clearedDays / clearedCount);
            }
            if (endorsedCount > 0) {
                endorsedValues.add(endorsedDays / endorsedCount);
            }
            if (approvedCount > 0) {
                approvedValues.add(approvedDays / approvedCount);
            }

            data.addLabel(labels.get(i));

        }

        for (int value : requestedValues) {
            completed.addData(value);
            completed.addBackgroundColor(Color.random());
            completed.setLabel("REQUESTED");
        }

        for (int value : completedValues) {
            requested.addData(value);
            requested.addBackgroundColor(Color.random());
            requested.setLabel("COMPLETED");
        }

        for (int value : clearedValues) {
            cleared.addData(value);
            cleared.addBackgroundColor(Color.random());
            cleared.setLabel("CLEARED");
        }

        for (int value : endorsedValues) {
            endorsed.addData(value);
            endorsed.addBackgroundColor(Color.random());
            endorsed.setLabel("ENDORSED");
        }

        for (int value : approvedValues) {
            approved.addData(value);
            approved.addBackgroundColor(Color.random());
            approved.setLabel("APPROVED");
        }

        for (int value : forReleaseValues) {
            forRelease.addData(value);
            forRelease.addBackgroundColor(Color.random());
            forRelease.setLabel("RELEASED");
        }

        data.addDataset(completed);
        data.addDataset(requested);
        data.addDataset(cleared);
        data.addDataset(endorsed);
        data.addDataset(approved);
//        data.addDataset(forRelease);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getBarChartLoanType(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        ArrayList<String> labels = new ArrayList();
        boolean b = true;

        BarOptions options = new BarOptions();
        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Loan Type Availability: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        APCPRequestDAO dao = new APCPRequestDAO();

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        BarDataset cropProd = new BarDataset();
        BarDataset live = new BarDataset();
        ArrayList<Integer> cropProdValues = new ArrayList();
        ArrayList<Integer> liveValues = new ArrayList();

        for (int i = 0; i < labels.size(); i++) {
            int total = 0;
            int total1 = 0;
            for (ARBO arbo : arboList) {

                arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);

                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getApcpType() == 1) { // crop prod
                                total++;
                            } else { // livelihood
                                total1++;
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getApcpType() == 1) { // crop prod
                                total++;
                            } else { // livelihood
                                total1++;
                            }
                        }
                    }
                }
            }
            cropProdValues.add(total);
            liveValues.add(total1);
            data.addLabel(labels.get(i));
        }

        for (int value : cropProdValues) {
            cropProd.addData(value);
            cropProd.addBackgroundColor(Color.DARK_OLIVE_GREEN);
            cropProd.setLabel("CROP PRODUCTION");
        }

        for (int value : liveValues) {
            live.addData(value);
            live.addBackgroundColor(Color.RED);
            live.setLabel("LIVELIHOOD");
        }

        data.addDataset(cropProd);
        data.addDataset(live);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getBarChartLoanTerm(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        ArrayList<String> labels = new ArrayList();
        boolean b = true;
        APCPRequestDAO dao = new APCPRequestDAO();

        BarOptions options = new BarOptions();
        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Loan Term Availability: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        BarDataset annualDS = new BarDataset();
        BarDataset plantationDS = new BarDataset();
        BarDataset capitalDS = new BarDataset();
        BarDataset fixedAssetDS = new BarDataset();
        ArrayList<Integer> annualValues = new ArrayList();
        ArrayList<Integer> plantationValues = new ArrayList();
        ArrayList<Integer> capitalValues = new ArrayList();
        ArrayList<Integer> fixedAssetValues = new ArrayList();

        for (int i = 0; i < labels.size(); i++) {
            int annual = 0;
            int plantation = 0;
            int capital = 0;
            int fixedAsset = 0;

            for (ARBO arbo : arboList) {
                arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getLoanReason().getLoanReasonDesc() != null) {
                                if (r.getLoanReason().getLoanTermID() == 1) { // Semi-Annual / Annual
                                    annual++;
                                } else if (r.getLoanReason().getLoanTermID() == 2) { // Plantation
                                    plantation++;
                                } else if (r.getLoanReason().getLoanTermID() == 3) { // Working Capital
                                    capital++;
                                } else if (r.getLoanReason().getLoanTermID() == 4) { // Fixed Asset Acquisition
                                    fixedAsset++;
                                }
                            }

                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getLoanReason().getLoanReasonDesc() != null) {
                                if (r.getLoanReason().getLoanTermID() == 1) { // Semi-Annual / Annual
                                    annual++;
                                } else if (r.getLoanReason().getLoanTermID() == 2) { // Plantation
                                    plantation++;
                                } else if (r.getLoanReason().getLoanTermID() == 3) { // Working Capital
                                    capital++;
                                } else if (r.getLoanReason().getLoanTermID() == 4) { // Fixed Asset Acquisition
                                    fixedAsset++;
                                }
                            }

                        }
                    }
                }
            }
            annualValues.add(annual);
            plantationValues.add(plantation);
            capitalValues.add(capital);
            fixedAssetValues.add(fixedAsset);
            data.addLabel(labels.get(i));
        }

        for (int value : annualValues) {
            annualDS.addData(value);
            annualDS.addBackgroundColor(Color.RED);
            annualDS.setLabel("SEMI-ANNUAL/ANNUAL CROPS");
        }

        for (int value : plantationValues) {
            plantationDS.addData(value);
            plantationDS.addBackgroundColor(Color.ORANGE);
            plantationDS.setLabel("PLANTATION CROPS");
        }

        for (int value : capitalValues) {
            capitalDS.addData(value);
            capitalDS.addBackgroundColor(Color.YELLOW);
            capitalDS.setLabel("WORKING CAPITAL");
        }

        for (int value : fixedAssetValues) {
            fixedAssetDS.addData(value);
            fixedAssetDS.addBackgroundColor(Color.GREEN);
            fixedAssetDS.setLabel("FIXED ASSET ACQUISITION");
        }

        data.addDataset(annualDS);
        data.addDataset(plantationDS);
        data.addDataset(capitalDS);
        data.addDataset(fixedAssetDS);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getStackedBarChartLoanReason(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<String> labels = new ArrayList();
        ArrayList<LoanReason> refLoanReasons = requestDAO.getAllLoanReasons();
        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Loan Reasons: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();
        for (LoanReason reason : refLoanReasons) {
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(reason.getLoanReasonDesc()); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar
            for (String label : labels) { // REGIONS/PROVINCES
                int count = 0;
                for (ARBO arbo : arboList) {

                    arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                    ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);

                    if (b) { // REGIONAL
                        if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                            for (APCPRequest r : filtered) {
                                if (r.getLoanReason().getLoanReasonDesc() != null) {
                                    if (r.getLoanReason().getLoanReasonDesc().equals(reason.getLoanReasonDesc())) {
                                        count++;
                                    }
                                }

                            }
                        }
                    } else { // PROVINCIAL OFFICE
                        if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                            for (APCPRequest r : filtered) {
                                if (r.getLoanReason().getLoanReasonDesc() != null) {
                                    if (r.getLoanReason().getLoanReasonDesc().equals(reason.getLoanReasonDesc())) {
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
                if (count > 0) {
                    dataset.addData(count);
                }
            }
            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }

        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();

    }

//    public String getBarChartAPCPAmounts(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {
//
//        ArrayList<String> labels = new ArrayList();
//        boolean b = true;
//
//        if (regionList.isEmpty()) {
//            labels.add("REGION I (ILOCOS REGION)");
//            labels.add("REGION II (CAGAYAN VALLEY)");
//            labels.add("REGION III (CENTRAL LUZON)");
//            labels.add("REGION IV-A (CALABARZON)");
//            labels.add("REGION IV-B (MIMAROPA)");
//            labels.add("REGION V (BICOL REGION)");
//            labels.add("REGION VI (WESTERN VISAYAS)");
//            labels.add("REGION VII (CENTRAL VISAYAS)");
//            labels.add("REGION VIII (EASTERN VISAYAS)");
//            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
//            labels.add("REGION X (NORTHERN MINDANAO)");
//            labels.add("REGION XI (DAVAO REGION)");
//            labels.add("REGION XII (SOCCSKSARGEN)");
//            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
//            labels.add("REGION XIII (Caraga)");
//            System.out.println("REGION EMPTY!");
//        } else if (provList.isEmpty()) {
//            for (Region r : regionList) {
//                labels.add(r.getRegDesc());
//            }
//
//        } else {
//            for (Province p : provList) {
//                labels.add(p.getProvDesc());
//            }
//            b = false;
//        }
//
//        BarData data = new BarData();
//
//        BarDataset one = new BarDataset();
//        BarDataset two = new BarDataset();
//        BarDataset three = new BarDataset();
//        BarDataset four = new BarDataset();
//        ArrayList<Integer> aa = new ArrayList();
//        ArrayList<Integer> bb = new ArrayList();
//        ArrayList<Integer> cc = new ArrayList();
//        ArrayList<Integer> dd = new ArrayList();
//
//        APCPRequestDAO dao = new APCPRequestDAO();
//
//        Calendar current = Calendar.getInstance();
//        int year = current.get(Calendar.YEAR);
//
//        for (int i = 0; i < labels.size(); i++) {
//            int a1 = 0;
//            int a2 = 0;
//            int a3 = 0;
//            int a4 = 0;
//            for (ARBO arbo : arboList) {
//                if (b) { // REGIONAL
//                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
//                        a1 += dao.getTotalPastDueAmount(arbo.getRequestList());
//                        a2 += dao.getYearlySumOfReleasesByRequestId(arbo.getRequestList(), year);
//                        a3 += dao.getSumOfAccumulatedReleasesByRequestId(arbo.getRequestList());
//                        a4 += dao.getTotalApprovedAmount(arbo.getRequestList());
//                    }
//                } else { // PROVINCIAL OFFICE
//                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
//                        a1 += dao.getTotalPastDueAmount(arbo.getRequestList());
//                        a2 += dao.getYearlySumOfReleasesByRequestId(arbo.getRequestList(), year);
//                        a3 += dao.getSumOfAccumulatedReleasesByRequestId(arbo.getRequestList());
//                        a4 += dao.getTotalApprovedAmount(arbo.getRequestList());
//                    }
//                }
//            }
//            aa.add(a1);
//            bb.add(a2);
//            cc.add(a3);
//            dd.add(a4);
//        }
//
//        for (int value : aa) {
//            one.addData(value);
//            one.addBackgroundColor(Color.random());
//            one.setLabel("TOTAL PAST DUE AMOUNT");
//        }
//
//        for (int value : bb) {
//            two.addData(value);
//            two.addBackgroundColor(Color.random());
//            two.setLabel("YEARLY RELEASES");
//        }
//
//        for (int value : cc) {
//            three.addData(value);
//            three.addBackgroundColor(Color.random());
//            three.setLabel("ACCUMULATED RELEASES");
//        }
//
//        for (int value : dd) {
//            four.addData(value);
//            four.addBackgroundColor(Color.random());
//            four.setLabel("APPROVED AMOUNT");
//        }
//
//        data.addDataset(one);
//        data.addDataset(two);
//        data.addDataset(three);
//        data.addDataset(four);
//
//        return new BarChart(data).toJson();
//
//    }
    public String getStackedBarChartPastDue(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<String> labels = new ArrayList();
        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Past Due Amounts: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        if (regionList.isEmpty()) { // REGION parameter is empty
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
        } else if (provList.isEmpty()) { // PROVINCE parameter is empty
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();

        for (String label : labels) { // REGIONS/PROVINCES

            double sum = 0;
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(label); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar

            for (ARBO arbo : arboList) {

                arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);

                if (b) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            sum += r.getTotalPDAAmountPerRequest();
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            sum += r.getTotalPDAAmountPerRequest();
                        }
                    }
                }
            }
            if (sum > 0) {
                dataset.addData(sum);
            }

            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }
        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getStackedBarChartApprovedAmounts(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<String> labels = new ArrayList();
        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        SimpleDateFormat f = new SimpleDateFormat("MMMMM dd, yyyy");
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Approved Amounts: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        if (regionList.isEmpty()) { // REGION parameter is empty
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
        } else if (provList.isEmpty()) { // PROVINCE parameter is empty
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
            System.out.println("");
        }

        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();

        for (String label : labels) { // REGIONS/PROVINCES

            double sum = 0;
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(label); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar

            for (ARBO arbo : arboList) {

                arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                ArrayList<APCPRequest> filtered = filterAPCPByDate(arbo.getRequestList(), start, end);

                if (b) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getRequestStatus() == 1 || r.getRequestStatus() == 2 || r.getRequestStatus() == 3 || r.getRequestStatus() == 4) {
                                sum += r.getLoanAmount();
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : filtered) {
                            if (r.getRequestStatus() == 1 || r.getRequestStatus() == 2 || r.getRequestStatus() == 3 || r.getRequestStatus() == 4) {
                                sum += r.getLoanAmount();
                            }
                        }
                    }
                }
            }
            if (sum > 0) {
                dataset.addData(sum);
            }

            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }
        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    // </editor-fold>
    //<editor-fold desc="CAPDEV REPORTS">
    // CAPDEV Plan Status
    public String getStackedBarChartCAPDEVPlans(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        ArrayList<String> labels = new ArrayList();
        boolean b = true;

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;
        }

        BarData data = new BarData();

        BarDataset pending = new BarDataset();
        ArrayList<Integer> pendingValues = new ArrayList();
        BarDataset approved = new BarDataset();
        ArrayList<Integer> approvedValues = new ArrayList();
        BarDataset assigned = new BarDataset();
        ArrayList<Integer> assignedValues = new ArrayList();
        BarDataset implemented = new BarDataset();
        ArrayList<Integer> implementedValues = new ArrayList();

        BarOptions options = new BarOptions();

        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("CAPDEV Plans: " + f.format(start) + " - " + f.format(end));

        options.setTitle(title);

        APCPRequestDAO dao = new APCPRequestDAO();
        CAPDEVDAO dao2 = new CAPDEVDAO();

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int pendingCount = 0;
            int approvedCount = 0;
            int assignedCount = 0;
            int implementedCount = 0;

            for (ARBO arbo : arboList) {

                arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));

                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            r.setPlans(dao2.getAllCAPDEVPlanByRequest(r.getRequestID()));
                            ArrayList<CAPDEVPlan> filtered = filterCAPDEVByDate(r.getPlans(), start, end);
                            for (CAPDEVPlan c : filtered) {
                                if (c.getPlanStatus() == 1) { // pending
                                    pendingCount++;
                                } else if (c.getPlanStatus() == 2) { // approved
                                    approvedCount++;
                                } else if (c.getPlanStatus() == 4) { // assigned
                                    assignedCount++;
                                } else if (c.getPlanStatus() == 5) { // implemented
                                    implementedCount++;
                                }
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            r.setPlans(dao2.getAllCAPDEVPlanByRequest(r.getRequestID()));
                            ArrayList<CAPDEVPlan> filtered = filterCAPDEVByDate(r.getPlans(), start, end);
                            for (CAPDEVPlan c : filtered) {
                                if (c.getPlanStatus() == 1) { // pending
                                    pendingCount++;
                                } else if (c.getPlanStatus() == 2) { // approved
                                    approvedCount++;
                                } else if (c.getPlanStatus() == 4) { // assigned
                                    assignedCount++;
                                } else if (c.getPlanStatus() == 5) { // implemented
                                    implementedCount++;
                                }
                            }
                        }
                    }
                }
            }

            pendingValues.add(pendingCount);
            approvedValues.add(approvedCount);
            assignedValues.add(assignedCount);
            implementedValues.add(implementedCount);

            data.addLabel(labels.get(i));

        }

        for (int value : pendingValues) {
            pending.addData(value);
            pending.addBackgroundColor(Color.random());
            pending.setLabel("PENDING");
        }

        for (int value : approvedValues) {
            approved.addData(value);
            approved.addBackgroundColor(Color.random());
            approved.setLabel("APPROVED");
        }

        for (int value : assignedValues) {
            assigned.addData(value);
            assigned.addBackgroundColor(Color.random());
            assigned.setLabel("ASSIGNED");
        }

        for (int value : implementedValues) {
            implemented.addData(value);
            implemented.addBackgroundColor(Color.random());
            implemented.setLabel("IMPLEMENTED");
        }

        data.addDataset(pending);
        data.addDataset(approved);
        data.addDataset(assigned);
        data.addDataset(implemented);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    // Frequency of Activity Types
    public String getStackedBarChartActivityTypes(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList, Date start, Date end) {

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        ArrayList<String> labels = new ArrayList();
        ArrayList<CAPDEVActivity> allActivities = capdevDAO.getCAPDEVActivities();

        boolean b = true;

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("CAPDEV Activities: " + f.format(start) + " - " + f.format(end));

        if (regionList.isEmpty()) {
            labels.add("REGION I (ILOCOS REGION)");
            labels.add("REGION II (CAGAYAN VALLEY)");
            labels.add("REGION III (CENTRAL LUZON)");
            labels.add("REGION IV-A (CALABARZON)");
            labels.add("REGION IV-B (MIMAROPA)");
            labels.add("REGION V (BICOL REGION)");
            labels.add("REGION VI (WESTERN VISAYAS)");
            labels.add("REGION VII (CENTRAL VISAYAS)");
            labels.add("REGION VIII (EASTERN VISAYAS)");
            labels.add("REGION IX (ZAMBOANGA PENINSULA)");
            labels.add("REGION X (NORTHERN MINDANAO)");
            labels.add("REGION XI (DAVAO REGION)");
            labels.add("REGION XII (SOCCSKSARGEN)");
            labels.add("CORDILLERA ADMINISTRATIVE REGION (CAR)");
            labels.add("REGION XIII (Caraga)");
            System.out.println("REGION EMPTY!");
        } else if (provList.isEmpty()) {
            for (Region r : regionList) {
                labels.add(r.getRegDesc());
            }
            System.out.println("PROV EMPTY!");
        } else {
            for (Province p : provList) {
                labels.add(p.getProvDesc());
            }
            b = false;

        }

        APCPRequestDAO dao = new APCPRequestDAO();
        CAPDEVDAO dao2 = new CAPDEVDAO();
        BarData data = new BarData();

        ArrayList<BarDataset> datasets = new ArrayList();
        for (CAPDEVActivity activity : allActivities) {
            BarDataset dataset = new BarDataset(); // initializes
            dataset.setLabel(activity.getActivityName()); // ONE color
            dataset.setBackgroundColor(Color.random()); // ONE bar
            for (String label : labels) { // REGIONS/PROVINCES
                int count = 0;
                for (ARBO arbo : arboList) {
                    arbo.setRequestList(dao.getAllARBORequests(arbo.getArboID()));
                    if (b) { // REGIONAL
                        if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                            for (APCPRequest r : arbo.getRequestList()) {
                                r.setPlans(dao2.getAllCAPDEVPlanByRequest(r.getRequestID()));
                                ArrayList<CAPDEVPlan> filtered = filterCAPDEVByDate(r.getPlans(), start, end);
                                for (CAPDEVPlan p : filtered) {
                                    if (p.getPlanStatus() == 5) { // IMPLEMENTED
                                        for (CAPDEVActivity planActivity : p.getActivities()) {
                                            if (planActivity.getActivityName() != null) { // NOT NULL/HAS VALUE
                                                if (planActivity.getActivityName().equals(activity.getActivityName())) { // IF MATCH
                                                    count++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else { // PROVINCIAL OFFICE
                        if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                            for (APCPRequest r : arbo.getRequestList()) {
                                r.setPlans(dao2.getAllCAPDEVPlanByRequest(r.getRequestID()));
                                ArrayList<CAPDEVPlan> filtered = filterCAPDEVByDate(r.getPlans(), start, end);
                                for (CAPDEVPlan p : filtered) {
                                    if (p.getPlanStatus() == 5) { // IMPLEMENTED
                                        for (CAPDEVActivity planActivity : p.getActivities()) {
                                            if (planActivity.getActivityName() != null) { // NOT NULL/HAS VALUE
                                                if (planActivity.getActivityName().equals(activity.getActivityName())) { // IF MATCH
                                                    count++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (count > 0) {
                    dataset.addData(count);
                }
            }
            if (dataset.getData().size() > 0) {
                datasets.add(dataset);
            }

        }

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();

    }
    //</editor-fold>

    //<editor-fold desc="DASHBOARDS">
    //APCP
    public String getStackedBarChartAPCPOnTrackDelayedByStatus(ArrayList<ARBO> arboList, int status, int userType, String titleStr) {

        ArrayList<String> labels = new ArrayList();
        APCPRequestDAO requestDAO = new APCPRequestDAO();

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        // SETTING OF LABELS
        if (userType == 3) { // PROVINCIAL
            if (!arboList.isEmpty()) {
                labels.add(arboList.get(0).getProvOfficeCodeDesc());
            }
        } else if (userType == 2) { // REGIONAL
            if (!arboList.isEmpty()) {
                for (ARBO arbo : arboList) {
                    if (!labels.contains(arbo.getProvOfficeCodeDesc())) {
                        labels.add(arbo.getProvOfficeCodeDesc());
                    }
                }
            }
        } else if (userType == 1) { // CENTRAL
            if (!arboList.isEmpty()) {
                for (ARBO arbo : arboList) {
                    if (!labels.contains(arbo.getArboRegionDesc())) {
                        labels.add(arbo.getArboRegionDesc());
                    }
                }
            }
        }

        BarDataset onTrack = new BarDataset();
        onTrack.setLabel("ON TRACK");
        onTrack.setBackgroundColor(Color.GREEN);

        BarDataset delayed = new BarDataset();
        delayed.setLabel("DELAYED");
        delayed.setBackgroundColor(Color.RED);

        for (String label : labels) {
            int onTrackCount = 0;
            int delayedCount = 0;
            for (ARBO arbo : arboList) {
                arbo.setRequestList(requestDAO.getAllARBORequests(arbo.getArboID()));
                if (userType == 3) { // PROVINCIAL
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) {
                        onTrackCount += requestDAO.getOnTrackRequestsPerStatus(arbo.getRequestList(), status);
                        delayedCount += requestDAO.getDelayedRequestsPerStatus(arbo.getRequestList(), status);
                    }
                } else if (userType == 2) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) {
                        onTrackCount += requestDAO.getOnTrackRequestsPerStatus(arbo.getRequestList(), status);
                        delayedCount += requestDAO.getDelayedRequestsPerStatus(arbo.getRequestList(), status);
                    }
                } else if (userType == 1) { // CENTRAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) {
                        onTrackCount += requestDAO.getOnTrackRequestsPerStatus(arbo.getRequestList(), status);
                        delayedCount += requestDAO.getDelayedRequestsPerStatus(arbo.getRequestList(), status);
                    }
                }
            }
            onTrack.addData(onTrackCount);
            delayed.addData(delayedCount);
        }

        BarData data = new BarData();
        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(onTrack);
        datasets.add(delayed);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();
    }

    public String getLineChartAPCPTotalARBOs(ArrayList<APCPRequest> requestList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        APCPRequestDAO dao = new APCPRequestDAO();

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar releaseDate = Calendar.getInstance();

        int currentCount = 0;
        int lastCount = 0;

        ArrayList<Integer> currentARBOs = new ArrayList();
        ArrayList<Integer> lastARBOs = new ArrayList();

        for (String label : labels) {
            for (APCPRequest req : requestList) {
                for (APCPRelease rel : dao.getAllAPCPReleasesByRequest(req.getRequestID())) {
                    releaseDate.setTime(rel.getReleaseDate());
                    if (label.equalsIgnoreCase("Q1")) {
                        if (releaseDate.get(Calendar.MONTH) == 0 || releaseDate.get(Calendar.MONTH) == 1 || releaseDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBOs.contains(req.getArboID())) {
                                    lastARBOs.add(req.getArboID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q2")) {
                        if (releaseDate.get(Calendar.MONTH) == 3 || releaseDate.get(Calendar.MONTH) == 4 || releaseDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q2
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q2
                                }
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBOs.contains(req.getArboID())) {
                                    lastARBOs.add(req.getArboID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q3")) {
                        if (releaseDate.get(Calendar.MONTH) == 6 || releaseDate.get(Calendar.MONTH) == 7 || releaseDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q3
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q3
                                }
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBOs.contains(req.getArboID())) {
                                    lastARBOs.add(req.getArboID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q4")) {
                        if (releaseDate.get(Calendar.MONTH) == 9 || releaseDate.get(Calendar.MONTH) == 10 || releaseDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q4
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBOs.contains(req.getArboID())) {
                                    lastARBOs.add(req.getArboID());
                                    lastCount++;
                                }
                            }
                        }
                    }
                }
            }
            currentYear.addData(currentCount);
            lastYear.addData(lastCount);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartAPCPTotalARBs(ArrayList<APCPRequest> requestList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar disbursementDate = Calendar.getInstance();

        int currentCount = 0;
        int lastCount = 0;

        APCPRequestDAO dao = new APCPRequestDAO();

        ArrayList<Integer> currentARBs = new ArrayList();
        ArrayList<Integer> lastARBs = new ArrayList();

        for (String label : labels) {
            for (APCPRequest req : requestList) {
                for (Disbursement d : dao.getAllDisbursementsByRequest(req.getRequestID())) {
                    disbursementDate.setTime(d.getDateDisbursed());
                    if (label.equalsIgnoreCase("Q1")) {
                        if (disbursementDate.get(Calendar.MONTH) == 0 || disbursementDate.get(Calendar.MONTH) == 1 || disbursementDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                            if (disbursementDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(d.getArbID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(d.getArbID()); // ADDS THE ARB ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (disbursementDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(d.getArbID())) {
                                    lastARBs.add(d.getArbID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q2")) {
                        if (disbursementDate.get(Calendar.MONTH) == 3 || disbursementDate.get(Calendar.MONTH) == 4 || disbursementDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q2
                            if (disbursementDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(d.getArbID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(d.getArbID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q2
                                }
                            } else if (disbursementDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(d.getArbID())) {
                                    lastARBs.add(d.getArbID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q3")) {
                        if (disbursementDate.get(Calendar.MONTH) == 6 || disbursementDate.get(Calendar.MONTH) == 7 || disbursementDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q3
                            if (disbursementDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(d.getArbID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(d.getArbID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q3
                                }
                            } else if (disbursementDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(d.getArbID())) {
                                    lastARBs.add(d.getArbID());
                                    lastCount++;
                                }
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q4")) {
                        if (disbursementDate.get(Calendar.MONTH) == 9 || disbursementDate.get(Calendar.MONTH) == 10 || disbursementDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q4
                            if (disbursementDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(d.getArbID())) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(d.getArbID()); // ADDS THE ARBO ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (disbursementDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(d.getArbID())) {
                                    lastARBs.add(d.getArbID());
                                    lastCount++;
                                }
                            }
                        }
                    }
                }
            }
            currentYear.addData(currentCount);
            lastYear.addData(lastCount);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartTotalReleasedAmount(ArrayList<APCPRequest> requestList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar releaseDate = Calendar.getInstance();

        double currentSum = 0;
        double lastSum = 0;

        APCPRequestDAO dao = new APCPRequestDAO();

        ArrayList<Integer> currentARBOs = new ArrayList();
        ArrayList<Integer> lastARBOs = new ArrayList();

        for (String label : labels) {
            for (APCPRequest req : requestList) {
                for (APCPRelease rel : dao.getAllAPCPReleasesByRequest(req.getRequestID())) {
                    releaseDate.setTime(rel.getReleaseDate());
                    if (label.equalsIgnoreCase("Q1")) {
                        if (releaseDate.get(Calendar.MONTH) == 0 || releaseDate.get(Calendar.MONTH) == 1 || releaseDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += rel.getReleaseAmount();
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += rel.getReleaseAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q2")) {
                        if (releaseDate.get(Calendar.MONTH) == 3 || releaseDate.get(Calendar.MONTH) == 4 || releaseDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q2
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += rel.getReleaseAmount();
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += rel.getReleaseAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q3")) {
                        if (releaseDate.get(Calendar.MONTH) == 6 || releaseDate.get(Calendar.MONTH) == 7 || releaseDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q3
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += rel.getReleaseAmount();
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += rel.getReleaseAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q4")) {
                        if (releaseDate.get(Calendar.MONTH) == 9 || releaseDate.get(Calendar.MONTH) == 10 || releaseDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q4
                            if (releaseDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += rel.getReleaseAmount();
                            } else if (releaseDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += rel.getReleaseAmount();
                            }
                        }
                    }
                }
            }
            currentYear.addData(currentSum);
            lastYear.addData(lastSum);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartTotalPastDueAmount(ArrayList<APCPRequest> requestList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar dateRecorded = Calendar.getInstance();

        double currentSum = 0;
        double lastSum = 0;

        APCPRequestDAO dao = new APCPRequestDAO();

        ArrayList<Integer> currentARBOs = new ArrayList();
        ArrayList<Integer> lastARBOs = new ArrayList();

        for (String label : labels) {
            for (APCPRequest req : requestList) {
                req.setUnsettledPastDueAccounts(dao.getAllUnsettledPastDueAccountsByRequest(req.getRequestID()));
                if (!req.getUnsettledPastDueAccounts().isEmpty()) {
                    for (PastDueAccount pda : req.getUnsettledPastDueAccounts()) {
                        dateRecorded.setTime(pda.getDateRecorded());
                        if (label.equalsIgnoreCase("Q1")) {
                            if (dateRecorded.get(Calendar.MONTH) == 0 || dateRecorded.get(Calendar.MONTH) == 1 || dateRecorded.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                                if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                    currentSum += pda.getPastDueAmount();
                                } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                    lastSum += pda.getPastDueAmount();
                                }
                            }
                        } else if (label.equalsIgnoreCase("Q2")) {
                            if (dateRecorded.get(Calendar.MONTH) == 3 || dateRecorded.get(Calendar.MONTH) == 4 || dateRecorded.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q1
                                if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                    currentSum += pda.getPastDueAmount();
                                } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                    lastSum += pda.getPastDueAmount();
                                }
                            }
                        } else if (label.equalsIgnoreCase("Q3")) {
                            if (dateRecorded.get(Calendar.MONTH) == 6 || dateRecorded.get(Calendar.MONTH) == 7 || dateRecorded.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q1
                                if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                    currentSum += pda.getPastDueAmount();
                                } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                    lastSum += pda.getPastDueAmount();
                                }
                            }
                        } else if (label.equalsIgnoreCase("Q4")) {
                            if (dateRecorded.get(Calendar.MONTH) == 9 || dateRecorded.get(Calendar.MONTH) == 10 || dateRecorded.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q1
                                if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                    currentSum += pda.getPastDueAmount();
                                } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                    lastSum += pda.getPastDueAmount();
                                }
                            }
                        }
                    }
                }

            }
            currentYear.addData(currentSum);
            lastYear.addData(lastSum);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    //CAPDEV
    public String getStackedBarChartCAPDEVOnTrackDelayedByStatus(ArrayList<ARBO> arboList, int status, int userType, String titleStr) {

        ArrayList<String> labels = new ArrayList();

        CAPDEVDAO capdevDAO = new CAPDEVDAO();
        APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        // SETTING OF LABELS
        if (userType == 3) { // PROVINCIAL
            if (!arboList.isEmpty()) {
                labels.add(arboList.get(0).getProvOfficeCodeDesc());
            }
        } else if (userType == 2) { // REGIONAL
            if (!arboList.isEmpty()) {
                for (ARBO arbo : arboList) {
                    if (!labels.contains(arbo.getProvOfficeCodeDesc())) {
                        labels.add(arbo.getProvOfficeCodeDesc());
                    }
                }
            }
        } else if (userType == 1) { // CENTRAL
            if (!arboList.isEmpty()) {
                for (ARBO arbo : arboList) {
                    if (!labels.contains(arbo.getArboRegionDesc())) {
                        labels.add(arbo.getArboRegionDesc());
                    }
                }
            }
        }

        BarDataset onTrack = new BarDataset();
        onTrack.setLabel("ON TRACK");
        onTrack.setBackgroundColor(Color.GREEN);

        BarDataset delayed = new BarDataset();
        delayed.setLabel("DELAYED");
        delayed.setBackgroundColor(Color.RED);

        for (String label : labels) {
            int onTrackCount = 0;
            int delayedCount = 0;
            for (ARBO arbo : arboList) {
                arbo.setRequestList(apcpRequestDAO.getAllARBORequests(arbo.getArboID()));
                if (userType == 3) { // PROVINCIAL
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) {
                        for (APCPRequest req : arbo.getRequestList()) {
                            req.setPlans(capdevDAO.getAllCAPDEVPlanByRequest(req.getRequestID()));
                            onTrackCount += capdevDAO.getOnTrackPlansPerStatus(req.getPlans(), status);
                            delayedCount += capdevDAO.getDelayedPlansPerStatus(req.getPlans(), status);
                        }
                    }
                } else if (userType == 2) { // REGIONAL
                    if (label.equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) {
                        for (APCPRequest req : arbo.getRequestList()) {
                            req.setPlans(capdevDAO.getAllCAPDEVPlanByRequest(req.getRequestID()));
                            onTrackCount += capdevDAO.getOnTrackPlansPerStatus(req.getPlans(), status);
                            delayedCount += capdevDAO.getDelayedPlansPerStatus(req.getPlans(), status);
                        }
                    }
                } else if (userType == 1) { // CENTRAL
                    if (label.equalsIgnoreCase(arbo.getArboRegionDesc())) {
                        for (APCPRequest req : arbo.getRequestList()) {
                            req.setPlans(capdevDAO.getAllCAPDEVPlanByRequest(req.getRequestID()));
                            onTrackCount += capdevDAO.getOnTrackPlansPerStatus(req.getPlans(), status);
                            delayedCount += capdevDAO.getDelayedPlansPerStatus(req.getPlans(), status);
                        }
                    }
                }
            }
            onTrack.addData(onTrackCount);
            delayed.addData(delayedCount);
        }

        BarData data = new BarData();
        ArrayList<BarDataset> datasets = new ArrayList();
        datasets.add(onTrack);
        datasets.add(delayed);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new BarChart(data, options).setHorizontal().toJson();
    }

    public String getLineChartCAPDEVTotalARBOs(ArrayList<CAPDEVPlan> planList, String titleStr) {

        APCPRequestDAO requestDAO = new APCPRequestDAO();
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar implementedDate = Calendar.getInstance();

        int currentCount = 0;
        int lastCount = 0;

        ArrayList<Integer> currentARBOs = new ArrayList();
        ArrayList<Integer> lastARBOs = new ArrayList();

        for (String label : labels) {
            for (CAPDEVPlan plan : planList) {
                implementedDate.setTime(plan.getImplementedDate());
                if (label.equalsIgnoreCase("Q1")) {
                    if (implementedDate.get(Calendar.MONTH) == 0 || implementedDate.get(Calendar.MONTH) == 1 || implementedDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1

                        APCPRequest req = requestDAO.getRequestByID(plan.getRequestID());

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastARBOs.contains(req.getArboID())) {
                                lastARBOs.add(req.getArboID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q2")) {
                    if (implementedDate.get(Calendar.MONTH) == 3 || implementedDate.get(Calendar.MONTH) == 4 || implementedDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q1

                        APCPRequest req = requestDAO.getRequestByID(plan.getRequestID());

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastARBOs.contains(req.getArboID())) {
                                lastARBOs.add(req.getArboID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q3")) {
                    if (implementedDate.get(Calendar.MONTH) == 6 || implementedDate.get(Calendar.MONTH) == 7 || implementedDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q1

                        APCPRequest req = requestDAO.getRequestByID(plan.getRequestID());

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastARBOs.contains(req.getArboID())) {
                                lastARBOs.add(req.getArboID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q4")) {
                    if (implementedDate.get(Calendar.MONTH) == 9 || implementedDate.get(Calendar.MONTH) == 10 || implementedDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q1

                        APCPRequest req = requestDAO.getRequestByID(plan.getRequestID());

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentARBOs.contains(req.getArboID())) { // IF NOT YET ON THE LIST, ADD
                                currentARBOs.add(req.getArboID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastARBOs.contains(req.getArboID())) {
                                lastARBOs.add(req.getArboID());
                                lastCount++;
                            }
                        }
                    }
                }

            }
            currentYear.addData(currentCount);
            lastYear.addData(lastCount);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartCAPDEVTotalARBs(ArrayList<CAPDEVPlan> planList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar implementedDate = Calendar.getInstance();

        int currentCount = 0;
        int lastCount = 0;

        ArrayList<Integer> currentARBs = new ArrayList();
        ArrayList<Integer> lastARBs = new ArrayList();

        CAPDEVDAO dao = new CAPDEVDAO();

        for (String label : labels) {
            for (CAPDEVPlan plan : planList) {

                implementedDate.setTime(plan.getImplementedDate());
                plan.setActivities(dao.getCAPDEVPlanActivities(plan.getPlanID()));

                if (label.equalsIgnoreCase("Q1")) {
                    if (implementedDate.get(Calendar.MONTH) == 0 || implementedDate.get(Calendar.MONTH) == 1 || implementedDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                        for (int arbID : plan.getAllAttenededParticipants()) {
                            if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(arbID)) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(arbID); // ADDS THE ARB ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(arbID)) {
                                    lastARBs.add(arbID);
                                    lastCount++;
                                }
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q2")) {
                    if (implementedDate.get(Calendar.MONTH) == 3 || implementedDate.get(Calendar.MONTH) == 4 || implementedDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q1
                        for (int arbID : plan.getAllAttenededParticipants()) {
                            if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(arbID)) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(arbID); // ADDS THE ARB ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(arbID)) {
                                    lastARBs.add(arbID);
                                    lastCount++;
                                }
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q3")) {
                    if (implementedDate.get(Calendar.MONTH) == 6 || implementedDate.get(Calendar.MONTH) == 7 || implementedDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q1
                        for (int arbID : plan.getAllAttenededParticipants()) {
                            if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(arbID)) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(arbID); // ADDS THE ARB ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(arbID)) {
                                    lastARBs.add(arbID);
                                    lastCount++;
                                }
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q4")) {
                    if (implementedDate.get(Calendar.MONTH) == 9 || implementedDate.get(Calendar.MONTH) == 10 || implementedDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q1
                        for (int arbID : plan.getAllAttenededParticipants()) {
                            if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                if (!currentARBs.contains(arbID)) { // IF NOT YET ON THE LIST, ADD
                                    currentARBs.add(arbID); // ADDS THE ARB ID
                                    currentCount++; // ADDS THE COUNT FOR Q1
                                }
                            } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                if (!lastARBs.contains(arbID)) {
                                    lastARBs.add(arbID);
                                    lastCount++;
                                }
                            }
                        }
                    }
                }

            }
            currentYear.addData(currentCount);
            lastYear.addData(lastCount);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartCAPDEVTotalImplementedPlans(ArrayList<CAPDEVPlan> planList, String titleStr) {

        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar implementedDate = Calendar.getInstance();

        int currentCount = 0;
        int lastCount = 0;

        ArrayList<Integer> currentPlans = new ArrayList();
        ArrayList<Integer> lastPlans = new ArrayList();

        for (String label : labels) {
            for (CAPDEVPlan plan : planList) {
                implementedDate.setTime(plan.getImplementedDate());
                if (label.equalsIgnoreCase("Q1")) {
                    if (implementedDate.get(Calendar.MONTH) == 0 || implementedDate.get(Calendar.MONTH) == 1 || implementedDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentPlans.add(plan.getPlanID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastPlans.add(plan.getPlanID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q2")) {
                    if (implementedDate.get(Calendar.MONTH) == 3 || implementedDate.get(Calendar.MONTH) == 4 || implementedDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q1
                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentPlans.add(plan.getPlanID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastPlans.add(plan.getPlanID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q3")) {
                    if (implementedDate.get(Calendar.MONTH) == 6 || implementedDate.get(Calendar.MONTH) == 7 || implementedDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q1

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentPlans.add(plan.getPlanID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastPlans.add(plan.getPlanID());
                                lastCount++;
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q4")) {
                    if (implementedDate.get(Calendar.MONTH) == 9 || implementedDate.get(Calendar.MONTH) == 10 || implementedDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q1

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentPlans.add(plan.getPlanID()); // ADDS THE ARBO ID
                                currentCount++; // ADDS THE COUNT FOR Q1
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastPlans.add(plan.getPlanID());
                                lastCount++;
                            }
                        }
                    }
                }

            }
            currentYear.addData(currentCount);
            lastYear.addData(lastCount);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getLineChartCAPDEVTotalImplementedBudget(ArrayList<CAPDEVPlan> planList, String titleStr) {

        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar implementedDate = Calendar.getInstance();

        double currentSum = 0;
        double lastSum = 0;

        ArrayList<Integer> currentPlans = new ArrayList();
        ArrayList<Integer> lastPlans = new ArrayList();

        for (String label : labels) {
            for (CAPDEVPlan plan : planList) {
                implementedDate.setTime(plan.getImplementedDate());
                if (label.equalsIgnoreCase("Q1")) {
                    if (implementedDate.get(Calendar.MONTH) == 0 || implementedDate.get(Calendar.MONTH) == 1 || implementedDate.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentSum += plan.getBudget();
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastSum += plan.getBudget();
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q2")) {
                    if (implementedDate.get(Calendar.MONTH) == 3 || implementedDate.get(Calendar.MONTH) == 4 || implementedDate.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q1
                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentSum += plan.getBudget();
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastSum += plan.getBudget();
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q3")) {
                    if (implementedDate.get(Calendar.MONTH) == 6 || implementedDate.get(Calendar.MONTH) == 7 || implementedDate.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q1

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentSum += plan.getBudget();
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastSum += plan.getBudget();
                            }
                        }
                    }
                } else if (label.equalsIgnoreCase("Q4")) {
                    if (implementedDate.get(Calendar.MONTH) == 9 || implementedDate.get(Calendar.MONTH) == 10 || implementedDate.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q1

                        if (implementedDate.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                            if (!currentPlans.contains(plan.getPlanID())) { // IF NOT YET ON THE LIST, ADD
                                currentSum += plan.getBudget();
                            }
                        } else if (implementedDate.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                            if (!lastPlans.contains(plan.getPlanID())) {
                                lastSum += plan.getBudget();
                            }
                        }
                    }
                }

            }
            currentYear.addData(currentSum);
            lastYear.addData(lastSum);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    //</editor-fold>
    //<editor-fold desc="ARBO PROFILE">
    public String getPieChartDisbursement(ArrayList<ARB> arbList) {

        APCPRequestDAO dao2 = new APCPRequestDAO();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetDisbursements = new PieDataset();

        for (ARB arb : arbList) {
            double amount = 0;
            stringLabels.add(arb.getFLName());
            ArrayList<Disbursement> disbursements = dao2.getAllDisbursementsByARB(arb.getArbID());

            for (Disbursement d : disbursements) {
                amount += d.getDisbursedAmount();
            }

            datasetDisbursements.addData(amount);
            datasetDisbursements.addBackgroundColor(Color.random());
            datasetDisbursements.setBorderWidth(2);

        }

        data.addDataset(datasetDisbursements);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }

    public String getPieChartRepayment(ArrayList<ARB> arbList) {

        APCPRequestDAO dao2 = new APCPRequestDAO();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetRepayments = new PieDataset();

        for (ARB arb : arbList) {
            double amount = 0;
            stringLabels.add(arb.getFLName());
            ArrayList<Repayment> repayments = dao2.getArbRepaymentsByARB(arb.getArbID());

            for (Repayment r : repayments) {
                amount += r.getAmount();
            }

            datasetRepayments.addData(amount);
            datasetRepayments.addBackgroundColor(Color.random());
            datasetRepayments.setBorderWidth(2);

        }

        data.addDataset(datasetRepayments);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }

    public String getPieChartOSBalance(ArrayList<ARB> arbList) {

        APCPRequest req = new APCPRequest();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetRepayments = new PieDataset();

        for (ARB arb : arbList) {

            double amount = 0;
            stringLabels.add(arb.getFLName());

            amount += req.getTotalARBOSBalance(arb.getArbID());

            datasetRepayments.addData(amount);
            datasetRepayments.addBackgroundColor(Color.random());
            datasetRepayments.setBorderWidth(2);

        }

        data.addDataset(datasetRepayments);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }

    public String getLineChartPastDue(ArrayList<APCPRequest> requestList, String titleStr) {
        ArrayList<String> labels = new ArrayList();
        LineOptions options = new LineOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(titleStr);

        options.setTitle(title);

        Calendar current = Calendar.getInstance();

        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, -1);

        labels.add("Q1");
        labels.add("Q2");
        labels.add("Q3");
        labels.add("Q4");

        LineDataset currentYear = new LineDataset();
        currentYear.setLabel(String.valueOf(current.get(Calendar.YEAR)));
        currentYear.addPointBackgroundColor(Color.GREEN);
        currentYear.setBorderColor(Color.GREEN);
        currentYear.setBackgroundColor(Color.TRANSPARENT);

        LineDataset lastYear = new LineDataset();
        lastYear.setLabel(String.valueOf(last.get(Calendar.YEAR)));
        lastYear.addPointBackgroundColor(Color.LIGHT_BLUE);
        lastYear.setBorderColor(Color.LIGHT_BLUE);
        lastYear.setBackgroundColor(Color.TRANSPARENT);

        Calendar dateRecorded = Calendar.getInstance();

        ArrayList<Integer> currentARBOs = new ArrayList();
        ArrayList<Integer> lastARBOs = new ArrayList();

        APCPRequestDAO dao = new APCPRequestDAO();

        double currentSum = 0;
        double lastSum = 0;

        for (String label : labels) {

            for (APCPRequest req : requestList) {
                req.setPastDueAccounts(dao.getAllPastDueAccountsByRequest(req.getRequestID()));
                for (PastDueAccount pda : req.getPastDueAccounts()) {

                    dateRecorded.setTime(pda.getDateRecorded());

                    if (label.equalsIgnoreCase("Q1")) {
                        if (dateRecorded.get(Calendar.MONTH) == 0 || dateRecorded.get(Calendar.MONTH) == 1 || dateRecorded.get(Calendar.MONTH) == 2) { // FIRST, CHECK IF Q1
                            if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += pda.getPastDueAmount();
                            } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += pda.getPastDueAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q2")) {
                        if (dateRecorded.get(Calendar.MONTH) == 3 || dateRecorded.get(Calendar.MONTH) == 4 || dateRecorded.get(Calendar.MONTH) == 5) { // FIRST, CHECK IF Q2
                            if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += pda.getPastDueAmount();
                            } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += pda.getPastDueAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q3")) {
                        if (dateRecorded.get(Calendar.MONTH) == 6 || dateRecorded.get(Calendar.MONTH) == 7 || dateRecorded.get(Calendar.MONTH) == 8) { // FIRST, CHECK IF Q3
                            if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += pda.getPastDueAmount();
                            } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += pda.getPastDueAmount();
                            }
                        }
                    } else if (label.equalsIgnoreCase("Q4")) {
                        if (dateRecorded.get(Calendar.MONTH) == 9 || dateRecorded.get(Calendar.MONTH) == 10 || dateRecorded.get(Calendar.MONTH) == 11) { // FIRST, CHECK IF Q4
                            if (dateRecorded.get(Calendar.YEAR) == current.get(Calendar.YEAR)) { // IF SAME YEAR
                                currentSum += pda.getPastDueAmount();
                            } else if (dateRecorded.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
                                lastSum += pda.getPastDueAmount();
                            }
                        }
                    }

                }
            }
            currentYear.addData(currentSum);
            lastYear.addData(lastSum);
        }

        LineData data = new LineData();
        ArrayList<LineDataset> datasets = new ArrayList();
        datasets.add(lastYear);
        datasets.add(currentYear);

        data.setLabels(labels);
        data.setDatasets(datasets);

        return new LineChart(data, options).toJson();

    }

    public String getPieChartPastDue(ArrayList<APCPRequest> unsettledProvincialRequests) {
        CAPDEVDAO dao = new CAPDEVDAO();
        APCPRequestDAO dao2 = new APCPRequestDAO();
        ArrayList<PastDueAccount> reasons = dao.getAllPastDueReasons();
        ArrayList<String> stringLabels = new ArrayList();
        PieData data = new PieData();
        PieDataset datasetReasons = new PieDataset();

        for (PastDueAccount reason : reasons) {
            int count = 0;
            stringLabels.add(reason.getReasonPastDueDesc());
            for (APCPRequest r : unsettledProvincialRequests) {
                r.setUnsettledPastDueAccounts(dao2.getAllUnsettledPastDueAccountsByRequest(r.getRequestID()));
                ArrayList<PastDueAccount> unsettled = r.getUnsettledPastDueAccounts();
                for (PastDueAccount pda : unsettled) {
                    if (pda.getReasonPastDue() == reason.getReasonPastDue()) {
                        count++;
                    }
                }
            }
            datasetReasons.addData(count);
            datasetReasons.addBackgroundColor(Color.random());
            datasetReasons.setBorderWidth(2);
        }

        data.addDataset(datasetReasons);
        for (String l : stringLabels) {
            data.addLabel(l);
        }

        return new PieChart(data).toJson();
    }
    //</editor-fold>

    public ArrayList<APCPRequest> filterAPCPByDate(ArrayList<APCPRequest> list, Date start, Date end) {

        ArrayList<APCPRequest> filtered = new ArrayList();

        for (APCPRequest r : list) {
            if (isIncluded(r.getDateRequested(), start, end)) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public ArrayList<CAPDEVPlan> filterCAPDEVByDate(ArrayList<CAPDEVPlan> list, Date start, Date end) {

        ArrayList<CAPDEVPlan> filtered = new ArrayList();

        for (CAPDEVPlan r : list) {
            if (isIncluded(r.getPlanDate(), start, end)) {
                filtered.add(r);
            }
        }

        return filtered;
    }

    public boolean isIncluded(Date d, Date start, Date end) {
        return start.compareTo(d) * d.compareTo(end) > 0;
    }
}
