package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.cost.Cost;
import cz.uhk.ppro.semestralniprojekt.cost.CostRepository;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.permanent.Permanent;
import cz.uhk.ppro.semestralniprojekt.permanent.PermanentRepository;
import cz.uhk.ppro.semestralniprojekt.revenue.Revenue;
import cz.uhk.ppro.semestralniprojekt.revenue.RevenueRepository;
import cz.uhk.ppro.semestralniprojekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private PermanentRepository permanentRepository;

    @Override
    public List<FinancialEntity> getIntervalFinancialEntitiesForUser(Integer userId, LocalDate startDate, LocalDate endDate) {
        List<FinancialEntity> finance = new ArrayList<>();

        List<Revenue> revenueList = revenueRepository.findByUserIdAndDateBetweenAndPermanentIsNull(userId, Date.valueOf(startDate), Date.valueOf(endDate));
        List<Cost> costList = costRepository.findByUserIdAndDateBetweenAndPermanentIsNull(userId, Date.valueOf(startDate), Date.valueOf(endDate));
        List<Revenue> revenuePermanentList = revenueRepository.findPermanentsByUserId(userId);
        List<Cost> costPermanentList = costRepository.findPermanentsByUserId(userId);

        for (Revenue revenue : revenuePermanentList) {
            Permanent permanent = permanentRepository.findOneByRevenueId(revenue.getId());
            Date permanentDateInCurrentMonth = Date.valueOf(startDate.plusDays(permanent.getMonthDay() - 1));
            if (permanentDateInCurrentMonth.compareTo(revenue.getDate()) >= 0) {
                Revenue copy = new Revenue(revenue);
                copy.setDate(permanentDateInCurrentMonth);
                revenueList.add(copy);
            }
        }

        for (Cost cost : costPermanentList) {
            Permanent permanent = permanentRepository.findOneByCostId(cost.getId());
            Date permanentDateInCurrentMonth = Date.valueOf(startDate.plusDays(permanent.getMonthDay() - 1));
            if (permanentDateInCurrentMonth.compareTo(cost.getDate()) >= 0) {
                Cost copy = new Cost(cost);
                copy.setDate(permanentDateInCurrentMonth);
                costList.add(copy);
            }
        }

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
    public Float sumBalanceForUserDueDate(Integer userId, LocalDate date) {
        Float balance = 0f;
        Float sumRevenues = revenueRepository.sumValueByUserIdAndDateLessThan(userId, Date.valueOf(date));
        Float sumCosts = costRepository.sumValueByUserIdAndDateLessThan(userId, Date.valueOf(date));
        List<Revenue> revenuePermanentList = revenueRepository.findPermanentsByUserId(userId);
        List<Cost> costPermanentList = costRepository.findPermanentsByUserId(userId);

        for (Revenue revenue : revenuePermanentList) {
            LocalDate revenueDate = revenue.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long monthDiff = ChronoUnit.MONTHS.between(revenueDate, date);
            if (monthDiff > 0) {
                if (sumRevenues == null) {
                    sumRevenues = 0f;
                }

                sumRevenues += (revenue.getValue() * monthDiff);
            }
        }

        for (Cost cost : costPermanentList) {
            LocalDate costDate = cost.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long monthDiff = ChronoUnit.MONTHS.between(costDate, date);
            if (monthDiff > 0) {
                if (sumCosts == null) {
                    sumCosts = 0f;
                }

                sumCosts += (cost.getValue() * monthDiff);
            }
        }

        if (sumRevenues != null) {
            balance += sumRevenues;
        }

        if (sumCosts != null) {
            balance -= sumCosts;
        }

        return balance;
    }

    @Override
    public Float countBalance(List<FinancialEntity> entities) {
        Float result = 0f;

        for (FinancialEntity entity : entities) {
            if (entity.getType().equals("cost")) {
                result -= entity.getValue();
            } else if (entity.getType().equals("revenue")) {
                result += entity.getValue();
            }
        }

        return result;
    }

    @Override
    public FinancialEntity resolveCostOrRevenue(User user, String entityType, Integer entityId) {
        FinancialEntity entity = null;

        if (entityType.equals("cost")) {
            entity = costRepository.findOneByIdAndUserId(entityId, user.getId());

            Permanent permanent = permanentRepository.findOneByCostId(entityId);
            if (entity != null && permanent != null) {
                entity.setIsPermanent(true);
                entity.setMonthDay(permanent.getMonthDay());
            }
        } else if (entityType.equals("revenue")) {
            entity = revenueRepository.findOneByIdAndUserId(entityId, user.getId());

            Permanent permanent = permanentRepository.findOneByRevenueId(entityId);
            if (entity != null && permanent != null) {
                entity.setIsPermanent(true);
                entity.setMonthDay(permanent.getMonthDay());
            }
        }

        if (entity != null) {
            entity.setType(entityType);
        }

        return entity;
    }

    @Override
    public void save(FinancialEntity entity) {
        if (entity.getType().equals("cost")) {
            saveCost(entity);
        } else if (entity.getType().equals("revenue")) {
            saveRevenue(entity);
        }
    }

    @Override
    public void deleteCostOrRevenue(User user, String entityType, Integer entityId) {
        if (entityType.equals("cost")) {
            Cost entity = costRepository.findOneByIdAndUserId(entityId, user.getId());
            if (entity != null) {
                if (entity.getPermanent() != null) {
                    permanentRepository.delete(entity.getPermanent());
                }

                costRepository.delete(entity);
            }
        } else if (entityType.equals("revenue")) {
            Revenue entity = revenueRepository.findOneByIdAndUserId(entityId, user.getId());
            if (entity != null) {
                if (entity.getPermanent() != null) {
                    permanentRepository.delete(entity.getPermanent());
                }

                revenueRepository.delete(entity);
            }
        }
    }

    private void saveCost(FinancialEntity entity) {
        Cost cost = new Cost();
        Permanent permanent = permanentRepository.findOneByCostId(entity.getId());

        cost.setId(entity.getId());
        cost.setDate(entity.getDate());
        cost.setValue(entity.getValue());
        cost.setNote(entity.getNote());
        cost.setUser(entity.getUser());

        cost = costRepository.save(cost);

        if (entity.getIsPermanent()) {
            if (permanent == null) {
                permanent = new Permanent();
            }
            permanent.setCost(cost);
            permanent.setMonthDay(entity.getMonthDay());

            permanentRepository.save(permanent);
        } else if (permanent != null) {
            permanentRepository.delete(permanent);
        }
    }

    private void saveRevenue(FinancialEntity entity) {
        Revenue revenue = new Revenue();
        Permanent permanent = permanentRepository.findOneByRevenueId(entity.getId());

        revenue.setId(entity.getId());
        revenue.setDate(entity.getDate());
        revenue.setValue(entity.getValue());
        revenue.setNote(entity.getNote());
        revenue.setUser(entity.getUser());

        revenue = revenueRepository.save(revenue);

        if (entity.getIsPermanent()) {
            if (permanent == null) {
                permanent = new Permanent();
            }
            permanent.setRevenue(revenue);
            permanent.setMonthDay(entity.getMonthDay());

            permanentRepository.save(permanent);
        } else if (permanent != null) {
            permanentRepository.delete(permanent);
        }
    }

}
