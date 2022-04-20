package com.adeo.services.controller.menuorder;

import com.adeo.services.entity.MenuItem;
import com.adeo.services.entity.MenuItemMenuOrder;
import com.adeo.services.entity.MenuOrder;
import com.adeo.services.entity.PaymentOption;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MenuOrderCreateRequest {

  @NotNull
  @NotEmpty
  private List<MenuItemOrderCreateRequest> menuItems;
  @NotNull
  private PaymentOptionRequest paymentOption;

  public MenuOrder from(){

    List<MenuItemMenuOrder> menuItemMenuOrders = menuItems.stream()
        .map(MenuItemOrderCreateRequest::from)
        .collect(Collectors.toList());

    MenuOrder menuOrder = new MenuOrder();
    menuOrder.setMenuItems(menuItemMenuOrders);
    menuOrder.setPaymentOption(from(paymentOption));

    return menuOrder;
  }

  private static PaymentOption from(PaymentOptionRequest paymentOptionRequest) {
    Map<PaymentOptionRequest, PaymentOption> paymentOptions = new EnumMap<>(PaymentOptionRequest.class);

    paymentOptions.put(PaymentOptionRequest.CASH, PaymentOption.CASH);
    paymentOptions.put(PaymentOptionRequest.CREDIT_CARD, PaymentOption.CREDIT_CARD);
    paymentOptions.put(PaymentOptionRequest.ROOM, PaymentOption.ROOM);

    PaymentOption paymentOption = paymentOptions.get(paymentOptionRequest);

    if (paymentOption == null) {
      throw new IllegalArgumentException("Non existing PaymentOption");
    }

    return paymentOption;
  }

  public List<MenuItemOrderCreateRequest> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItemOrderCreateRequest> menuItems) {
    this.menuItems = menuItems;
  }

  public PaymentOptionRequest getPaymentOption() {
    return paymentOption;
  }

  public void setPaymentOption(PaymentOptionRequest paymentOption) {
    this.paymentOption = paymentOption;
  }
}

class MenuItemOrderCreateRequest{

  @NotNull
  private Integer id;
  @NotNull
  private Integer amount;

  public MenuItemMenuOrder from(){
    MenuItem menuItem = new MenuItem();
    menuItem.setId(id);

    MenuItemMenuOrder menuItemMenuOrder = new MenuItemMenuOrder();
    menuItemMenuOrder.setMenuItem(menuItem);
    menuItemMenuOrder.setAmount(amount);

    return menuItemMenuOrder;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }
}

enum PaymentOptionRequest{
  CASH, CREDIT_CARD, ROOM
}
