package com.unit.daybook.global.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {

	private Long memberId;
	private final String snsId;
	private final String email;
	private final String nickname;
	private final List<GrantedAuthority> authorityList;

	public CustomUserDetails(Long memberId, String snsId, String email, String nickname) {
		this.memberId = memberId;
		this.snsId = snsId;
		this.authorityList = new ArrayList<>();
		this.email = email;
		this.nickname = nickname;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorityList;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
