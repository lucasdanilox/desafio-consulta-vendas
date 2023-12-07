package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
            + "FROM Sale obj "
            + "WHERE (:minDate IS NULL OR obj.date >= :minDate) AND "
            + "(:maxDate IS NULL OR obj.date <= :maxDate) AND "
            + "(:name IS NULL OR LOWER(obj.seller.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
            + "ORDER BY obj.date DESC")
    Page<SaleReportDTO> findSales(@Param("minDate") LocalDate minDate,
                                  @Param("maxDate") LocalDate maxDate,
                                  @Param("name") String name,
                                  Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount)) "
            + "FROM Sale obj "
            + "WHERE (:minDate IS NULL OR obj.date >= :minDate) AND (:maxDate IS NULL OR obj.date <= :maxDate) "
            + "GROUP BY obj.seller.name")
    Page<SummaryDTO> sumTotalSalesBySeller(@Param("minDate") LocalDate minDate,
                                           @Param("maxDate") LocalDate maxDate,
                                           Pageable pageable);

}
