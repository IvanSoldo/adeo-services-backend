package com.adeo.services.service.menuorder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class FoodServiceWorkingTimeTest {

  private FoodServiceWorkingTime foodServiceWorkingTime;

  @Test
  void given_Valid_CurrentWorkingTime_For_FirstShift_Then_Return_True() {
    // prepare
    LocalTime firstShiftStart = LocalTime.now().minusHours(1);
    LocalTime firstShiftEnd = LocalTime.now().plusHours(1);
    WorkingTime firstShiftWorkingTime = new WorkingTime(firstShiftStart, firstShiftEnd);

    LocalTime secondShiftStart = LocalTime.now().plusHours(3);
    LocalTime secondShiftEnd = LocalTime.now().plusHours(6);
    WorkingTime secondShiftWorkingTime = new WorkingTime(secondShiftStart, secondShiftEnd);

    foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime, secondShiftWorkingTime);

    // execute
    boolean canOrderMenu = foodServiceWorkingTime.canOrderFood();

    // verify
    assertThat(canOrderMenu).isTrue();
  }

  @Test
  void given_Valid_CurrentWorkingTime_For_SecondShift_Then_Return_True() {
    // prepare
    LocalTime firstShiftStart = LocalTime.now().minusHours(6);
    LocalTime firstShiftEnd = LocalTime.now().minusHours(3);
    WorkingTime firstShiftWorkingTime = new WorkingTime(firstShiftStart, firstShiftEnd);

    LocalTime secondShiftStart = LocalTime.now().minusHours(1);
    LocalTime secondShiftEnd = LocalTime.now().plusHours(1);
    WorkingTime secondShiftWorkingTime = new WorkingTime(secondShiftStart, secondShiftEnd);

    foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime, secondShiftWorkingTime);

    // execute
    boolean canOrderMenu = foodServiceWorkingTime.canOrderFood();

    // verify
    assertThat(canOrderMenu).isTrue();
  }

  @Test
  void given_Invalid_CurrentWorkingTime_In_Past_For_BothShift_Then_Return_False() {
    // prepare
    LocalTime firstShiftStart = LocalTime.now().minusHours(5);
    LocalTime firstShiftEnd = LocalTime.now().minusHours(3);
    WorkingTime firstShiftWorkingTime = new WorkingTime(firstShiftStart, firstShiftEnd);

    LocalTime secondShiftStart = LocalTime.now().minusHours(2);
    LocalTime secondShiftEnd = LocalTime.now().minusHours(1);
    WorkingTime secondShiftWorkingTime = new WorkingTime(secondShiftStart, secondShiftEnd);

    foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime, secondShiftWorkingTime);

    // execute
    boolean canOrderMenu = foodServiceWorkingTime.canOrderFood();

    // verify
    assertThat(canOrderMenu).isFalse();
  }

  @Test
  void given_Invalid_CurrentWorkingTime_In_Future_For_BothShift_Then_Return_False() {
    // prepare
    LocalTime firstShiftStart = LocalTime.now().plusHours(1);
    LocalTime firstShiftEnd = LocalTime.now().plusHours(2);
    WorkingTime firstShiftWorkingTime = new WorkingTime(firstShiftStart, firstShiftEnd);

    LocalTime secondShiftStart = LocalTime.now().plusHours(5);
    LocalTime secondShiftEnd = LocalTime.now().plusHours(8);
    WorkingTime secondShiftWorkingTime = new WorkingTime(secondShiftStart, secondShiftEnd);

    foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime, secondShiftWorkingTime);

    // execute
    boolean canOrderMenu = foodServiceWorkingTime.canOrderFood();

    // verify
    assertThat(canOrderMenu).isFalse();
  }

  @Test
  void given_Invalid_CurrentWorkingTime_In_Where_CurrentTime_Is_Between_Shifts_Then_Return_False() {
    // prepare
    LocalTime firstShiftStart = LocalTime.now().minusHours(5);
    LocalTime firstShiftEnd = LocalTime.now().minusHours(4);
    WorkingTime firstShiftWorkingTime = new WorkingTime(firstShiftStart, firstShiftEnd);

    LocalTime secondShiftStart = LocalTime.now().plusHours(5);
    LocalTime secondShiftEnd = LocalTime.now().plusHours(8);
    WorkingTime secondShiftWorkingTime = new WorkingTime(secondShiftStart, secondShiftEnd);

    foodServiceWorkingTime = new FoodServiceWorkingTime(firstShiftWorkingTime, secondShiftWorkingTime);

    // execute
    boolean canOrderMenu = foodServiceWorkingTime.canOrderFood();

    // verify
    assertThat(canOrderMenu).isFalse();
  }

}