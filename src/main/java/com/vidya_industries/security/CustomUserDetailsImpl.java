package com.vidya_industries.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vidya_industries.Entity.UserEntity;

public class CustomUserDetailsImpl implements UserDetails {
	private UserEntity user;
	

	public CustomUserDetailsImpl(UserEntity user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of
				(new SimpleGrantedAuthority(
						user.getRole().toString()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	public UserEntity getuser() {
		return user;
	}
	

}
