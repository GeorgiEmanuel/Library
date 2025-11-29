package model;

import java.util.List;

public class MonthlyReport {

    private List<MonthlyReportRow> monthlyReport;

    public MonthlyReport(List<MonthlyReportRow>  monthlyReport){
        this.monthlyReport = monthlyReport;
    }

    public List<MonthlyReportRow> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(List<MonthlyReportRow> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    @Override
    public String toString(){
        if (monthlyReport == null || monthlyReport.isEmpty()){
            return "No data available for the current month";
        }
        String rows =
                monthlyReport.stream().map(row -> "Customer: " + row.getUsername() +
                                            ", number of books bought: " + row.getNumberOfBooksBought() +
                                            ", total price: " + row.getTotalPrice())
                        .reduce("",(a,b) -> a + b + "\n");
        return "Monthly Report:\n" + rows;
    }
}
