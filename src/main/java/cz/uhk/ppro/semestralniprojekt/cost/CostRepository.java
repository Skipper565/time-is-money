package cz.uhk.ppro.semestralniprojekt.cost;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CostRepository extends CrudRepository<Cost, Integer> {

    List<Cost> findByUserId(Integer userId);

    Cost findOneByIdAndUserId(Integer id, Integer userId);

}