package com.adeo.services.service.menu;

import com.adeo.services.entity.Menu;
import java.util.List;

public interface MenuService {

  List<Menu> getAllMenus();

  Integer createMenu(Menu menu);

  void deleteMenu(int id);

  void updateMenu(Menu menu);

}
