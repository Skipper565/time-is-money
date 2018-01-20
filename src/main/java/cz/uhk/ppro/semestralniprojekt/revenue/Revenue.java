package cz.uhk.ppro.semestralniprojekt.revenue;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "revenues")
public class Revenue extends FinancialEntity {
}
