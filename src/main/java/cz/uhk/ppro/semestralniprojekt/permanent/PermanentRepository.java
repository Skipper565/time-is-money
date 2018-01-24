package cz.uhk.ppro.semestralniprojekt.permanent;

import org.springframework.data.repository.CrudRepository;

public interface PermanentRepository extends CrudRepository<Permanent, Integer> {

    Permanent findOneByRevenueId(Integer revenueId);

    Permanent findOneByCostId(Integer costId);

}
