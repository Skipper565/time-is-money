package cz.uhk.ppro.semestralniprojekt.revenue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RevenueRepository extends CrudRepository<Revenue, Integer> {

    List<Revenue> findByUserIdAndDateBetween(Integer userId, Date start, Date end);

    @Query("select sum(r.value) from Revenue r where r.user.id = ?1 and r.date < ?2")
    Float sumValueByUserIdAndDateLessThen(Integer userId, Date end);

    Revenue findOneByIdAndUserId(Integer id, Integer userId);

}