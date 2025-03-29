package com.vidya_industries.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidya_industries.Entity.QuotationEntity;
import com.vidya_industries.Entity.QuotationStatus;

public interface QuotationDao extends JpaRepository<QuotationEntity, Long>{

	List<QuotationEntity> findByQuotationStatusOrQuotationStatus(QuotationStatus pending, QuotationStatus completed);

}
