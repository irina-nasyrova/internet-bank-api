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
import java.util.Optional;
import java.util.UUID;

import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.PUT_MONEY;
import static com.bank.internetbankapi.domain.constants.OperationTypeEnum.TAKE_MONEY;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    final private UserRepository userRepository;
    final private OperationRepository operationRepository;

    @Override
    public BigDecimal getBalance(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("Пользователь не найден id=" + userId)).getBalance();
    }

    @Override
    @Transactional
    public void takeMoney(UUID userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresentOrElse(user1 -> {
            BigDecimal newBalance = user1.getBalance().subtract(amount);
            if (newBalance.signum() == -1) {
                throw new RuntimeException("Недостаточно средств на счете, id=" + userId);
            }
            user1.setBalance(newBalance);
            userRepository.save(user1);
            saveOperation(userId, TAKE_MONEY, amount);
        }, () -> {
            throw new RuntimeException("Пользователь не найден id=" + userId);
        });
    }

    @Override
    @Transactional
    public void putMoney(UUID userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresentOrElse(user1 -> {
            BigDecimal newBalance = user1.getBalance().add(amount);
            user1.setBalance(newBalance);
            userRepository.save(user1);
            saveOperation(userId, PUT_MONEY, amount);
        }, () -> {
            throw new RuntimeException("Пользователь не найден id=" + userId);
        });
    }

    @Override
    public List<Operation> getOperationList(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
        return operationRepository.findOperationList(userId, startDate, endDate);
    }

    @Override
    public List<String> transferMoney(UUID userIdFrom, UUID userIdTo, BigDecimal amount) {
        return null;
    }

    private void saveOperation(UUID userId, OperationTypeEnum type, BigDecimal amount) {
        Operation operation = new Operation(userId, type, amount);
        operationRepository.save(operation);
    }
}
