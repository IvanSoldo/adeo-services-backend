package com.adeo.services.service.menu;

import com.adeo.services.entity.Menu;
import com.adeo.services.repository.MenuRepository;
import com.adeo.services.service.menuitem.MenuItemNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;

  public MenuServiceImpl(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public List<Menu> getAllMenus() {
    return menuRepository.findByIsDeletedFalse();
  }

  @Override
  public Integer createMenu(Menu menu) {

    Menu savedMenu = menuRepository.save(menu);

    return savedMenu.getId();
  }

  @Override
  public void deleteMenu(int menuId) {

    Menu menu = menuRepository.findByIdAndIsDeletedFalse(menuId)
        .orElseThrow(() -> new MenuItemNotFoundException("Menu with id: " + menuId + " not found"));

    menu.setDeleted(true);

    menuRepository.save(menu);

  }

  @Override
  public void updateMenu(Menu menu) {

    Menu menuFromDb = menuRepository.findByIdAndIsDeletedFalse(menu.getId())
        .orElseThrow(() -> new MenuItemNotFoundException("Menu with id: " + menu.getId() + " not found"));

    menuFromDb.update(menu);

    menuRepository.save(menuFromDb);

  }
}
