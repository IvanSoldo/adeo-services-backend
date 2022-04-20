package com.adeo.services.controller.menu;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.Menu;
import com.adeo.services.service.menu.MenuService;
import com.adeo.services.service.menuitem.MenuNotFoundException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

  private final MenuService menuService;

  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  @GetMapping("api/v1/menu/menu-item")
  public MenuListResponse getAllMenusWithMenuItems() {
    List<Menu> menus = menuService.getAllMenus();

    return MenuListResponse.from(menus);
  }

  @Secured("ROLE_ADMIN")
  @PostMapping("api/v1/menu")
  public MenuCreateResponse createMenu(@Valid @RequestBody MenuCreateRequest menuCreateRequest) {
    Menu menu = menuCreateRequest.from();
    Integer menuId = menuService.createMenu(menu);

    return new MenuCreateResponse(menuId);
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("api/v1/menu/{id}")
  public void deleteMenu(@PathVariable("id") Integer id) {
    menuService.deleteMenu(id);
  }

  @Secured("ROLE_ADMIN")
  @PatchMapping("api/v1/menu/{id}")
  public void updateMenu(@PathVariable("id") Integer id, @Valid @RequestBody MenuUpdateRequest menuUpdateRequest) {
    Menu menu = menuUpdateRequest.from(id);
    menuService.updateMenu(menu);
  }

  @ExceptionHandler(MenuNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleMenuNotFound(MenuNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

}
