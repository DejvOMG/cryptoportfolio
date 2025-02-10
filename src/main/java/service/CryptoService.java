package com.example.cryptoportfolio.service;

import com.example.cryptoportfolio.model.Crypto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoService {
    private final List<Crypto> cryptoList = new ArrayList<>();
    private Integer nextId = 1;

    public Crypto addCrypto(Crypto crypto) {
        crypto.setId(nextId++);
        cryptoList.add(crypto);
        return crypto;
    }

    public List<Crypto> getAllCryptos(String sort) {
        List<Crypto> sortedList = new ArrayList<>(cryptoList);

        if (sort != null) {
            switch (sort.toLowerCase()) {
                case "name":
                    sortedList.sort(Comparator.comparing(Crypto::getName));
                    break;
                case "price":
                    sortedList.sort(Comparator.comparing(Crypto::getPrice));
                    break;
                case "quantity":
                    sortedList.sort(Comparator.comparing(Crypto::getQuantity));
                    break;
            }
        }

        return sortedList;
    }

    public Optional<Crypto> getCryptoById(Integer id) {
        return cryptoList.stream()
                .filter(crypto -> crypto.getId().equals(id))
                .findFirst();
    }

    public Optional<Crypto> updateCrypto(Integer id, Crypto updatedCrypto) {
        Optional<Crypto> existingCrypto = getCryptoById(id);

        if (existingCrypto.isPresent()) {
            Crypto crypto = existingCrypto.get();
            crypto.setName(updatedCrypto.getName());
            crypto.setSymbol(updatedCrypto.getSymbol());
            crypto.setPrice(updatedCrypto.getPrice());
            crypto.setQuantity(updatedCrypto.getQuantity());
            return Optional.of(crypto);
        }

        return Optional.empty();
    }

    public Double getPortfolioValue() {
        return cryptoList.stream()
                .mapToDouble(crypto -> crypto.getPrice() * crypto.getQuantity())
                .sum();
    }
}