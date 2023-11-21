package com.rsm.rsm_backend.repository;

import com.rsm.rsm_backend.entity.Provider;
import com.rsm.rsm_backend.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

    List<Request> findByProvider(Provider provider);

}
