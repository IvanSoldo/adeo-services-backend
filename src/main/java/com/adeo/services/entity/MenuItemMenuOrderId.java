package com.adeo.services.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class MenuItemMenuOrderId implements Serializable {

  private int menuItemId;
  private int menuOrderId;

  public int getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(int menuItemId) {
    this.menuItemId = menuItemId;
  }

  public int getMenuOrderId() {
    return menuOrderId;
  }

  public void setMenuOrderId(int menuOrderId) {
    this.menuOrderId = menuOrderId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuItemMenuOrderId menuItemMenuOrderId = (MenuItemMenuOrderId) o;
    return menuItemId == menuItemMenuOrderId.menuItemId && menuOrderId == menuItemMenuOrderId.menuOrderId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(menuItemId, menuOrderId);
  }
}