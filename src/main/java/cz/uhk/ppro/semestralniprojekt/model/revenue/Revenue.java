package cz.uhk.ppro.semestralniprojekt.model.revenue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.model.permanent.Permanent;

import javax.persistence.*;

@Entity
@Table(name = "revenues")
@AttributeOverride(name = "date", column = @Column(name = "rev_date"))
public class Revenue extends FinancialEntity {

    @OneToOne(mappedBy = "revenue")
    @JsonIgnore
    private Permanent permanent;

    public Revenue() {}

    public Revenue(Revenue revenue) {
        this.id = revenue.id;
        this.date = revenue.date;
        this.value = revenue.value;
        this.note = revenue.note;
        this.user = revenue.user;
        this.latitude = revenue.latitude;
        this.longitude = revenue.longitude;
        this.permanent = revenue.permanent;
    }

    public Permanent getPermanent() {
        return permanent;
    }

    public void setPermanent(Permanent permanent) {
        this.permanent = permanent;
    }

}
