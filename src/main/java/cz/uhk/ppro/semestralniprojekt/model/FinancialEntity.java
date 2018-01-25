package cz.uhk.ppro.semestralniprojekt.model;

import cz.uhk.ppro.semestralniprojekt.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class FinancialEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    protected Integer id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    protected Date date;

    @Column(name = "value")
    protected float value;

    @Column(name = "note")
    protected String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

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

}
