package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.model.user.User;

import java.time.LocalDate;
import java.util.List;

public interface FinancialService {

    List<FinancialEntity> getIntervalFinancialEntitiesForUser(Integer userId, LocalDate startDate, LocalDate endDate);

    Float sumBalanceForUserDueDate(Integer userId, LocalDate date);

    Float countBalance(List<FinancialEntity> entities);

    FinancialEntity resolveCostOrRevenue(User user, String entityType, Integer entityId);

    void save(FinancialEntity entity);

    void deleteCostOrRevenue(User user, String entityType, Integer entityId);

}