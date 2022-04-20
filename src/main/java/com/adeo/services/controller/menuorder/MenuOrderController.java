package com.adeo.services.controller.menuorder;

import com.adeo.services.controller.AdeoErrorMessage;
import com.adeo.services.entity.MenuOrder;
import com.adeo.services.service.firewall.InvalidIpAddressException;
import com.adeo.services.service.firewall.IpAddress;
import com.adeo.services.service.menuorder.MenuOrderFoundException;
import com.adeo.services.service.menuorder.MenuOrderInvalidTimeException;
import com.adeo.services.service.menuorder.MenuOrderService;
import com.adeo.services.service.menuorder.MenuOrderStatus;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuOrderController {

  private final MenuOrderService menuOrderService;

  public MenuOrderController(MenuOrderService menuOrderService) {
    this.menuOrderService = menuOrderService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/menu-order")
  public MenuOrderResponseList getMenuOrders(Pageable pageable, @RequestParam MenuOrderStatus menuOrderStatus) {

    Page<MenuOrder> menuOrders = menuOrderService.getAll(pageable, menuOrderStatus);

    return MenuOrderResponseList.from(menuOrders.getContent(), menuOrders.getTotalPages());
  }

  @PostMapping("api/v1/menu-order/room/{id}")
  public MenuOrderCreatedResponse createMenuOrder(
      @PathVariable("id") String id,
      @Valid @RequestBody MenuOrderCreateRequest menuOrderCreateRequest,
      HttpServletRequest request) {

    IpAddress ipAddress = new IpAddress(request.getRemoteAddr());

    Integer menuOrderId = menuOrderService.create(menuOrderCreateRequest.from(), id, ipAddress);

    return new MenuOrderCreatedResponse(menuOrderId);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @DeleteMapping("api/v1/menu-order/{id}")
  public void deleteMenuOrder(@PathVariable("id") Integer id) {
    menuOrderService.delete(id);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/menu-order/unprocessed")
  public MenuOrderUnprocessedCountResponse getUnprocessedMenuOrdersCount() {
    Integer menuOrderUnprocessedCount = menuOrderService.geUnprocessedCount();

    return new MenuOrderUnprocessedCountResponse(menuOrderUnprocessedCount);
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @PatchMapping("api/v1/menu-order/{id}/process")
  public void switchMenuOrderProcessed(@PathVariable("id") Integer id) {
    menuOrderService.switchProcessed(id);
  }

  @ExceptionHandler(MenuOrderFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  AdeoErrorMessage handleMenuNotFound(MenuOrderFoundException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(InvalidIpAddressException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  AdeoErrorMessage handleInvalidIpAddressException(InvalidIpAddressException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

  @ExceptionHandler(MenuOrderInvalidTimeException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  AdeoErrorMessage handleIMenuOrderInvalidTimeException(MenuOrderInvalidTimeException e) {
    return new AdeoErrorMessage(e.getMessage());
  }

}
