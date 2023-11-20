package com.rsm.rsm_backend.service.impl;

import com.rsm.rsm_backend.entity.VerificationToken;
import com.rsm.rsm_backend.repository.VerificationTokenRepository;
import com.rsm.rsm_backend.service.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }


    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken addVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public void deleteVerificationToken(String token) {
        verificationTokenRepository.delete(verificationTokenRepository.findByToken(token).get());
    }

//    public int setConfirmedAt(String token) {
//        return verificationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
//    }
}

