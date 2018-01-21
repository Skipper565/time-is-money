package cz.uhk.ppro.semestralniprojekt.revenue;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;

import javax.persistence.*;

@Entity
@Table(name = "revenues")
public class Revenue extends FinancialEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_revenue")
    @SequenceGenerator(name = "id_revenue", sequenceName = "SEQ_REVENUE")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
