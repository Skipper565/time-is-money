package cz.uhk.ppro.semestralniprojekt.model.permanent;

import cz.uhk.ppro.semestralniprojekt.model.cost.Cost;
import cz.uhk.ppro.semestralniprojekt.model.revenue.Revenue;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permanents")
public class Permanent implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_permanent")
    @SequenceGenerator(name = "id_permanent", sequenceName = "SEQ_PERMANENT")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "revenue_id")
    private Revenue revenue;

    @OneToOne
    @JoinColumn(name = "cost_id")
    private Cost cost;

    @Column(name = "month_day")
    private int monthDay;

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

    public int getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(int monthDay) {
        this.monthDay = monthDay;
    }

}
