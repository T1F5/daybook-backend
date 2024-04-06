package com.unit.daybook.domain.board.repository;

import com.unit.daybook.domain.board.entity.ReadBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadBoardRepository  extends JpaRepository<ReadBoard, Long> {

}
