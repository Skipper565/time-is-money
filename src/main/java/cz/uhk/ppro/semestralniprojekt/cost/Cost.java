package cz.uhk.ppro.semestralniprojekt.cost;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "costs")
public class Cost extends FinancialEntity {
}
