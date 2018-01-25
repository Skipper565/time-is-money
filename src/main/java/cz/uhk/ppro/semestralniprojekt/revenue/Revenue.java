package cz.uhk.ppro.semestralniprojekt.revenue;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.permanent.Permanent;

import javax.persistence.*;

@Entity
@Table(name = "revenues")
@AttributeOverride(name = "date", column = @Column(name = "rev_date"))
@SequenceGenerator(name = "default_gen", sequenceName = "SEQ_REVENUE")
public class Revenue extends FinancialEntity {

    @OneToOne(mappedBy = "revenue")
    private Permanent permanent;

    public Revenue() {}

    public Revenue(Revenue revenue) {
        this.id = revenue.id;
        this.date = revenue.date;
        this.value = revenue.value;
        this.note = revenue.note;
        this.user = revenue.user;
        this.permanent = revenue.permanent;
    }

    public Permanent getPermanent() {
        return permanent;
    }

    public void setPermanent(Permanent permanent) {
        this.permanent = permanent;
    }

}
