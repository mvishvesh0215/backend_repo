package com.vidya_industries.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
@ToString
public class UserEntity extends BaseEntity{
	
	@Column(name = "name",length = 2083,nullable = false)
	private String name;
	@Column(name = "email",length = 2083,nullable = false,unique = true)
	private String email;
	@Column(name = "phone",length = 2083,nullable = false,unique = true)
	private String phone;
	@Column(name = "position",length = 2083,nullable = false)
	private String position;
	@Column(name = "company",length = 2083,nullable = false)
	private String company;
	@Column(name = "address",length = 2083,nullable = false)
	private String address;
	@Column(name = "password",length = 2083,nullable = false)
	private String password;
	@Column(name = "role",nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

}
