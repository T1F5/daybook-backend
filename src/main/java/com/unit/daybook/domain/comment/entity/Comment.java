package com.unit.daybook.domain.comment.entity;

import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "comment_id")
   private Long commentId;

	@Column(nullable = false, length = 25)
   private String content;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "member_id")
   private Member member;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "board_id")
   private Board board;

	@Builder(access = AccessLevel.PRIVATE)
	private Comment(String content, Member member, Board board) {
		this.content = content;
		this.member = member;
		this.board = board;
	}

	public static Comment createComment(
		String content, Member member, Board board
	) {
		return Comment.builder()
			.content(content)
			.member(member)
			.board(board)
			.build();
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
