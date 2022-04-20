package com.adeo.services.service.statistic;

public class Statistic {

  private final String title;
  private final Integer count;

  public Statistic(String title, Integer count) {
    this.title = title;
    this.count = count;
  }

  public String getTitle() {
    return title;
  }

  public Integer getCount() {
    return count;
  }
}
