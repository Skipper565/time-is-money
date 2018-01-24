package cz.uhk.ppro.semestralniprojekt.revenue;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RevenueRepository extends CrudRepository<Revenue, Integer> {

    List<Revenue> findByUserId(Integer userId);

    Revenue findOneByIdAndUserId(Integer id, Integer userId);

}