package com.vidya_industries.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "quotations")
public class QuotationEntity extends BaseEntity{
	
	@Column(name = "paper_type",length = 2083,nullable = false)
	private String paperType;
	@Column(name = "layers",nullable = false)
	private Integer layers;
	@Column(name = "quantity",nullable = false)
	private Integer quantity;
	@Column(name = "dimensions",length = 2083,nullable = false)
	private String dimensions;
	@Column(name = "message",length = 2083)
	private String message;
	@Column(name = "quotation_status",nullable = false)
	@Enumerated(EnumType.STRING)
	private QuotationStatus quotationStatus;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
}
