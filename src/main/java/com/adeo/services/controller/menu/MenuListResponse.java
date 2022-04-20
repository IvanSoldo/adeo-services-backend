package com.adeo.services.controller.menu;

import com.adeo.services.entity.Menu;
import com.adeo.services.entity.MenuItem;
import java.util.List;
import java.util.stream.Collectors;

public class MenuListResponse {

  private List<MenuResponse> menus;

  public static MenuListResponse from(List<Menu> menus) {
    MenuListResponse menuItemResponse = new MenuListResponse();

    List<MenuResponse> menuResponses = menus.stream().map(MenuResponse::from).collect(Collectors.toList());

    menuItemResponse.setMenus(menuResponses);

    return menuItemResponse;
  }

  public List<MenuResponse> getMenus() {
    return menus;
  }

  public void setMenus(List<MenuResponse> menus) {
    this.menus = menus;
  }
}

class MenuResponse {

  private Integer id;
  private String nameHr;
  private String nameDe;
  private String nameIt;
  private String nameEn;
  private List<MenuItemResponse> menuItems;

  public static MenuResponse from(Menu menu) {
    MenuResponse menuResponse = new MenuResponse();

    menuResponse.setId(menu.getId());

    menuResponse.setNameHr(menu.getNameHr());
    menuResponse.setNameDe(menu.getNameDe());
    menuResponse.setNameIt(menu.getNameIt());
    menuResponse.setNameEn(menu.getNameEn());

    List<MenuItemResponse> menuItemResponses = menu.getMenuItems().stream().map(MenuItemResponse::from)
        .collect(Collectors.toList());

    menuResponse.setMenuItems(menuItemResponses);

    return menuResponse;

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNameHr() {
    return nameHr;
  }

  public void setNameHr(String nameHr) {
    this.nameHr = nameHr;
  }

  public String getNameDe() {
    return nameDe;
  }

  public void setNameDe(String nameDe) {
    this.nameDe = nameDe;
  }

  public String getNameIt() {
    return nameIt;
  }

  public void setNameIt(String nameIt) {
    this.nameIt = nameIt;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public List<MenuItemResponse> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItemResponse> menuItems) {
    this.menuItems = menuItems;
  }
}

class MenuItemResponse {

  private Integer id;
  private String nameHr;
  private String nameDe;
  private String nameIt;
  private String nameEn;
  private String descriptionHr;
  private String descriptionDe;
  private String descriptionIt;
  private String descriptionEn;
  private Double price;
  private String normative;

  public static MenuItemResponse from(MenuItem menuItem) {
    MenuItemResponse menuItemResponse = new MenuItemResponse();

    menuItemResponse.setId(menuItem.getId());

    menuItemResponse.setNameHr(menuItem.getNameHr());
    menuItemResponse.setNameDe(menuItem.getNameDe());
    menuItemResponse.setNameIt(menuItem.getNameIt());
    menuItemResponse.setNameEn(menuItem.getNameEn());

    menuItemResponse.setDescriptionHr(menuItem.getDescriptionHr());
    menuItemResponse.setDescriptionDe(menuItem.getDescriptionDe());
    menuItemResponse.setDescriptionIt(menuItem.getDescriptionIt());
    menuItemResponse.setDescriptionEn(menuItem.getDescriptionEn());

    menuItemResponse.setPrice(menuItem.getPrice());

    menuItemResponse.setNormative(menuItem.getNormative());

    return menuItemResponse;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNameHr() {
    return nameHr;
  }

  public void setNameHr(String nameHr) {
    this.nameHr = nameHr;
  }

  public String getNameDe() {
    return nameDe;
  }

  public void setNameDe(String nameDe) {
    this.nameDe = nameDe;
  }

  public String getNameIt() {
    return nameIt;
  }

  public void setNameIt(String nameIt) {
    this.nameIt = nameIt;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getDescriptionHr() {
    return descriptionHr;
  }

  public void setDescriptionHr(String descriptionHr) {
    this.descriptionHr = descriptionHr;
  }

  public String getDescriptionDe() {
    return descriptionDe;
  }

  public void setDescriptionDe(String descriptionDe) {
    this.descriptionDe = descriptionDe;
  }

  public String getDescriptionIt() {
    return descriptionIt;
  }

  public void setDescriptionIt(String descriptionIt) {
    this.descriptionIt = descriptionIt;
  }

  public String getDescriptionEn() {
    return descriptionEn;
  }

  public void setDescriptionEn(String descriptionEn) {
    this.descriptionEn = descriptionEn;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getNormative() {
    return normative;
  }

  public void setNormative(String normative) {
    this.normative = normative;
  }
}