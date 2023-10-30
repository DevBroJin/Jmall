package com.Jmall.service;

import java.util.List;

import com.Jmall.domain.BoardVO;
import com.Jmall.domain.Criteria;

// 인터페이스는 @Service 애노테이션 사용 안함.
public interface BoardService {

	public void register(BoardVO board);

	public BoardVO get(Long bno);
	
	public List<BoardVO> getList();
	
	public void delete(Long bno);
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public void modify(BoardVO board);
}
