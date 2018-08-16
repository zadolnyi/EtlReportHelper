package com.zadolnyi.reporthelper.model;

public class ReportFactory {

    static public ReportLine getReport(Reports reportsItem) {
        ReportLine report = null;
        try {
            report = (ReportLine)Class.forName("com.zadolnyi.reporthelper.model."+reportsItem.getClassName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return report;
    }
}
