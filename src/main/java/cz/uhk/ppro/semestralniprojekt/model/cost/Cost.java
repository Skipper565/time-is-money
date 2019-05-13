package cz.uhk.ppro.semestralniprojekt.model.cost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.model.permanent.Permanent;

import javax.persistence.*;

@Entity
@Table(name = "costs")
@AttributeOverride(name = "date", column = @Column(name = "cos_date"))
public class Cost extends FinancialEntity {

    @OneToOne(mappedBy = "cost")
    @JsonIgnore
    private Permanent permanent;

    public Cost() {}

    public Cost(Cost cost) {
        this.id = cost.id;
        this.date = cost.date;
        this.value = cost.value;
        this.note = cost.note;
        this.user = cost.user;
        this.latitude = cost.latitude;
        this.longitude = cost.longitude;
        this.permanent = cost.permanent;
    }

    public Cost(FinancialEntity entity) {
        this.date = entity.getDate();
        this.value = entity.getValue();
        this.note = entity.getNote();
        this.user = entity.getUser();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }

    public Permanent getPermanent() {
        return permanent;
    }

    public void setPermanent(Permanent permanent) {
        this.permanent = permanent;
    }

}
