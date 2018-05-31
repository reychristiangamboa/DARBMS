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

    public String getStackedBarChartAPCPRequests(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int requestedCount = 0;
            int clearedCount = 0;
            int endorsedCount = 0;
            int approvedCount = 0;
            int forReleaseCount = 0;
            int releasedCount = 0;

            for (ARBO arbo : arboList) {
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
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
                        for (APCPRequest r : arbo.getRequestList()) {
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
            requested.addBackgroundColor(Color.random());
            requested.setLabel("REQUESTED");
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
            forRelease.setLabel("FOR RELEASE");
        }

        for (int value : releasedValues) {
            released.addData(value);
            released.addBackgroundColor(Color.random());
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

    public String getStackedBarChartApprovalRate(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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

        Calendar current = Calendar.getInstance();

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int requestedCount = 0;
            int clearedCount = 0;
            int endorsedCount = 0;
            int approvedCount = 0;
            int forReleaseCount = 0;

            int requestedDays = 0;
            int clearedDays = 0;
            int endorsedDays = 0;
            int approvedDays = 0;
            int forReleaseDays = 0;

            for (ARBO arbo : arboList) {

                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            Calendar startDate = Calendar.getInstance();
                            Calendar endDate = Calendar.getInstance();

                            if (r.getDateRequested() != null && r.getDateCleared() != null) {
                                requestedCount++;
                                startDate.setTime(r.getDateRequested());
                                endDate.setTime(r.getDateCleared());
                                requestedDays += (int) daysBetween(startDate, endDate);
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
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            Calendar startDate = Calendar.getInstance();
                            Calendar endDate = Calendar.getInstance();

                            if (r.getDateRequested() != null && r.getDateCleared() != null) {
                                requestedCount++;
                                startDate.setTime(r.getDateRequested());
                                endDate.setTime(r.getDateCleared());
                                requestedDays += (int) daysBetween(startDate, endDate);
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

            requestedValues.add(requestedDays / requestedCount);
            clearedValues.add(clearedDays / clearedCount);
            endorsedValues.add(endorsedDays / endorsedCount);
            approvedValues.add(approvedDays / approvedCount);

            data.addLabel(labels.get(i));

        }

        for (int value : requestedValues) {
            requested.addData(value);
            requested.addBackgroundColor(Color.random());
            requested.setLabel("REQUESTED");
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

        data.addDataset(requested);
        data.addDataset(cleared);
        data.addDataset(endorsed);
        data.addDataset(approved);
//        data.addDataset(forRelease);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getBarChartLoanType(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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
                for (APCPRequest r : arbo.getRequestList()) {
                    if (r.getApcpType() == 1) {
                        total++;
                    } else {
                        total1++;
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

        return new BarChart(data).setHorizontal().toJson();

    }

    public String getStackedBarChartLoanReason(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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

        BarDataset NeedCapital = new BarDataset();
        ArrayList<Integer> needCapitalValues = new ArrayList();
        BarDataset HogFattening = new BarDataset();
        ArrayList<Integer> hogFatteningValues = new ArrayList();
        BarDataset RiceProduction = new BarDataset();
        ArrayList<Integer> riceProductionValues = new ArrayList();
        BarDataset CornProduction = new BarDataset();
        ArrayList<Integer> cornProductionValues = new ArrayList();

        BarOptions options = new BarOptions();
        BarScale scales = new BarScale();
        XAxis x = new XAxis();
        YAxis y = new YAxis();
        x.setStacked(Boolean.TRUE);
        y.setStacked(Boolean.TRUE);
        scales.addxAxes(x);
        scales.addyAxes(y);
        options.setScales(scales);

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int one = 0;
            int two = 0;
            int three = 0;
            int four = 0;

            for (ARBO arbo : arboList) {
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            if (r.getLoanReason() == 1) { // requested
                                one++;
                            } else if (r.getRequestStatus() == 2) { // cleared
                                two++;
                            } else if (r.getRequestStatus() == 3) { // endorsed
                                three++;
                            } else if (r.getRequestStatus() == 4) { // approved
                                four++;
                            }
                        }
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            if (r.getLoanReason() == 1) { // requested
                                one++;
                            } else if (r.getRequestStatus() == 2) { // cleared
                                two++;
                            } else if (r.getRequestStatus() == 3) { // endorsed
                                three++;
                            } else if (r.getRequestStatus() == 4) { // approved
                                four++;
                            }
                        }
                    }
                }
            }

            needCapitalValues.add(one);
            hogFatteningValues.add(two);
            riceProductionValues.add(three);
            cornProductionValues.add(four);

            data.addLabel(labels.get(i));

        }

        for (int value : needCapitalValues) {
            NeedCapital.addData(value);
            NeedCapital.addBackgroundColor(Color.random());
            NeedCapital.setLabel("NEED CAPITAL");
        }

        for (int value : hogFatteningValues) {
            HogFattening.addData(value);
            HogFattening.addBackgroundColor(Color.random());
            HogFattening.setLabel("HOG FATTENING");
        }

        for (int value : riceProductionValues) {
            RiceProduction.addData(value);
            RiceProduction.addBackgroundColor(Color.random());
            RiceProduction.setLabel("RICE PRODUCTION");
        }

        for (int value : cornProductionValues) {
            CornProduction.addData(value);
            CornProduction.addBackgroundColor(Color.random());
            CornProduction.setLabel("CORN PRODUCTION");
        }

        data.addDataset(NeedCapital);
        data.addDataset(HogFattening);
        data.addDataset(RiceProduction);
        data.addDataset(CornProduction);

        return new BarChart(data, options).setHorizontal().toJson();

    }

    public String getBarChartAPCPAmounts(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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
            System.out.println("REGION EMPTY!");
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

        BarDataset one = new BarDataset();
        BarDataset two = new BarDataset();
        BarDataset three = new BarDataset();
        BarDataset four = new BarDataset();
        ArrayList<Integer> aa = new ArrayList();
        ArrayList<Integer> bb = new ArrayList();
        ArrayList<Integer> cc = new ArrayList();
        ArrayList<Integer> dd = new ArrayList();

        APCPRequestDAO dao = new APCPRequestDAO();

        Calendar current = Calendar.getInstance();
        int year = current.get(Calendar.YEAR);

        for (int i = 0; i < labels.size(); i++) {
            int a1 = 0;
            int a2 = 0;
            int a3 = 0;
            int a4 = 0;
            for (ARBO arbo : arboList) {
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        a1 += dao.getTotalPastDueAmount(arbo.getRequestList());
                        a2 += dao.getYearlySumOfReleasesByRequestId(arbo.getRequestList(), year);
                        a3 += dao.getSumOfAccumulatedReleasesByRequestId(arbo.getRequestList());
                        a4 += dao.getTotalApprovedAmount(arbo.getRequestList());
                    }
                } else { // PROVINCIAL OFFICE
                    if (labels.get(i).equalsIgnoreCase(arbo.getProvOfficeCodeDesc())) { // checker
                        a1 += dao.getTotalPastDueAmount(arbo.getRequestList());
                        a2 += dao.getYearlySumOfReleasesByRequestId(arbo.getRequestList(), year);
                        a3 += dao.getSumOfAccumulatedReleasesByRequestId(arbo.getRequestList());
                        a4 += dao.getTotalApprovedAmount(arbo.getRequestList());
                    }
                }
            }
            aa.add(a1);
            bb.add(a2);
            cc.add(a3);
            dd.add(a4);
        }

        for (int value : aa) {
            one.addData(value);
            one.addBackgroundColor(Color.random());
            one.setLabel("TOTAL PAST DUE AMOUNT");
        }

        for (int value : bb) {
            two.addData(value);
            two.addBackgroundColor(Color.random());
            two.setLabel("YEARLY RELEASES");
        }

        for (int value : cc) {
            three.addData(value);
            three.addBackgroundColor(Color.random());
            three.setLabel("ACCUMULATED RELEASES");
        }

        for (int value : dd) {
            four.addData(value);
            four.addBackgroundColor(Color.random());
            four.setLabel("APPROVED AMOUNT");
        }

        data.addDataset(one);
        data.addDataset(two);
        data.addDataset(three);
        data.addDataset(four);

        return new BarChart(data).toJson();

    }

    public String getStackedBarChartCAPDEVPlans(ArrayList<Region> regionList, ArrayList<Province> provList, ArrayList<ARBO> arboList) {

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

        for (int i = 0; i < labels.size(); i++) { // REGIONS/PROVINCES

            int pendingCount = 0;
            int approvedCount = 0;
            int assignedCount = 0;
            int implementedCount = 0;

            for (ARBO arbo : arboList) {
                if (b) { // REGIONAL
                    if (labels.get(i).equalsIgnoreCase(arbo.getArboRegionDesc())) { // checker
                        for (APCPRequest r : arbo.getRequestList()) {
                            for (CAPDEVPlan c : r.getPlans()) {
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
                            for (CAPDEVPlan c : r.getPlans()) {
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

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();

        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));
    }
}
