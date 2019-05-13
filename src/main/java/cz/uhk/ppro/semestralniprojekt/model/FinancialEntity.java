package cz.uhk.ppro.semestralniprojekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.uhk.ppro.semestralniprojekt.model.user.User;
import org.jetbrains.annotations.Nullable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class FinancialEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    protected Date date;

    @Column(name = "value")
    protected float value;

    @Column(name = "note")
    protected String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    protected User user;

    @Column(name = "latitude")
    @Nullable
    protected Float latitude;

    @Column(name = "longitude")
    @Nullable
    protected Float longitude;

    @Transient
    private String type;

    @Transient
    private Boolean isPermanent = false;

    @Transient
    private Integer monthDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(@Nullable Float latitude) {
        this.latitude = latitude;
    }

    @Nullable
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(@Nullable Float longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsPermanent() {
        return isPermanent;
    }

    public void setIsPermanent(Boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    public Integer getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(Integer monthDay) {
        this.monthDay = monthDay;
    }

    @Override
    public String toString() {
        return "FinancialEntity{" +
                "id=" + id +
                ", date=" + date +
                ", value=" + value +
                ", note='" + note + '\'' +
                ", user=" + user +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type='" + type + '\'' +
                ", isPermanent=" + isPermanent +
                ", monthDay=" + monthDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialEntity that = (FinancialEntity) o;
        return Float.compare(that.value, value) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(user, that.user) &&
                Objects.equals(type, that.type) &&
                Objects.equals(isPermanent, that.isPermanent) &&
                Objects.equals(monthDay, that.monthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, value, user, type, isPermanent, monthDay);
    }

}
