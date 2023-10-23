package com.example.pifinance_back.Services;

import com.example.pifinance_back.Entities.Client;
import com.example.pifinance_back.Entities.Wallet;
import com.example.pifinance_back.Entities.WalletEnum;
import com.example.pifinance_back.Repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {
    public class WalletNotFoundException extends RuntimeException {
        public WalletNotFoundException(String message) {
            super(message);
        }
    }
@Autowired
    private final WalletRepository walletRepository;

    public Wallet createWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }
    public List<Wallet> getWalletsByUser(Client user) {
            return walletRepository.findByUser(user);
    }

    public List<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }

    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }

    public Wallet updateWallet(Wallet wallet) {
        if (wallet.getId_wallet() == null || !walletRepository.existsById(wallet.getId_wallet())) {
            throw new WalletNotFoundException("Le portefeuille avec l'ID spécifié n'existe pas.");
        }
        return walletRepository.save(wallet);
    }

    public List<Wallet> getActiveWalletsForUser(Client user) {
        return walletRepository.findByUserAndIsActive(user, true);
    }

    public Wallet deactivateWallet(Long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            wallet.setActive(false);
            return walletRepository.save(wallet);
        } else {
            throw new WalletNotFoundException("Le portefeuille avec l'ID spécifié n'existe pas.");
        }
    }

    public BigDecimal getWalletBalance(Long walletId) {
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if (optionalWallet.isPresent()) {
            Wallet wallet = optionalWallet.get();
            return wallet.getBalance();
        } else {
            throw new WalletNotFoundException("Le portefeuille avec l'ID spécifié n'existe pas.");
        }
    }

    public List<Wallet> getWalletsByType(WalletEnum type) {
        return walletRepository.findByType(type);
    }

    public List<Wallet> getWalletsByCurrency(Currency currency) {
        return walletRepository.findByCurrency(currency);
    }

    public List<Wallet> getWalletsByUserAndType(Client user, WalletEnum type) {
        return walletRepository.findByUserAndType(user, type);
    }
}
