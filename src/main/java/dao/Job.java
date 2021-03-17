package dao;

import java.time.LocalDateTime;
import java.util.Date;

public class Job {

    private int id;
    private String name;
    private LocalDateTime created;
    private Date dueDate;
    private Date finished;
    private Date deleted;
    private int quantity;
    private String material;
    private Double workloadEstimate;
    private Double workloadActual;

    public Job(int id, String name, LocalDateTime created, Date duedate, Date finished, Date deleted,
               int quantity, String material, Double workloadEstimate, Double workloadActual) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.dueDate = duedate;
        this.finished = finished;
        this.deleted = deleted;
        this.quantity = quantity;
        this.material = material;
        this.workloadEstimate = workloadEstimate;
        this.workloadActual = workloadActual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getWorkloadEstimate() {
        return workloadEstimate;
    }

    public void setWorkloadEstimate(Double workloadEstimate) {
        this.workloadEstimate = workloadEstimate;
    }

    public Double getWorkloadActual() {
        return workloadActual;
    }

    public void setWorkloadActual(Double workloadActual) {
        this.workloadActual = workloadActual;
    }
}


