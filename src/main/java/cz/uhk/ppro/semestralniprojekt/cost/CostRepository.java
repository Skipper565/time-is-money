package cz.uhk.ppro.semestralniprojekt.cost;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CostRepository extends CrudRepository<Cost, Integer> {

    List<Cost> findByUserIdAndDateBetween(Integer userId, Date start, Date end);

    @Query("select sum(c.value) from Cost c where c.user.id = ?1 and c.date < ?2")
    Float sumValueByUserIdAndDateLessThen(Integer userId, Date end);

    Cost findOneByIdAndUserId(Integer id, Integer userId);

}