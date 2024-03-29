package com.qnocks.billy.catalog.repository;

import com.qnocks.billy.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndTenantId(Long id, String tenantId);
}
