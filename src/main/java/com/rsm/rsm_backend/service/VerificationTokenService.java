package com.rsm.rsm_backend.service;

import com.rsm.rsm_backend.entity.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    void saveConfirmationToken(VerificationToken token);

    Optional<VerificationToken> getToken(String token);
}
