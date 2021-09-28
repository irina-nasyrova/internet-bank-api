package com.bank.internetbankapi.service;

import com.bank.internetbankapi.domain.entity.User;
import com.bank.internetbankapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    final private UserRepository userRepository;

    @Override
    public BigDecimal getBalance(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new RuntimeException("Пользователь не найден id=" + userId)).getBalance();
    }

    @Override
    public void takeMoney(UUID userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresentOrElse(user1 -> {
            BigDecimal newBalance = user1.getBalance().subtract(amount);
            if (newBalance.signum() == -1) {
                throw new RuntimeException("Недостаточно средств на счете, id=" + userId);
            }
            user1.setBalance(newBalance);
            userRepository.save(user1);
        }, () -> {
            throw new RuntimeException("Пользователь не найден id=" + userId);
        });
    }

    @Override
    public void putMoney(UUID userId, BigDecimal amount) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresentOrElse(user1 -> {
            BigDecimal newBalance = user1.getBalance().add(amount);
            user1.setBalance(newBalance);
            userRepository.save(user1);
        }, () -> {
            throw new RuntimeException("Пользователь не найден id=" + userId);
        });
    }

    @Override
    public List<String> getOperationList(UUID userId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public List<String> transferMoney(UUID userIdFrom, UUID userIdTo, BigDecimal amount) {
        return null;
    }
}
