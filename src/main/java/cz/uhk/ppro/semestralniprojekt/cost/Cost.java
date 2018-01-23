package cz.uhk.ppro.semestralniprojekt.cost;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.*;

@Entity
@Table(name = "costs")
@AttributeOverrides({
    @AttributeOverride(name = "date", column = @Column(name = "cos_date")),
})
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_COST")
public class Cost extends FinancialEntity {}
