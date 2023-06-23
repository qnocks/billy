package com.qnocks.billy.tenant.repository;

import com.qnocks.billy.tenant.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByTenantId(String tenantId);
    Optional<Client> findByIdAndTenantId(Long id, String tenantId);
}
