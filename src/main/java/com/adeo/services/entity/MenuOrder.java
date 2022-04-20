package com.adeo.services.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "menu_order")
public class MenuOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToMany(
      mappedBy = "menuOrder",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  private List<MenuItemMenuOrder> menuItems = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  private Room room;

  private Double price;

  @Enumerated(EnumType.STRING)
  private PaymentOption paymentOption;

  private Boolean isProcessed = false;

  private LocalDateTime createdAt;

  public void calculatePrice(Double deliveryPrice){
    double orderPrice = menuItems.stream()
        .mapToDouble(MenuItemMenuOrder::calculatePrice)
        .sum();
    this.price = orderPrice + deliveryPrice;
  }

  public void switchIsProcessed(){
    this.isProcessed = !this.isProcessed;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<MenuItemMenuOrder> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItemMenuOrder> menuItems) {
    this.menuItems = menuItems;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public PaymentOption getPaymentOption() {
    return paymentOption;
  }

  public void setPaymentOption(PaymentOption paymentOption) {
    this.paymentOption = paymentOption;
  }

  public Boolean getProcessed() {
    return isProcessed;
  }

  public void setProcessed(Boolean processed) {
    isProcessed = processed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
