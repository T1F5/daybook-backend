package com.unit.daybook.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.service.MemberService;
import com.unit.daybook.global.config.security.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public Member memberFindOne(
		@LoginUsers CustomUserDetails userDetails
	) {
		return memberService.findOneMember(userDetails.getMemberId());
	}

}
