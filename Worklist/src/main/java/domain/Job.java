package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Job {

    private int id;
    private String name;
    private LocalDateTime created;
    private LocalDate dueDate;
    private LocalDate finished;
    private LocalDate deleted;
    private int quantity;
    private Material material;
    private Double workloadEstimate;
    private Double workloadActual;
    private String details;
    private String customer;
    private Employee creator;

    public Job(String name, LocalDate dueDate, int quantity, Material material,
               Double workloadEstimate, String details, String customer, Employee creator) {
        this.name = name;
        this.created = LocalDateTime.now();
        this.dueDate = dueDate;
        this.quantity = quantity;
        this.material = material;
        this.workloadEstimate = workloadEstimate;
        this.details = details;
        this.customer = customer;
        this.creator = creator;
    }

    public Job(int id, String name, LocalDateTime created, LocalDate dueDate, LocalDate finished, LocalDate deleted,
               int quantity, Material material, Double workloadEstimate, Double workloadActual,
               String details, String customer, Employee creator) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.dueDate = dueDate;
        this.finished = finished;
        this.deleted = deleted;
        this.quantity = quantity;
        this.material = material;
        this.workloadEstimate = workloadEstimate;
        this.workloadActual = workloadActual;
        this.details = details;
        this.customer = customer;
        this.creator = creator;
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

    public String getCreatedString() {
        return created.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
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

    public boolean isFinished() {
        return finished != null;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
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

    public Employee getCreator() {
        return creator;
    }

    public void setCreator(Employee creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creator != null ? creator.getUsername() : "";
    }

}


