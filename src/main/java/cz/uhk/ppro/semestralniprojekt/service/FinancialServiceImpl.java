package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.cost.Cost;
import cz.uhk.ppro.semestralniprojekt.cost.CostRepository;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.revenue.Revenue;
import cz.uhk.ppro.semestralniprojekt.revenue.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public void save(FinancialEntity entity) {
        if (entity.getType().equals("COST")) {
            Cost cost = (Cost) entity;
            costRepository.save(cost);
        } else if (entity.getType().equals("REVENUE")) {
            Revenue revenue = (Revenue) entity;
            revenueRepository.save(revenue);
        }
    }

}
