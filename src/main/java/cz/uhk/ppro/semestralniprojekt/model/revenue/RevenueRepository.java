package cz.uhk.ppro.semestralniprojekt.model.revenue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface RevenueRepository extends CrudRepository<Revenue, Integer> {

    List<Revenue> findByUserIdAndDateBetweenAndPermanentIsNull(Integer userId, Date start, Date end);

    @Query("select r from Revenue r inner join r.user u inner join r.permanent p where u.id = ?1")
    List<Revenue> findPermanentsByUserId(Integer userId);

    @Query("select sum(r.value) from Revenue r inner join r.user u left outer join r.permanent p where u.id = ?1 and r.date < ?2 and p is null")
    Float sumValueByUserIdAndDateLessThan(Integer userId, Date end);

    Revenue findOneByIdAndUserId(Integer id, Integer userId);

}