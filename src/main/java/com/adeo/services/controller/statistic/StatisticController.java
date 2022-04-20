package com.adeo.services.controller.statistic;

import com.adeo.services.service.statistic.Statistic;
import com.adeo.services.service.statistic.StatisticService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

  private final StatisticService statisticService;

  @Autowired
  public StatisticController(StatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @Secured({"ROLE_ADMIN", "ROLE_RECEPTIONIST"})
  @GetMapping("api/v1/statistics")
  public StatisticResponseList getAll() {
    List<Statistic> statistics = statisticService.getAll();
    return StatisticResponseList.from(statistics);
  }

}
