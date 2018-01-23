package cz.uhk.ppro.semestralniprojekt.revenue;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.*;

@Entity
@Table(name = "revenues")
@AttributeOverrides({
        @AttributeOverride(name = "date", column = @Column(name = "rev_date")),
})
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_REVENUE")
public class Revenue extends FinancialEntity {}
