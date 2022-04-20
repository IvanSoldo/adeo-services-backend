package com.adeo.services.service.menuorder;

import com.adeo.services.entity.MenuItem;
import com.adeo.services.entity.MenuItemMenuOrder;
import com.adeo.services.entity.MenuOrder;
import com.adeo.services.entity.Room;
import com.adeo.services.repository.MenuItemRepository;
import com.adeo.services.repository.MenuOrderRepository;
import com.adeo.services.repository.RoomRepository;
import com.adeo.services.service.firewall.FirewallService;
import com.adeo.services.service.firewall.InvalidIpAddressException;
import com.adeo.services.service.firewall.IpAddress;
import com.adeo.services.service.menuitem.MenuItemNotFoundException;
import com.adeo.services.service.room.RoomNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MenuOrderServiceImpl implements MenuOrderService {

  private static final Double DELIVERY_PRICE = 40.0;

  private final MenuOrderRepository menuOrderRepository;
  private final MenuItemRepository menuItemRepository;
  private final RoomRepository roomRepository;
  private final FirewallService firewallService;

  public MenuOrderServiceImpl(
      MenuOrderRepository menuOrderRepository,
      MenuItemRepository menuItemRepository,
      RoomRepository repository,
      FirewallService firewallService) {
    this.menuOrderRepository = menuOrderRepository;
    this.menuItemRepository = menuItemRepository;
    this.roomRepository = repository;
    this.firewallService = firewallService;
  }

  @Override
  public Integer geUnprocessedCount() {
    return menuOrderRepository.countByIsProcessedFalse();
  }

  @Override
  public void switchProcessed(Integer menuOrderId) {
    MenuOrder menuOrder = menuOrderRepository.findById(menuOrderId)
        .orElseThrow(() -> new MenuOrderFoundException("Menu order not found with id: " + menuOrderId));

    menuOrder.switchIsProcessed();

    menuOrderRepository.save(menuOrder);
  }

  @Override
  public void delete(Integer menuOrderId) {
    MenuOrder menuOrder = menuOrderRepository.findById(menuOrderId)
        .orElseThrow(() -> new MenuOrderFoundException("Menu order not found with id: " + menuOrderId));

    menuOrderRepository.delete(menuOrder);
  }

  @Override
  public Integer create(MenuOrder menuOrder, String roomId, IpAddress ipAddress) {

    boolean hasFirewallPermission = firewallService.hasPermission(ipAddress);
    if (!hasFirewallPermission) {
      throw new InvalidIpAddressException("Invalid ip address");
    }

    WorkingTime firstShiftWorkingTime = new WorkingTime(LocalTime.of(12, 0), LocalTime.of(17, 45));
    WorkingTime secondShiftWorkingTime = new WorkingTime(LocalTime.of(21, 0), LocalTime.of(22, 45));

    FoodServiceWorkingTime foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime,
        secondShiftWorkingTime);

    if (!foodServiceWorkingTime.canOrderFood()) {
      throw new MenuOrderInvalidTimeException("Room service is not working in this hours.");
    }

    Room room = roomRepository.findByUuidAndIsDeletedFalse(roomId)
        .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

    menuOrder.setRoom(room);

    List<Integer> menuItemsId = menuOrder.getMenuItems().stream()
        .map(menuItemMenuOrder -> menuItemMenuOrder.getMenuItem().getId())
        .collect(Collectors.toList());

    boolean isMenuItemsExist = menuItemRepository.existsByIdIn(menuItemsId);

    if (!isMenuItemsExist) {
      throw new MenuItemNotFoundException("MenuItems with ids: " + menuItemsId + " not found");
    }

    for (MenuItemMenuOrder menuItemMenuOrder : menuOrder.getMenuItems()) {
      int menuItemId = menuItemMenuOrder.getMenuItem().getId();
      MenuItem menuItemFromDb = menuItemRepository.findByIdAndIsDeletedFalse(menuItemId)
          .orElseThrow(() -> new MenuItemNotFoundException("MenuItem with id: " + menuItemId + " not found"));

      menuItemMenuOrder.setMenuItem(menuItemFromDb);
      menuItemMenuOrder.setMenuOrder(menuOrder);
    }

    menuOrder.calculatePrice(DELIVERY_PRICE);
    menuOrder.setCreatedAt(LocalDateTime.now(ZoneId.of("CET")));

    MenuOrder savedMenuOrder = menuOrderRepository.save(menuOrder);

    return savedMenuOrder.getId();
  }

  @Override
  public Page<MenuOrder> getAll(Pageable pageable, MenuOrderStatus menuOrderStatus) {

    Pageable sortedByCreatedAt = PageRequest
        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

    if (menuOrderStatus == MenuOrderStatus.PROCESSED) {
      return menuOrderRepository.findByIsProcessedTrue(sortedByCreatedAt);
    } else if (menuOrderStatus == MenuOrderStatus.UNPROCESSED) {
      return menuOrderRepository.findByIsProcessedFalse(sortedByCreatedAt);
    } else {
      return menuOrderRepository.findAll(sortedByCreatedAt);
    }
  }
}
