package com.adeo.services.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

@Entity(name = "menu_item")
@NaturalIdCache
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
public class MenuItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "menu_id", insertable = false, updatable = true)
  private Menu menu;

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
  private Boolean isDeleted = false;

  public void update(MenuItem menuItem){
    this.nameHr = menuItem.getNameHr();
    this.nameDe = menuItem.getNameDe();
    this.nameIt = menuItem.getNameIt();
    this.nameEn = menuItem.getNameEn();

    this.descriptionHr = menuItem.getDescriptionHr();
    this.descriptionDe = menuItem.getDescriptionDe();
    this.descriptionIt = menuItem.getDescriptionIt();
    this.descriptionEn = menuItem.getDescriptionEn();

    this.price = menuItem.getPrice();

    this.normative = menuItem.getNormative();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Menu getMenu() {
    return menu;
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
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

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }
}
