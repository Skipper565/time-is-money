package cz.uhk.ppro.semestralniprojekt.permanent;

import cz.uhk.ppro.semestralniprojekt.cost.Cost;
import cz.uhk.ppro.semestralniprojekt.revenue.Revenue;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permanent")
public class Permanent implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_permanent")
    @SequenceGenerator(name = "id_permanent", sequenceName = "SEQ_PERMANENT")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "revenue_id")
    private Revenue revenue;

    @ManyToOne
    @JoinColumn(name = "cost_id")
    private Cost cost;

    @Column(name = "month_day")
    @NotEmpty
    private int month_day;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Revenue getRevenue() {
        return revenue;
    }

    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public int getMonth_day() {
        return month_day;
    }

    public void setMonth_day(int month_day) {
        this.month_day = month_day;
    }

}
