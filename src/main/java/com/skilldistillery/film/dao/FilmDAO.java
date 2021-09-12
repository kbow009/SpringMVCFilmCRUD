package com.skilldistillery.film.dao;

import java.util.List;

import com.skilldistillery.film.entities.Film;

public interface FilmDAO {
	public Film findById(int filmId);
	public Film addFilm(Film film);
	public List<Film> findFilmByKeyword(String filmKeyword);
	public String findFilmLanguage(int languageId);
	public void deleteFilm(Film film);
	public boolean updateFilm(Film film);

}