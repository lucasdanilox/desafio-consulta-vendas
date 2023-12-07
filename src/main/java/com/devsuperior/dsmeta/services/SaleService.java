package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public Page<SaleReportDTO> findByMonths(String minDate, String maxDate, String sellerName, Pageable pageable) {
        LocalDate minDateResult = (minDate == null) ? null : LocalDate.parse(minDate);
        LocalDate maxDateResult = (maxDate == null) ? LocalDate.now() : LocalDate.parse(maxDate);

        if (minDateResult == null && maxDateResult != null) {
            minDateResult = maxDateResult.minusYears(1);
        }

        return repository.findSales(minDateResult, maxDateResult, sellerName, pageable);
    }

    public Page<SummaryDTO> findBySumm(String minDate, String maxDate, Pageable pageable) {
        LocalDate minDateResult = (minDate == null) ? null : LocalDate.parse(minDate);
        LocalDate maxDateResult = (maxDate == null) ? LocalDate.now() : LocalDate.parse(maxDate);

        if (minDateResult == null && maxDateResult != null) {
            minDateResult = maxDateResult.minusYears(1);
        }
        return repository.sumTotalSalesBySeller(minDateResult, maxDateResult, pageable);

    }

}
