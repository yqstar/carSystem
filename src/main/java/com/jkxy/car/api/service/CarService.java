package com.jkxy.car.api.service;

import com.jkxy.car.api.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CarService {

    List<Car> findAll();

    Car findById(int id);

    List<Car> findByCarName(String carName);

    void deleteById(int id);

    void updateById(Car car);

    void insertCar(Car car);

    //根据车名、车系查询库存信息
    Car findByCarNameAndCarSeries(String carName, String carSeries);

    //显示的页数 page，每页的条目数size
    List<Car> findCarInfoByWhere(String carName, Integer page, Integer size);
}
