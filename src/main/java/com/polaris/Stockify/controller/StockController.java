package com.polaris.Stockify.controller;

import com.polaris.Stockify.annotations.LogExecutionTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @GetMapping("/{id}")
//    @LogExecutionTime
    public void getStockById(@PathVariable String id) throws InterruptedException {
        System.out.println("Get Stock Method actually called");
    }
}
