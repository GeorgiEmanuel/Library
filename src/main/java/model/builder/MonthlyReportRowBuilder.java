package model.builder;

import model.MonthlyReportRow;

public class MonthlyReportRowBuilder {

    private MonthlyReportRow monthlyReport;

    public MonthlyReportRowBuilder() { this.monthlyReport = new MonthlyReportRow(); }

    public MonthlyReportRowBuilder setNumberOfBooksBought(Long numberOfBooksBought){
        monthlyReport.setNumberOfBooksBought(numberOfBooksBought);
        return this;
    }

    public MonthlyReportRowBuilder setTotalPrice(Long totalPrice){
        monthlyReport.setTotalPrice(totalPrice);
        return this;
    }

    public MonthlyReportRowBuilder setUsername(String username){
        monthlyReport.setUsername(username);
        return this;
    }

    public MonthlyReportRow build(){
        return monthlyReport;
    }


}
