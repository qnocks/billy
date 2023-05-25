package com.qnocks.billy.tenant.repository;

import com.qnocks.billy.tenant.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAllByTenantId(Long tenantId);
}
