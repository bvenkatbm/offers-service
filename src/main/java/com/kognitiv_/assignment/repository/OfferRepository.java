package com.kognitiv_.assignment.repository;

import com.kognitiv_.assignment.entity.OfferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    @Query("select o from OfferEntity o" +
            " where (:name is null or o.name = :name)" +
            " and (:location is null or o.location = :location)" +
            " and (:validFrom is null or o.validFrom >= :validFrom)" +
            " and (:validTill is null or o.validTill <= :validTill)")
    Page<OfferEntity> findByParameters(@Param("name") String name,
                                       @Param("location") String location,
                                       @Param("validFrom") LocalDate validFrom,
                                       @Param("validTill") LocalDate validTill,
                                       Pageable pageable);
}
