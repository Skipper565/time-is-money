package cz.uhk.ppro.semestralniprojekt.model.api;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import java.util.List;

public class MonthFinanceOverview {

    private String date;
    private float startBalance;
    private float endBalance;
    private List<FinancialEntity> financeList;

    public MonthFinanceOverview(String date, float startBalance, float endBalance, List<FinancialEntity> financeList) {
        this.date = date;
        this.startBalance = startBalance;
        this.endBalance = endBalance;
        this.financeList = financeList;
    }

    public String getDate() {
        return date;
    }

    public float getStartBalance() {
        return startBalance;
    }

    public float getEndBalance() {
        return endBalance;
    }

    public List<FinancialEntity> getFinanceList() {
        return financeList;
    }
}
