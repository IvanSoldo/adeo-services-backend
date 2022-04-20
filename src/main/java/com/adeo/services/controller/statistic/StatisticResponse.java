package com.adeo.services.controller.statistic;

import com.adeo.services.service.statistic.Statistic;

public class StatisticResponse {

  private final String title;
  private final Integer count;

  private StatisticResponse(String title, Integer count) {
    this.title = title;
    this.count = count;
  }

  public static StatisticResponse from(Statistic statistic){
    return new StatisticResponse(statistic.getTitle(), statistic.getCount());
  }

  public String getTitle() {
    return title;
  }

  public Integer getCount() {
    return count;
  }

}
