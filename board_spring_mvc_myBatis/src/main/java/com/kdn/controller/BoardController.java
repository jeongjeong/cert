package com.kdn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.kdn.model.biz.BoardService;
import com.kdn.model.domain.Board;
import com.kdn.model.domain.PageBean;

@Controller
public class BoardController {
	/**
	 * Error 처리
	 * 
	 * @ExceptionHandler Controller 에서 오류가 발생하면 처리하는 기능
	 */
	@ExceptionHandler
	public ModelAndView handler(Exception e){
		ModelAndView model = new ModelAndView("index");
		model.addObject("msg",e.getMessage()); //request 에 저장
		model.addObject("content","ErrorHandler.jsp"); //request에 저장
		return model;
	}
	
	@Autowired
	private BoardService  boardService;
	@RequestMapping(value="listBoard.do", method=RequestMethod.GET)
	public String listBoard(PageBean bean, Model model ){
		List<Board> list = boardService.searchAll(bean);
		model.addAttribute("list", list);
		model.addAttribute("content", "board/listBoard.jsp");
		return "index";
	}
	@RequestMapping(value="searchBoard.do", method=RequestMethod.GET)
	public String searchBoard(int no, Model model ){
		model.addAttribute("board", boardService.search(no));
		model.addAttribute("content", "board/searchBoard.jsp");
		return "index";
	}
	
	
	@RequestMapping(value="insertBoardForm.do", method=RequestMethod.GET)
	public String insertBoardForm(Model model){
		model.addAttribute("content", "board/insertBoard.jsp");
		return "index";
	}
	
	@RequestMapping(value="insertBoard.do", method=RequestMethod.POST)
	public String insertBoard(Board board, HttpServletRequest request){
		
		String dir = request.getRealPath("upload/");
		boardService.add(board, dir);
		return "redirect:listBoard.do"; 
		// insert 후 listboard 직접 호출하면 살아있는 경로와 요청정보에 의해 재등록됨
		// 반드시 redirect로 목록 불러와야함
	}
	
	
}




