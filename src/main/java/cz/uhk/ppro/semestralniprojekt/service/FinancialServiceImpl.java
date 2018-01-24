package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.cost.Cost;
import cz.uhk.ppro.semestralniprojekt.cost.CostRepository;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.revenue.Revenue;
import cz.uhk.ppro.semestralniprojekt.revenue.RevenueRepository;
import cz.uhk.ppro.semestralniprojekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public List<FinancialEntity> getAllFinancialEntitiesForUser(Integer userId) {
        List<FinancialEntity> finance = new ArrayList<>();

        List<Revenue> revenueList = revenueRepository.findByUserId(userId);
        List<Cost> costList = costRepository.findByUserId(userId);

        for (Revenue revenue : revenueList) {
            revenue.setType("revenue");
        }

        for (Cost cost : costList) {
            cost.setType("cost");
        }

        finance.addAll(revenueList);
        finance.addAll(costList);

        return finance;
    }

    @Override
    public FinancialEntity resolveCostOrRevenue(User user, String entityType, Integer entityId) {
        FinancialEntity entity = null;

        if (entityType.equals("cost")) {
            entity = costRepository.findOneByIdAndUserId(entityId, user.getId());
        } else if (entityType.equals("revenue")) {
            entity = revenueRepository.findOneByIdAndUserId(entityId, user.getId());
        }

        if (entity != null) {
            entity.setType(entityType);
        }

        return entity;
    }

    @Override
    public void save(FinancialEntity entity) {
        if (entity.getType().equals("cost")) {
            Cost cost = new Cost();
            cost.setId(entity.getId());
            cost.setDate(entity.getDate());
            cost.setValue(entity.getValue());
            cost.setNote(entity.getNote());
            cost.setUser(entity.getUser());
            costRepository.save(cost);
        } else if (entity.getType().equals("revenue")) {
            Revenue revenue = new Revenue();
            revenue.setId(entity.getId());
            revenue.setDate(entity.getDate());
            revenue.setValue(entity.getValue());
            revenue.setNote(entity.getNote());
            revenue.setUser(entity.getUser());
            revenueRepository.save(revenue);
        }
    }

    @Override
    public void deleteCostOrRevenue(User user, String entityType, Integer entityId) {
        if (entityType.equals("cost")) {
            Cost entity = costRepository.findOneByIdAndUserId(entityId, user.getId());
            if (entity != null) {
                costRepository.delete(entity);
            }
        } else if (entityType.equals("revenue")) {
            Revenue entity = revenueRepository.findOneByIdAndUserId(entityId, user.getId());
            if (entity != null) {
                revenueRepository.delete(entity);
            }
        }
    }

}
