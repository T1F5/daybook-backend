package com.unit.daybook.domain.board.service;

import com.unit.daybook.domain.board.dto.request.AddBoardRequestDto;
import com.unit.daybook.domain.board.dto.response.BoardResponseDto;
import com.unit.daybook.domain.board.dto.response.BoardTmpResponse;
import com.unit.daybook.domain.board.dto.response.FindBoardListResponse;
import com.unit.daybook.domain.board.dto.response.FindOneBoardResponse;
import com.unit.daybook.domain.board.entity.Board;
import com.unit.daybook.domain.board.entity.Hashtag;
import com.unit.daybook.domain.board.entity.ReadBoard;
import com.unit.daybook.domain.board.repository.*;

import com.unit.daybook.domain.member.domain.Member;
import com.unit.daybook.domain.member.repository.MemberRepository;
import com.unit.daybook.domain.reaction.dto.response.ReactionTypeAndCount;
import com.unit.daybook.domain.reaction.repository.ReactionRepository;
import com.unit.daybook.global.error.exception.CustomException;
import com.unit.daybook.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReadBoardRepository readBoardRepository;
    private final HashtagRepository hashtagRepository;
    private final ReactionRepository reactionRepository;

    public BoardResponseDto addBoard(AddBoardRequestDto addBoardRequestDto, Long memberId) {
        Member member = memberRepository
            .findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (addBoardRequestDto.respectBoardId() != null) {
            Board respectBoard = boardRepository
                .findById(addBoardRequestDto.respectBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
            respectBoard.plusRespect();
            boardRepository.save(respectBoard);
        }

        Board board = boardRepository.save(Board.createBoard(addBoardRequestDto, member));

        List<String> hashtags = addBoardRequestDto.hashtags();
        for (int i = 0; i < hashtags.size(); i++) {
            hashtagRepository.save(Hashtag.createHashtag(addBoardRequestDto.hashtags().get(i), board));
        }

        return BoardResponseDto.from(board);
    }

    @Transactional(readOnly = true)
    public FindOneBoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        List<ReactionTypeAndCount> reactions = reactionRepository.findAllByBoardGroupByReactionType(board);

        return FindOneBoardResponse.of(board, reactions);
    }

    @Transactional(readOnly = true)
    public FindBoardListResponse getMyBoards(Long memberId) {
        List<Board> boards= boardRepository.findBoardsByMemberId(memberId);
        return FindBoardListResponse.from(boards);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getRandomBoards(Long memberId) {
        List<BoardResponseDto> result = getTodayBoardByMemberId(memberId);
        if (result.size() < 3) {
            List<Board> boards = getCurrentBoards(memberId);
            // read-board 에도 적재
            Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
            readBoardRepository.save(ReadBoard.createReadBoard(member, boards.get(0)));
            readBoardRepository.save(ReadBoard.createReadBoard(member, boards.get(1)));
            readBoardRepository.save(ReadBoard.createReadBoard(member, boards.get(2)));
            result = boards.stream()
                    .map(BoardResponseDto::from)
                    .toList();
        }
        result = new ArrayList<>();
        List<Board> boards = getCurrentBoards(memberId);
        // read-board 에도 적재
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException(memberId + "not found"));
        int toSave = 3 - result.size();
        for (int i =0 ; i<toSave; i++) {
            ReadBoard entity = ReadBoard.createReadBoard(member, boards.get(i));
            result.add(BoardResponseDto.from(boards.get(i)));
            readBoardRepository.save(entity);
        }

        return result;
    }


    public void batchReadBoard() {

        List<Long> memberIds = memberRepository.findAll().stream().map(Member::getId).toList();

		for (Long memberId : memberIds) {
			// 이미 읽은 일지
			List<Board> alreadyReadBoards = readBoardRepository.findBoardsByMemberId(memberId);

			// 안 읽은 일지
			List<Board> notReadBoards = boardRepository.findNotReadBoardsByMemberId(memberId, alreadyReadBoards);

			// 적재할 일지 고유 id
			List<Long> randomIdxs = selectRandomNumbers(notReadBoards.size() - 1);

			// 적재
			Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException(memberId + "not found"));
			readBoardRepository.save(
				ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(0)))));
			readBoardRepository.save(
				ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(1)))));
			readBoardRepository.save(
				ReadBoard.createReadBoard(member, notReadBoards.get(Math.toIntExact(randomIdxs.get(2)))));

		}
    }

    public List<BoardResponseDto> getTodayBoardByMemberId(Long memberId) {
        List<Long> todayBoardIds = readBoardRepository.findTodayBoardsByMemberId(memberId);
        List<Board> result = boardRepository.findBoardInBoardIds(todayBoardIds);
        return result.stream()
                .map(BoardResponseDto::from)
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

    // public BoardTmpResponse modifyBoard(Long boardId, AddBoardRequestDto addBoardRequestDto) {
    //     Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(boardId + ""));
    //     board.modifyBoard(addBoardRequestDto);
    //     List<String> newHashContents = addBoardRequestDto.hashtags();
    //     List<String> newHashContents2 = addBoardRequestDto.hashtags();
    //
    //     List<Hashtag> originHashContents = board.getHashtags();
    //
	// 	for (String newHashContent : newHashContents) {
	// 		hashtagRepository.save(Hashtag.createHashtag(newHashContent, board));
	// 	}
    //
    //     return BoardTmpResponse.builder().dto(AddBoardResponseDto.from(board)).hashtags(newHashContents).build();
    // }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public BoardTmpResponse getBoardWithHashTag(Long boardId) {
        List<String> hashContents = hashtagRepository.findAllByBoardId(boardId);
        FindOneBoardResponse result = getBoard(boardId);
        return BoardTmpResponse.builder().dto(result).hashtags(hashContents).build();

    }

    private List<Board> getCurrentBoards(Long memberId) {
		return boardRepository.findCurrentBoards(memberId);
    }
}
