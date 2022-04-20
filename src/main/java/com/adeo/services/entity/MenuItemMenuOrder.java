package com.adeo.services.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity(name = "menu_item_menu_order")
public class MenuItemMenuOrder {

  @EmbeddedId
  private MenuItemMenuOrderId id = new MenuItemMenuOrderId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("menuItemId")
  private MenuItem menuItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("menuOrderId")
  private MenuOrder menuOrder;

  private Integer amount;

  public Double calculatePrice(){
    return amount * menuItem.getPrice();
  }

  public MenuItemMenuOrderId getId() {
    return id;
  }

  public void setId(MenuItemMenuOrderId id) {
    this.id = id;
  }

  public MenuItem getMenuItem() {
    return menuItem;
  }

  public void setMenuItem(MenuItem menuItem) {
    this.menuItem = menuItem;
  }

  public MenuOrder getMenuOrder() {
    return menuOrder;
  }

  public void setMenuOrder(MenuOrder menuOrder) {
    this.menuOrder = menuOrder;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}
