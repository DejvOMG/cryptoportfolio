package com.example.cryptoportfolio;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CryptoController {
    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping
    public ResponseEntity<Crypto> addCrypto(@RequestBody Crypto crypto) {
        return ResponseEntity.ok(cryptoService.addCrypto(crypto));
    }

    @GetMapping
    public ResponseEntity<List<Crypto>> getAllCryptos(@RequestParam(required = false) String sort) {
        return ResponseEntity.ok(cryptoService.getAllCryptos(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCryptoById(@PathVariable Integer id) {
        return cryptoService.getCryptoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crypto> updateCrypto(@PathVariable Integer id, @RequestBody Crypto crypto) {
        return cryptoService.updateCrypto(id, crypto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Double> getPortfolioValue() {
        return ResponseEntity.ok(cryptoService.getPortfolioValue());
    }
}