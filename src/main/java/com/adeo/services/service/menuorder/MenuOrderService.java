package com.adeo.services.service.menuorder;

import com.adeo.services.entity.MenuOrder;
import com.adeo.services.service.firewall.IpAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuOrderService {

  Integer geUnprocessedCount();

  void switchProcessed(Integer menuOrderId);

  void delete(Integer menuOrderId);

  Integer create(MenuOrder menuOrder, String roomId, IpAddress ipAddress);

  Page<MenuOrder> getAll(Pageable pageable, MenuOrderStatus menuOrderStatus);

}
