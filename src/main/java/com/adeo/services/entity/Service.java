package com.adeo.services.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "service")
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private boolean isEnabled;
  @ManyToMany(mappedBy = "services")
  private List<Order> orders = new ArrayList<>();
  @ManyToOne
  @JoinColumn(name = "service_type_id", insertable = false, updatable = true)
  private ServiceType serviceType;
  private Boolean isDeleted = false;

  public Service() {
  }

  public Service(int id, String name, boolean isEnabled) {
    this.id = id;
    this.name = name;
    this.isEnabled = isEnabled;
  }

  public Service(String name, boolean isEnabled) {
    this.name = name;
    this.isEnabled = isEnabled;
  }

  public void update(Service service) {
    this.name = service.getName();
    this.isEnabled = service.isEnabled();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public ServiceType getServiceType() {
    return serviceType;
  }

  public void setServiceType(ServiceType serviceType) {
    this.serviceType = serviceType;
  }

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }
}
