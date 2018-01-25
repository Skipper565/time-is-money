package cz.uhk.ppro.semestralniprojekt.cost;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CostRepository extends CrudRepository<Cost, Integer> {

    List<Cost> findByUserIdAndDateBetweenAndPermanentIsNull(Integer userId, Date start, Date end);

    @Query("select c from Cost c inner join c.user u inner join c.permanent p where u.id = ?1")
    List<Cost> findPermanentsByUserId(Integer userId);

    @Query("select sum(c.value) from Cost c inner join c.user u left outer join c.permanent p where u.id = ?1 and c.date < ?2 and p is null")
    Float sumValueByUserIdAndDateLessThan(Integer userId, Date end);

    Cost findOneByIdAndUserId(Integer id, Integer userId);

}