package com.adeo.services.service.statistic;

import com.adeo.services.repository.OrderRepository;
import com.adeo.services.repository.RoomRepository;
import com.adeo.services.repository.ServiceTypeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Service;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class StatisticServiceImpl implements StatisticService {

  private final OrderRepository orderRepository;
  private final ServiceTypeRepository serviceTypeRepository;
  private final RoomRepository roomRepository;

  public StatisticServiceImpl(OrderRepository orderRepository,
      ServiceTypeRepository serviceTypeRepository, RoomRepository roomRepository) {
    this.orderRepository = orderRepository;
    this.serviceTypeRepository = serviceTypeRepository;
    this.roomRepository = roomRepository;
  }

  @Override
  public List<Statistic> getAll() {

    LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("CET"));

    int ordersCount = (int) orderRepository.count();
    int completedOrdersCount = orderRepository.countByEndAtLessThanEqual(currentDateTime);
    int pendingOrdersCount = orderRepository.countByStartAtGreaterThanEqual(currentDateTime);
    int occurringOrdersCount = orderRepository.countByStartAtLessThanEqualAndEndAtGreaterThanEqual(currentDateTime, currentDateTime);
    int serviceTypeCount = (int) serviceTypeRepository.count();
    int roomsCount = (int) roomRepository.count();

    LocalDateTime todayAtStartOfDay = LocalDate.now(ZoneId.of("CET")).atStartOfDay();
    LocalDateTime tomorrowAtStartOfDay = todayAtStartOfDay.plusDays(1);

    int todayOrdersCreatedCount = orderRepository.countByCreatedAtBetween(todayAtStartOfDay, tomorrowAtStartOfDay);

    LocalDateTime firstDayOfThisYear = LocalDate.now(ZoneId.of("CET")).with(firstDayOfYear()).atStartOfDay();
    LocalDateTime lastDayOfThisYear = LocalDate.now(ZoneId.of("CET")).with(lastDayOfYear()).plusDays(1).atStartOfDay();

    int thisYearOrdersCreatedCount = orderRepository.countByCreatedAtBetween(firstDayOfThisYear, lastDayOfThisYear);

    return List.of(new Statistic("Total Orders count", ordersCount),
        new Statistic("Total Completed Orders", completedOrdersCount),
        new Statistic("Total Pending Orders", pendingOrdersCount),
        new Statistic("Total Occurring Orders", occurringOrdersCount),
        new Statistic("Total Services Offering", serviceTypeCount),
        new Statistic("Total Rooms", roomsCount),
        new Statistic("Total Today Orders", todayOrdersCreatedCount),
        new Statistic("Total Orders this year", thisYearOrdersCreatedCount));
  }
}
