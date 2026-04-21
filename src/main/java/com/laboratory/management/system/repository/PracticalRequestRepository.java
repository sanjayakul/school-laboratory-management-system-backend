package com.laboratory.management.system.repository;

import com.laboratory.management.system.model.PracticalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticalRequestRepository extends JpaRepository<PracticalRequest, Long> {
}
