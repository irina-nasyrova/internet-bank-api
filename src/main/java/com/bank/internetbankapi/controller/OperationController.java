package com.bank.internetbankapi.controller;

import com.bank.internetbankapi.domain.entity.Operation;
import com.bank.internetbankapi.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
            @PathVariable UUID userId) throws Exception {
        log.info("Handled /operations/{}/balance GET HTTP request", userId);
        BigDecimal result = operationService.getBalance(userId);
        log.info("Produced HTTP 200 response for /operations/{}/balance GET:{}{}", userId, lineSeparator(), result);
        return result;
    }

    @PutMapping("/{userId}/balance/take")
    public void takeMoney(
            @PathVariable UUID userId,
            @RequestParam BigDecimal amount) throws Exception {
        log.info("Handled /operations/{}/balance/take PUT HTTP request", userId);
        operationService.takeMoney(userId, amount);
        log.info("Produced HTTP 200 response for /operations/{}/balance/take PUT:{}{}", userId, lineSeparator(), "OK");
    }

    @PutMapping("/{userId}/balance/put")
    public void putMoney(
            @PathVariable UUID userId,
            @RequestParam BigDecimal amount) {
        log.info("Handled /operations/{}/balance/put PUT HTTP request", userId);
        operationService.putMoney(userId, amount);
        log.info("Produced HTTP 200 response for /operations/{}/balance/put PUT:{}{}", userId, lineSeparator(), "OK");
    }

    @GetMapping("/{userId}/list")
    public List<Operation> getOperationList(
            @PathVariable UUID userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Nullable LocalDateTime endDate) {
        log.info("Handled /operations/{}/list GET HTTP request", userId);
        List<Operation> result = operationService.getOperationList(userId, startDate, endDate);
        log.info("Produced HTTP 200 response for /operations/{}/list GET:{}{}", userId, lineSeparator(), result);
        return result;
    }

    @PutMapping("/{userId}/transfer")
    public void transferMoney(
            @PathVariable UUID userId,
            @RequestParam UUID userIdTo,
            @RequestParam BigDecimal amount) {
        log.info("Handled /operations/{}/transfer PUT HTTP request", userId);
        operationService.transferMoney(userId, userIdTo, amount);
        log.info("Produced HTTP 200 response for /operations/{}/transfer PUT:{}{}", userId, lineSeparator(), "OK");
    }
}
