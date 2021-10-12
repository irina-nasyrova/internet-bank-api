package com.bank.internetbankapi.service;

import com.bank.internetbankapi.domain.constants.OperationTypeEnum;
import com.bank.internetbankapi.domain.entity.Operation;
import com.bank.internetbankapi.domain.entity.User;
import com.bank.internetbankapi.domain.repository.OperationRepository;
import com.bank.internetbankapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.PUT_MONEY;
import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.TAKE_MONEY;
import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.TRANSFER_FROM;
import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.TRANSFER_TO;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    final private UserRepository userRepository;
    final private OperationRepository operationRepository;

    @Override
    public BigDecimal getBalance(UUID userId) {
        User user = getUser(userId);
        return user.getBalance();
    }

    @Override
    @Transactional
    public void takeMoney(UUID userId, BigDecimal amount) {
        User user = getUser(userId);
        BigDecimal newBalance = user.getBalance().subtract(amount);
        checkBalance(newBalance, userId);
        user.setBalance(newBalance);
        userRepository.save(user);
        saveOperation(userId, TAKE_MONEY, amount);
    }

    @Override
    @Transactional
    public void putMoney(UUID userId, BigDecimal amount) {
        User user = getUser(userId);
        BigDecimal newBalance = user.getBalance().add(amount);
        user.setBalance(newBalance);
        userRepository.save(user);
        saveOperation(userId, PUT_MONEY, amount);
    }

    @Override
    public List<Operation> getOperationList(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
        return operationRepository.findOperationList(userId, startDate, endDate);
    }

    @Override
    public void transferMoney(UUID userId, UUID userIdTo, BigDecimal amount) {

        User user = getUser(userId);
        BigDecimal newBalance = user.getBalance().subtract(amount);
        checkBalance(newBalance, userId);
        user.setBalance(newBalance);
        userRepository.save(user);

        User userTo = getUser(userIdTo);
        BigDecimal newBalanceTo = userTo.getBalance().add(amount);
        userTo.setBalance(newBalanceTo);
        userRepository.save(userTo);

        saveOperation(userId, TRANSFER_TO, amount);
        saveOperation(userIdTo, TRANSFER_FROM, amount);
    }

    private void saveOperation(UUID userId, OperationTypeEnum type, BigDecimal amount) {
        Operation operation = new Operation(userId, type, amount);
        operationRepository.save(operation);
    }

    private User getUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new RuntimeException("Пользователь не найден id=" + userId);
                });
    }

    private void checkBalance(BigDecimal balance, UUID userId) {
        if (balance.signum() == -1) {
            throw new RuntimeException("Недостаточно средств на счете, id=" + userId);
        }
    }
}
