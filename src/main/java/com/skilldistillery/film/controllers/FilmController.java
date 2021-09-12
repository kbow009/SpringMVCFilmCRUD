package com.skilldistillery.film.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.skilldistillery.film.dao.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	@Autowired
	private FilmDAO filmDao;
	
	public void setDAO(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
	
//	@RequestMapping(path = {"/", "findFilm.do"}, method=RequestMethod.GET )
//	public String home() {
		
		
		
//		return "WEB-INF/home.jsp";
//	}
	@RequestMapping(path = "newFilm.do", method = RequestMethod.POST)
	  public ModelAndView newState(Film film) {
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("film",film);
	    mv.setViewName("WEB-INF/result.jsp");
	    return mv;
	}

	
	// need jsp for new film here.
	
	
//	
	@RequestMapping(path = "findFilm.do", params ="id", method = RequestMethod.GET)
	public ModelAndView findFilmByID(String id) {
		ModelAndView mv = new ModelAndView();
		int id_int = Integer.parseInt(id);
		Film f = filmDao.findById(id_int);
		mv.addObject("film8", f);
		mv.setViewName("WEB-INF/result.jsp");
		return mv;
	}
//}

	@RequestMapping(path = "deleteFilm.do", params = "id", method = RequestMethod.GET)
	public ModelAndView deleteFilm(int id) {
		ModelAndView mv = new ModelAndView();
//		Film f = filmDao.deleteFilm(id);
//		mv.addObject("film", f);
		mv.setViewName("result");
		return mv;
	}}

	
	
	
	
	
	


