package com.rs.jadwal;

import com.rs.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JadwalRepository extends JpaRepository<Jadwal, Integer>
{
    Optional<Jadwal> findByName(String name);
}
