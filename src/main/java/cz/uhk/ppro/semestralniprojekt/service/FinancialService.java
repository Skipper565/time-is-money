package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.user.User;

import java.util.List;

public interface FinancialService {

    List<FinancialEntity> getAllFinancialEntitiesForUser(Integer userId);

    FinancialEntity resolveCostOrRevenue(User user, String entityType, Integer entityId);

    void save(FinancialEntity entity);

    void deleteCostOrRevenue(User user, String entityType, Integer entityId);

}