package com.bank.internetbankapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OperationService {

    /**
     * Получение баланса по ID пользователя
     * @param userId идентификатор пользователя
     * @return баланс
     */
    BigDecimal getBalance(UUID userId) throws Exception;

    /**
     * Снятие заданной суммы с баланса пользователя
     * @param userId идентификатор пользователя
     * @param amount снимаемая сумма
     * @return баланс после снятия заданной суммы
     */
    void takeMoney(UUID userId, BigDecimal amount) throws Exception;

    /**
     * Пополнение баланса пользователя на заданную сумму
     * @param userId идентификатор пользователя
     * @param amount сумма для пополнения
     * @return баланс после пополнения на заданную сумму
     */
    void putMoney(UUID userId, BigDecimal amount);

    /**
     * Получение списка операций за выбранный период
     * @param userId идентификатор пользователя
     * @param startDate начало периода включительно
     * @param endDate окончание периода включительно
     * @return список операций пользователя за выбранный период
     */
    List<String> getOperationList(UUID userId, LocalDate startDate, LocalDate endDate);

    /**
     * Перевод заданной суммы другому пользователю
     * @param userIdFrom идентификатор пользователя
     * @param userIdTo идентификатор пользователя, которому переводят
     * @param amount сумма для перевода
     * @return баланс после перевода на заданную сумму
     */
    List<String> transferMoney(UUID userIdFrom, UUID userIdTo, BigDecimal amount);
}
