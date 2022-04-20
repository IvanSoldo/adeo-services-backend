package com.adeo.services.controller.menuitem;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.service.menuitem.MenuItemNotFoundException;
import com.adeo.services.service.menuitem.MenuItemService;
import com.adeo.services.service.menuitem.MenuNotFoundException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuItemController {

  private final MenuItemService menuItemService;

  @Autowired
  public MenuItemController(MenuItemService menuItemService) {
    this.menuItemService = menuItemService;
  }

  @Secured("ROLE_ADMIN")
  @PostMapping("api/v1/menu/{id}/menu-item")
  public MenuItemCreateResponse createMenuItem(@PathVariable("id") Integer id,
      @Valid @RequestBody MenuItemCreateRequest menuItemCreateRequest) {

    Integer menuItemId = menuItemService.create(menuItemCreateRequest.from(), id);

    return new MenuItemCreateResponse(menuItemId);
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("api/v1/menu-item/{id}")
  public void deleteMenuItem(@PathVariable("id") Integer id) {
    menuItemService.delete(id);
  }

  @Secured("ROLE_ADMIN")
  @PatchMapping("api/v1/menu-item/{id}")
  public void updateMenuItem(@PathVariable("id") Integer id, @Valid @RequestBody MenuItemUpdateRequest menuItemUpdateRequest) {
    menuItemService.update(menuItemUpdateRequest.from(id));
  }

  @ExceptionHandler(MenuItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleMenuItemNotFound(MenuItemNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(MenuNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleMenuNotFound(MenuNotFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

}
