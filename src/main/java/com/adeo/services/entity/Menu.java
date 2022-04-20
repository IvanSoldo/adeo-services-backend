package com.adeo.services.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Where;

@Entity(name = "menu")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToMany(orphanRemoval = true)
  @JoinColumn(name = "menu_id")
  @Where(clause = "is_deleted = false")
  private List<MenuItem> menuItems = new ArrayList<>();

  private String nameHr;
  private String nameDe;
  private String nameIt;
  private String nameEn;
  private Boolean isDeleted = false;

  public void addMenuItem(MenuItem menuItem){
    this.menuItems.add(menuItem);
  }

  public void update(Menu menu){
    this.nameHr = menu.getNameHr();
    this.nameDe = menu.getNameDe();
    this.nameIt = menu.getNameIt();
    this.nameEn = menu.getNameEn();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
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

  public Boolean getDeleted() {
    return isDeleted;
  }

  public void setDeleted(Boolean deleted) {
    isDeleted = deleted;
  }
}
