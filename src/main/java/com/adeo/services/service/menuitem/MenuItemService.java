package com.adeo.services.service.menuitem;

import com.adeo.services.entity.MenuItem;

public interface MenuItemService {

  Integer create(MenuItem menuItem, Integer menuId);

  void delete(Integer menuItemId);

  void update(MenuItem menuItem);

}
