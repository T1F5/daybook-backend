package com.unit.daybook.scheduler;

import com.unit.daybook.domain.board.service.BoardService;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import com.unit.daybook.domain.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class todayBoardsScheduler {
    private final BoardService boardService;
    private final MemberRepository memberRepository;
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void run() {
        System.out.println(LocalDateTime.now().toString());
        List<Long> memberIds = memberRepository.findAll().stream().map(Member::getId).toList();

        boardService.batchReadBoard();

    }
}
