package com.company.explorecalifornia.repository;

import com.company.explorecalifornia.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * The interface {@code RegionRepository} specifies methods for operating on the Region entity in the database
 */
public interface RegionRepository extends JpaRepository<Region, Long> {

    /**
     * Retrieves a region from the database by region name
     * @param name the region name
     * @return the region
     */
    Optional<Region> findByName(String name);
}