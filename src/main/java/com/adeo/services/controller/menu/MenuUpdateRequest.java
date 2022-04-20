package com.adeo.services.controller.menu;

import com.adeo.services.entity.Menu;
import javax.validation.constraints.NotNull;

public class MenuUpdateRequest {

  @NotNull
  private String nameHr;
  @NotNull
  private String nameDe;
  @NotNull
  private String nameIt;
  @NotNull
  private String nameEn;

  public Menu from(int id){
    Menu menu = new Menu();

    menu.setId(id);

    menu.setNameHr(this.nameHr);
    menu.setNameDe(this.nameDe);
    menu.setNameIt(this.nameIt);
    menu.setNameEn(this.nameEn);

    return menu;
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
}
