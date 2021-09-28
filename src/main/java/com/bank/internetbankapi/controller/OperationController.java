package com.bank.internetbankapi.controller;

import com.bank.internetbankapi.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.lang.System.lineSeparator;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/operations")
public class OperationController {

    private final OperationService operationService;

    @GetMapping("/{userId}/balance")
    public BigDecimal getBalance(
                      @PathVariable final UUID userId) throws Exception {
        log.info("Handled /operations/{}/balance GET HTTP request", userId);
        BigDecimal result = operationService.getBalance(userId);
        log.info("Produced HTTP 200 response for /operations/{}/balance GET:{}{}", userId, lineSeparator(), result);
        return result;
    }

    @PutMapping("/{userId}/balance/take")
    public void takeMoney(
            @PathVariable final UUID userId,
            @RequestParam final BigDecimal amount) throws Exception {
        log.info("Handled /operations/{}/balance/take PUT HTTP request", userId);
        operationService.takeMoney(userId, amount);
        log.info("Produced HTTP 200 response for /operations/{}/balance/take PUT:{}{}", userId, lineSeparator(), "OK");
    }

    @PutMapping("/{userId}/balance/add")
    public void putMoney(
            @PathVariable final UUID userId,
            @RequestParam final BigDecimal amount) throws Exception {
        log.info("Handled /operations/{}/balance/add PUT HTTP request", userId);
        operationService.putMoney(userId, amount);
        log.info("Produced HTTP 200 response for /operations/{}/balance/add PUT:{}{}", userId, lineSeparator(), "OK");
    }

    @GetMapping("/{userId}/list")
    public List<String> getOperationList(
            @PathVariable final UUID userId,
            @RequestParam final LocalDate startDate,
            @RequestParam final LocalDate endDate) {
        log.info("Handled /operations/{}/list GET HTTP request", userId);
        List<String> result = operationService.getOperationList(userId, startDate, endDate);
        log.info("Produced HTTP 200 response for /operations/{}/list GET:{}{}", userId, lineSeparator(), result);
        return result;
    }

    @PutMapping("/{userId}/transfer")
    public List<String> transferMoney(
            @PathVariable final UUID userId,
            @RequestParam final UUID userIdTo,
            @RequestParam final BigDecimal amount) {
        log.info("Handled /operations/{}/transfer PUT HTTP request", userId);
        List<String> result = operationService.transferMoney(userId, userIdTo, amount);
        log.info("Produced HTTP 200 response for /operations/{}/transfer PUT:{}{}", userId, lineSeparator(), result);
        return result;
    }
}
