package dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Job {

    private int id;
    private String name;
    private LocalDateTime created;
    private LocalDate dueDate;
    private LocalDate finished;
    private LocalDate deleted;
    private int quantity;
    private String material;
    private Double workloadEstimate;
    private Double workloadActual;
    private String details;
    private String customer;


    public Job(int id, String name, LocalDateTime created, LocalDate duedate, LocalDate finished, LocalDate deleted,
               int quantity, String material, Double workloadEstimate, Double workloadActual,
               String details, String customer) {
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
        this.details = details;
        this.customer = customer;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getFinished() {
        return finished;
    }

    public void setFinished(LocalDate finished) {
        this.finished = finished;
    }

    public LocalDate getDeleted() {
        return deleted;
    }

    public void setDeleted(LocalDate deleted) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

}


