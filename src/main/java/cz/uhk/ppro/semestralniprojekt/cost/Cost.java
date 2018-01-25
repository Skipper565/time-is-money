package cz.uhk.ppro.semestralniprojekt.cost;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.permanent.Permanent;

import javax.persistence.*;

@Entity
@Table(name = "costs")
@AttributeOverride(name = "date", column = @Column(name = "cos_date"))
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_COST")
public class Cost extends FinancialEntity {

    @OneToOne(mappedBy = "cost")
    private Permanent permanent;

    public Cost() {}

    public Cost(Cost cost) {
        this.id = cost.id;
        this.date = cost.date;
        this.value = cost.value;
        this.note = cost.note;
        this.user = cost.user;
        this.permanent = cost.permanent;
    }

    public Permanent getPermanent() {
        return permanent;
    }

    public void setPermanent(Permanent permanent) {
        this.permanent = permanent;
    }

}
