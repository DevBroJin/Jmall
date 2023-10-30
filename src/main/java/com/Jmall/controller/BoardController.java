package com.Jmall.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Jmall.domain.BoardVO;
import com.Jmall.domain.Criteria;
import com.Jmall.domain.PageDTO;
import com.Jmall.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

// 게시판의 기능에 사용할 URL매핑주소를 구성요소로 하는 컨트롤러 기능의 클래스.
// BoardController에서는 BoardServiceImpl의 메소드를 호출한다.
/*
servlet-context.xml 파일
<context:component-scan base-package="com.Jmall.controller" /> 자동추가됨.
*/
// pom.xml 파일에서 <groupID>log4j</groupID>위치에서 <scope>runtime</scope> 주석처리해야 @Log4j 를 사용가능해짐.
@Log4j // log객체지원 해줌.
@Controller // servlet-context.xml 파일에
@RequestMapping("/board/*") // views폴더 밑에 board 생성
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	/*
	 * public BoardController(BoardService boardService) { this.boardService =
	 * boardService; }
	 */

	// 매핑주소 /board/register
	// 글쓰기 폼
	@GetMapping("/register")
	public void write() {
		log.info("called register...");
	}

	// 매핑주소 /board/register
	// 저장버튼 클릭후 글쓰기 저장
	@PostMapping("/register")
	public String register(BoardVO board) {

		log.info("게시판 입력데이터: " + board); // board.toString()
		// db저장.
		/*
		 * 1)BoardMapper인터페이스와 BoardMapper.xml 작업 2)BoardService인터에피스와 BoardServiceImpl
		 * 작업
		 */
		boardService.register(board);

		return "redirect:/board/list"; // 주소는 절대경로
	}

	// 매핑주소 /board/list
	// 목록
	// Model model : list.jsp파일에 데이터(대부분 DB)를 출력하고자 할때
	/*
	@GetMapping("/list")
	public void list(Model model) {
		// 서비스 메서드 호출
		List<BoardVO> list = boardService.getList();
		model.addAttribute("list", list); // jsp작업진행
	}
	*/
	
	// 스프링이 Criteria클래스의 기본생성자를 호출하여, 객체를 생성해준다.
	// Criteria cri = new Criteria();
	// Model model = new 생성자()
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list: " + cri); // cri.toString()
		// Criteria(pageNum=1, amount=10, type=null, keyword=null)
		
		//1)목록 데이터 - List(BoardVO)
		List<BoardVO> list = boardService.getListWithPaging(cri);
		model.addAttribute("list", list);
		
		//2)페이징 기능 - PageDTO
		int total = boardService.getTotalCount(cri); // 테이블의 데이터 전체개수
		
		log.info("데이터 총개수: " + total);
		
		PageDTO pageDTO = new PageDTO(cri, total);
		model.addAttribute("pageMaker",pageDTO);
		
		//페이징정보: PageDTO(startPage=1, endPage=10, prev=false, next=true, total=1024, cri=Criteria(pageNum=1, amount=10, type=null, keyword=null))
		log.info("페이징정보: " + pageDTO);
	}
	
	// 매핑주소 /board/get?bno=게시물번호
	// 게시물읽기 : 리스트에서 제목을 클릭했을 때, 게시물번호에 데이터를 출력
	// 수정폼
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		// 목록에서 선택한 게시물번호
		log.info("게시물번호: " + bno);

		// 페이징과검색정보
		log.info("페이징과 검색정보: " + cri);
		
		BoardVO board = boardService.get(bno);
		model.addAttribute("board", board);
	}
	
	//매핑주소 /board/modify
	//수정하기
	// RedirectAttributes : 페이지(주소) 이동시 파라미터 정보를 제공하는 목적으로 사용
	@PostMapping("/modify")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		
		log.info("수정 데이터: " + board);
		log.info("Criteria: " + cri);
		// db저장.

		boardService.modify(board);
		
		//검색과 페이징 정보를 이동주소(/board/list)의 파라미터로 사용하기 위한 작업
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		
		// /board/list?pageNum=값&amount=값&type=값&keyword=값
		return "redirect:/board/list"; // list(Criteria cri, Model model)
	}
	
	//매핑주소 /board/delete
	//삭제하기
	@GetMapping("/delete")
	public String delete(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {
		
		log.info("삭제할 번호: " + bno);
		
		//db작업
		
		boardService.delete(bno);
		
		//검색과 페이징 정보를 이동주소(/board/list)의 파라미터로 사용하기 위한 작업
		/*
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("getKeyword", cri.getKeyword());
		*/
		
		return "redirect:/board/list" + cri.getListLink();
	}
}
