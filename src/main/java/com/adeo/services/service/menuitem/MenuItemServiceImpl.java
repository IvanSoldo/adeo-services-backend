package com.adeo.services.service.menuitem;

import com.adeo.services.entity.Menu;
import com.adeo.services.entity.MenuItem;
import com.adeo.services.repository.MenuItemRepository;
import com.adeo.services.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemServiceImpl implements MenuItemService {

  private final MenuItemRepository menuItemRepository;
  private final MenuRepository menuRepository;

  @Autowired
  public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuRepository menuRepository) {
    this.menuItemRepository = menuItemRepository;
    this.menuRepository = menuRepository;
  }

  @Override
  public Integer create(MenuItem menuItem, Integer menuId) {

    Menu menu = menuRepository.findByIdAndIsDeletedFalse(menuId)
        .orElseThrow(() -> new MenuItemNotFoundException("Menu with id: " + menuId + " not found"));

    menuItem.setMenu(menu);

    menu.addMenuItem(menuItem);

    MenuItem savedMenuItem = menuItemRepository.save(menuItem);

    menuRepository.save(menu);

    return savedMenuItem.getId();
  }

  @Override
  public void delete(Integer menuItemId) {
    MenuItem menuItem = menuItemRepository.findByIdAndIsDeletedFalse(menuItemId)
        .orElseThrow(() -> new MenuItemNotFoundException("MenuItem with id: " + menuItemId + " not found"));

    menuItem.setDeleted(true);

    menuItemRepository.save(menuItem);

  }

  @Override
  public void update(MenuItem menuItem) {

    MenuItem menuItemFromDb = menuItemRepository.findByIdAndIsDeletedFalse(menuItem.getId())
        .orElseThrow(() -> new MenuItemNotFoundException("MenuItem with id: " + menuItem.getId() + " not found"));

    menuItemFromDb.update(menuItem);

    menuItemRepository.save(menuItemFromDb);
  }
}
