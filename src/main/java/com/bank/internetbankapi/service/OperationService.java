package com.bank.internetbankapi.service;

import com.bank.internetbankapi.domain.entity.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OperationService {

    /**
     * Получение баланса по ID пользователя
     *
     * @param userId идентификатор пользователя
     * @return баланс
     */
    BigDecimal getBalance(UUID userId) throws Exception;

    /**
     * Снятие заданной суммы с баланса пользователя
     *
     * @param userId идентификатор пользователя
     * @param amount снимаемая сумма
     */
    void takeMoney(UUID userId, BigDecimal amount) throws Exception;

    /**
     * Пополнение баланса пользователя на заданную сумму
     *
     * @param userId идентификатор пользователя
     * @param amount сумма для пополнения
     */
    void putMoney(UUID userId, BigDecimal amount);

    /**
     * Получение списка операций за выбранный период
     *
     * @param userId    идентификатор пользователя
     * @param startDate начало периода включительно
     * @param endDate   окончание периода включительно
     * @return список операций пользователя за выбранный период
     */
    List<Operation> getOperationList(UUID userId, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Перевод заданной суммы другому пользователю
     *
     * @param userIdFrom идентификатор пользователя
     * @param userIdTo   идентификатор пользователя, которому переводят
     * @param amount     сумма для перевода
     * @return баланс после перевода на заданную сумму
     */
    List<String> transferMoney(UUID userIdFrom, UUID userIdTo, BigDecimal amount);
}
