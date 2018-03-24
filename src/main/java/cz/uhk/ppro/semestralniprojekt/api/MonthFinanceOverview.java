package cz.uhk.ppro.semestralniprojekt.api;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import java.util.List;

public class MonthFinanceOverview {

    private float startBalance;
    private float endBalance;
    private List<FinancialEntity> financeList;

    public MonthFinanceOverview(float startBalance, float endBalance, List<FinancialEntity> financeList) {
        this.startBalance = startBalance;
        this.endBalance = endBalance;
        this.financeList = financeList;
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
