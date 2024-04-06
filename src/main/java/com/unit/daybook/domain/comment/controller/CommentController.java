package com.unit.daybook.domain.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unit.daybook.domain.comment.dto.request.CommentCreateRequest;
import com.unit.daybook.domain.comment.dto.request.CommentModifyRequest;
import com.unit.daybook.domain.comment.dto.response.CommentCreateResponse;
import com.unit.daybook.domain.comment.dto.response.CommentModifyResponse;
import com.unit.daybook.domain.comment.service.CommentService;
import com.unit.daybook.domain.common.annotation.LoginUsers;
import com.unit.daybook.global.config.security.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<CommentCreateResponse> commentCreate(
		@Valid @RequestBody CommentCreateRequest request,
		@LoginUsers CustomUserDetails userDetails
	) {
		CommentCreateResponse comment = commentService.createComment(request, userDetails.getMemberId());
		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	@DeleteMapping("/{commentId}")
	public void commentDelete(
		@PathVariable Long commentId
	) {
		commentService.deleteComment(commentId);
	}

	@PutMapping("/{commentId}")
	public CommentModifyResponse commentModify(
		@Valid @RequestBody CommentModifyRequest request,
		@PathVariable Long commentId
	) {
		return commentService.updateComment(request, commentId);
	}
}
