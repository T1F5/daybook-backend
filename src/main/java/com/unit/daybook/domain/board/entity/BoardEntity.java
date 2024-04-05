package com.unit.daybook.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "board")
@Entity
public class BoardEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long boardId;

    @Column
    private String content;

    @Column
    private Long respectBoardId;



}
