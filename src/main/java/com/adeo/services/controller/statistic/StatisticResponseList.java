package com.adeo.services.controller.statistic;

import com.adeo.services.service.statistic.Statistic;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticResponseList {

  private final List<StatisticResponse> statistics;

  private StatisticResponseList(List<StatisticResponse> statistics) {
    this.statistics = statistics;
  }

  public static StatisticResponseList from(List<Statistic> statistics){
    List<StatisticResponse> statisticResponses = statistics.stream().map(StatisticResponse::from).collect(Collectors.toList());
    return new StatisticResponseList(statisticResponses);
  }

  public List<StatisticResponse> getStatistics() {
    return statistics;
  }
}
