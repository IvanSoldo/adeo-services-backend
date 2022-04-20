package com.adeo.services.controller.menuitem;

import com.adeo.services.entity.MenuItem;
import javax.validation.constraints.NotNull;

public class MenuItemCreateRequest {

  @NotNull
  private String nameHr;
  @NotNull
  private String nameDe;
  @NotNull
  private String nameIt;
  @NotNull
  private String nameEn;
  private String descriptionHr;
  private String descriptionDe;
  private String descriptionIt;
  private String descriptionEn;
  @NotNull
  private Double price;
  private String normative;

  public MenuItem from(){
    MenuItem menuItem = new MenuItem();

    menuItem.setNameHr(this.nameHr);
    menuItem.setNameDe(this.nameDe);
    menuItem.setNameIt(this.nameIt);
    menuItem.setNameEn(this.nameEn);

    menuItem.setDescriptionHr(this.descriptionHr);
    menuItem.setDescriptionDe(this.descriptionDe);
    menuItem.setDescriptionIt(this.descriptionIt);
    menuItem.setDescriptionEn(this.descriptionEn);

    menuItem.setPrice(this.price);

    menuItem.setNormative(this.normative);

    return menuItem;
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
