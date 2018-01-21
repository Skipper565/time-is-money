package cz.uhk.ppro.semestralniprojekt.cost;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.*;

@Entity
@Table(name = "costs")
public class Cost extends FinancialEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cost")
    @SequenceGenerator(name = "id_cost", sequenceName = "SEQ_COST")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
