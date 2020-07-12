package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok();
    }

    /**
     * 购买车辆
     *
     * @param quantity 数量
     * @return
     */

    @PostMapping("purchaseCars/{carName}/{carSeries}/{inventory}")
    public JSONResult purchaseCars(
            @PathVariable("carName") String carName,
            @PathVariable("carSeries") String carSeries,
            @PathVariable("inventory") Integer inventory) {

        //根据客户购买需求查询库存信息CarInfo
        Car carInfo = carService.findByCarNameAndCarSeries(carName, carSeries);
        //当库存数量 小于 当前购买需求数量时，提示库存不足，暂不支持购买

        if (carInfo.getInventory() < inventory) {
            return JSONResult.errorException("库存不足，暂不支持购买");
        } else {
            //满足购买要求，将库存量进行修改
            int remainInventory = carInfo.getInventory() - inventory;
            carInfo.setInventory(remainInventory);
            carService.updateById(carInfo);
            return JSONResult.ok();
        }
    }

    /**
     * 通过车名模糊分页查询
     *
     * @param carName
     * @param page    显示的页数
     * @param size    每页的条目数
     * @return
     */
    @GetMapping("findByWhere/{carName}/{page}/{size}")
    public JSONResult findByWhere(@PathVariable String carName,
                                  @PathVariable Integer page,
                                  @PathVariable Integer size) {
        if (page >= 1) {
            List<Car> cars = carService.findCarInfoByWhere(carName, (page - 1) * size, size);
            return JSONResult.ok(cars);
        } else {
            return JSONResult.errorMsg("输入参数不合法");
        }
    }


}
