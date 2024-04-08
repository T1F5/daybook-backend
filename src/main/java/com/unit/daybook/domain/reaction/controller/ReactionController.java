package com.unit.daybook.domain.reaction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.domain.reaction.dto.request.ReactionCreateRequest;
import com.unit.daybook.domain.reaction.dto.response.ReactionCreateResponse;
import com.unit.daybook.domain.reaction.service.ReactionService;
import com.unit.daybook.global.config.security.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reactions")
@RequiredArgsConstructor
public class ReactionController {

	private final ReactionService reactionService;

	@PostMapping
	public ResponseEntity<ReactionCreateResponse> reactionCreate(
		@Valid @RequestBody ReactionCreateRequest request,
		@LoginUsers CustomUserDetails userDetails
	) {
		ReactionCreateResponse reaction = reactionService.createReaction(request, userDetails.getMemberId());
		return ResponseEntity.status(HttpStatus.CREATED).body(reaction);
	}
}
