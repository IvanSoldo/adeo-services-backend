package com.adeo.services.controller.menuorder;

import com.adeo.services.entity.MenuItem;
import com.adeo.services.entity.MenuItemMenuOrder;
import com.adeo.services.entity.MenuOrder;
import com.adeo.services.entity.PaymentOption;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuOrderResponseList {

  private final Integer totalPages;
  private final List<MenuOrderResponse> menuOrders;

  private MenuOrderResponseList(Integer totalPages, List<MenuOrderResponse> menuOrders) {
    this.totalPages = totalPages;
    this.menuOrders = menuOrders;
  }

  public static MenuOrderResponseList from(List<MenuOrder> menuOrders, Integer totalPages) {
    List<MenuOrderResponse> menuItemsResponse = menuOrders.stream()
        .map(MenuOrderResponse::from)
        .collect(Collectors.toList());

    return new MenuOrderResponseList(totalPages, menuItemsResponse);
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public List<MenuOrderResponse> getMenuOrders() {
    return menuOrders;
  }
}

class MenuOrderResponse {

  private final Integer id;
  private final Double totalPrice;
  private final PaymentOptionResponse paymentOption;
  private final Boolean isProcessed;
  private final List<MenuItemResponse> menuItems;
  private final String roomNumber;
  private final String createdAt;

  private MenuOrderResponse(Integer id, List<MenuItemResponse> menuItems,
      Double totalPrice, PaymentOptionResponse paymentOption, Boolean isProcessed, String roomNumber,
      String createdAt) {
    this.id = id;
    this.menuItems = menuItems;
    this.totalPrice = totalPrice;
    this.paymentOption = paymentOption;
    this.isProcessed = isProcessed;
    this.roomNumber = roomNumber;
    this.createdAt = createdAt;
  }

  public static MenuOrderResponse from(MenuOrder menuOrder) {
    List<MenuItemResponse> menuItemsResponse = menuOrder.getMenuItems().stream()
        .map(MenuItemResponse::from)
        .collect(Collectors.toList());

    return new MenuOrderResponse(menuOrder.getId(), menuItemsResponse, menuOrder.getPrice(), from(menuOrder.getPaymentOption()),
        menuOrder.getProcessed(), menuOrder.getRoom().getNumber(), menuOrder.getCreatedAt().toString());
  }

  private static PaymentOptionResponse from(PaymentOption paymentOption) {
    Map<PaymentOption, PaymentOptionResponse> paymentOptions = new EnumMap<>(PaymentOption.class);

    paymentOptions.put(PaymentOption.CASH, PaymentOptionResponse.CASH);
    paymentOptions.put(PaymentOption.CREDIT_CARD, PaymentOptionResponse.CREDIT_CARD);
    paymentOptions.put(PaymentOption.ROOM, PaymentOptionResponse.ROOM);

    PaymentOptionResponse paymentOptionResponse = paymentOptions.get(paymentOption);

    if (paymentOptionResponse == null) {
      throw new IllegalArgumentException("Non existing PaymentOptionResponse");
    }

    return paymentOptionResponse;
  }

  public Integer getId() {
    return id;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public PaymentOptionResponse getPaymentOption() {
    return paymentOption;
  }

  public Boolean getProcessed() {
    return isProcessed;
  }

  public List<MenuItemResponse> getMenuItems() {
    return menuItems;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public String getCreatedAt() {
    return createdAt;
  }
}

class MenuItemResponse {

  private final String name;
  private final String description;
  private final Double totalPrice;
  private final String normative;
  private final Integer amount;

  public MenuItemResponse(String name, String description, Double pricePerMenuItem, String normative,
      Integer amount) {
    this.name = name;
    this.description = description;
    this.totalPrice = pricePerMenuItem * amount;
    this.normative = normative;
    this.amount = amount;
  }

  public static MenuItemResponse from(MenuItemMenuOrder menuItemMenuOrder) {
    MenuItem menuItem = menuItemMenuOrder.getMenuItem();
    return new MenuItemResponse(
        menuItem.getNameHr(),
        menuItem.getDescriptionHr(),
        menuItem.getPrice(),
        menuItem.getNormative(),
        menuItemMenuOrder.getAmount());
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public String getNormative() {
    return normative;
  }

  public Integer getAmount() {
    return amount;
  }
}

enum PaymentOptionResponse {
  CASH, CREDIT_CARD, ROOM
}