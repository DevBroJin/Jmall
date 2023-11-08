package com.Jmall.mapper;

// 컬렉션 3개 인터페이스 : List, Set, Map
import java.util.List;

import com.Jmall.domain.BoardVO;
import com.Jmall.dto.Criteria;

//인터페이스
/*
 root-context.xml파일
 <context:component-scan base-package="com.Jmall.service" /> 수동추간
 */
public interface BoardMapper {

	//추상메소드
	
	//글쓰기 저장.
	// 메소드 명과 id="register" 일치 <insert id="register"></insert>
	public void register(BoardVO board);
	
	//게시물 읽기 또는 글수정 폼.  1개 BoardVO
	public BoardVO get(Long bno);
	
	//조회수증가
	public void readCount(Long bno);
	
	//글수정하기
	public void modify(BoardVO board);
	
	// 전체목록. 여러개 List<BoardVO> 리턴값
	public List<BoardVO> getList();
	
	// 페이징 목록. 여러개 List<BoardVO> 리턴값. pageNum, amount, type, keyword 사용
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	// 전체 데이터 개수(검색포함)
	public int getTotalCount(Criteria cri); // type, keyword 사용
	
	//글 삭제하기
	public void delete(Long bno);
}
