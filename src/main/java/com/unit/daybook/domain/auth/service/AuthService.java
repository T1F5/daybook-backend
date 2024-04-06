package com.unit.daybook.domain.auth.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.unit.daybook.domain.auth.common.OAuthInfoResponse;
import com.unit.daybook.domain.auth.common.OAuthLoginParams;
import com.unit.daybook.domain.auth.dto.request.OauthProvider;
import com.unit.daybook.domain.auth.dto.response.SocialLoginResponse;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.domain.OauthInfo;
import com.unit.daybook.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final AuthTokensGenerator authTokensGenerator;
	private final RequestOAuthInfoService requestOAuthInfoService;

	public SocialLoginResponse socialLoginMember(OAuthLoginParams params) {
		OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
		Member member = findOrCreateMember(oAuthInfoResponse, params.oAuthProvider());
		member.updateLastLoginAt();
		return authTokensGenerator.generate(member);
	}

	private Member findOrCreateMember(OAuthInfoResponse oAuthInfoResponse, OauthProvider provider) {
		return memberRepository.findByNicknameAndOauthInfo_OauthId(oAuthInfoResponse.getNickname(), oAuthInfoResponse.getSnsId())
			.orElseGet(() -> signUp(oAuthInfoResponse, provider));
	}

	private Member signUp(OAuthInfoResponse oAuthInfoResponse, OauthProvider provider) {
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(oAuthInfoResponse.getSnsId(),
			provider.getValue(), oAuthInfoResponse.getEmail());
		Member user = Member.createNormalMember(oauthInfo, oAuthInfoResponse.getNickname());
		return memberRepository.save(user);
	}
}
