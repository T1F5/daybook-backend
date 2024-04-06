package com.unit.daybook.domain.board.service;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.AddBoardResponseDto;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.ReadBoard;
import com.unit.daybook.domain.board.repository.BoardRepository;

import com.unit.daybook.domain.board.repository.BoardRepositoryImpl;
import com.unit.daybook.domain.board.repository.ReadBoardRepository;
import com.unit.daybook.domain.board.repository.ReadBoardRepositoryImpl;
import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardRepositoryImpl boardRepositoryImpl;
    private final MemberRepository memberRepository;
    private final ReadBoardRepositoryImpl readBoardRepositoryImpl;
    private final ReadBoardRepository readBoardRepository;

    public AddBoardResponseDto addBoard(AddBoardRequestDto addBoardRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException(memberId + "not found"));
        if (addBoardRequestDto.respectBoardId() != null) {
            Board respectBoard = boardRepository.findById(addBoardRequestDto.respectBoardId()).orElseThrow(() -> new RuntimeException(addBoardRequestDto.respectBoardId() + "not found"));
            respectBoard.plusRespect();
            boardRepository.save(respectBoard);
        }
        // 글의 카운트 올리기.. redis..?

        return AddBoardResponseDto.from(boardRepository.save(Board.createBoard(addBoardRequestDto, member)));
    }

    @Transactional(readOnly = true)
    public AddBoardResponseDto getBoard(Long boardId) {
        return AddBoardResponseDto.from(
                boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(boardId + "not found")));
    }

    @Transactional(readOnly = true)
    public List<AddBoardResponseDto> getMyBoards(Long memberId) {
        // TODO 페이지네이션 - 스와이프 방식?
        return boardRepositoryImpl.findBoardsByMemberId(memberId)
                .stream()
                .map(AddBoardResponseDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AddBoardResponseDto> getRandomBoards(Long memberId) {
        return getTodayBoardByMemberId(memberId);
    }


    public void batchReadBoard() {

        List<Long> memberIds = memberRepository.findAll().stream().map(Member::getId).toList();

        for (int i = 0; i < memberIds.size(); i++) {
            Long memberId = memberIds.get(i);
            // 이미 읽은 일지
            List<Long> aleadyReadBoardIds = readBoardRepositoryImpl.findBoardsByMemberId(memberId);

            // 안 읽은 일지
            List<Board> notReadBoards = boardRepositoryImpl.findNotReadBoardsByMemberId(memberId, aleadyReadBoardIds);

            // 적재할 일지 고유 id
            List<Long> randomIdxs = selectRandomNumbers(notReadBoards.size() - 1);

            // 적재
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException(memberId + "not found"));
            readBoardRepository.save(ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(0)))));
            readBoardRepository.save(ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(1)))));
            readBoardRepository.save(ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(2)))));

        }
    }

    public List<AddBoardResponseDto> getTodayBoardByMemberId(Long memberId) {
        List<Long> todayBoards = readBoardRepositoryImpl.findTodayBoardsByMemberId(memberId);
        List<Board> result = boardRepositoryImpl.findBoardInBoardIds(todayBoards);
        return result.stream()
                .map(AddBoardResponseDto::from)
                .toList();
    }

    public static List<Long> selectRandomNumbers(long n) {
        // 1부터 n 까지의 수를 리스트에 추가
        List<Long> numbers = new ArrayList<>();
        for (long i = 1; i <= n; i++) {
            numbers.add(i);
        }

        // 랜덤으로 3개를 선택하기 위해 새 리스트를 생성
        List<Long> randomNumbers = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            // 리스트에서 랜덤하게 하나의 수를 선택하여 결과 리스트에 추가
            int randomIndex = random.nextInt(numbers.size());
            randomNumbers.add(numbers.get(randomIndex));
            // 선택된 수는 다시 리스트에서 제거하여 중복 선택을 방지
            numbers.remove(randomIndex);
        }

        return randomNumbers;
    }
}
