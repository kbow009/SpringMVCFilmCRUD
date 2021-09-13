package com.skilldistillery.film.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Film;

public class FilmDAOJdbcImpl implements FilmDAO {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private String user = "student";
	private String pass = "student";

	public FilmDAOJdbcImpl() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	@Override
	public Film findById(int filmId) {
		Film film = null;

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration,rental_rate, length, replacement_cost, rating, special_features, category.name FROM film JOIN language ON film.language_id = language.id JOIN film_category ON film.id = film_category.film_id JOIN category ON category.id = film_category.category_id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				film = new Film();
				film.setId(rs.getInt(1));
				film.setTitle(rs.getString(2));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(rs.getString("name"));
//				film.setCategories(getCategory(filmId));

			} else if (!rs.next()) {
				System.out.println("No data found");
			}
			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			System.err.println("Database error:");
			e.printStackTrace();
		}

		return film;
	}

	public List<String> getCategory(int id) {
		Film film = null;
		List<String> categories = new ArrayList<>();
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT category.* FROM category JOIN film_category  ON  category.id = film_category.film_id JOIN film ON film_category.film_id = film.id WHERE film.id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				categories.add(rs.getString("name"));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	private List<Actor> findActorsByFilmId(int filmId) {

		List<Actor> actors = new ArrayList<>();

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name " + " "
					+ " FROM actor JOIN film_actor ON actor.id = film_actor.actor_id " + " WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Actor actor = new Actor(id, firstName, lastName);

				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Film id not found");
			e.printStackTrace();
		}
		return actors;

	}

	@Override
	public List<Film> findFilmByKeyword(String filmKeyword) {
		List<Film> filmList = new ArrayList<>();

		Connection conn;

		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, "
					+ "rating FROM film JOIN language ON film.language_id = language.id  WHERE description LIKE ? OR title LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + filmKeyword + "%");
			stmt.setString(2, "%" + filmKeyword + "%");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRating(rs.getString("rating"));
				film.setActors(findActorsByFilmId(rs.getInt("id")));

				filmList.add(film);
			} else if (!rs.next()) {
				System.out.println("No data found");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return filmList;

	}

	public String findFilmLanguage(int languageId) {
		String language = null;

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT name FROM language WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, languageId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				language = rs.getString("name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Film id not found");
			e.printStackTrace();
		}
		return language;
	}

	@Override
	public void deleteFilm(Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "DELETE FROM film_actor WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getId());
			int updateCount = stmt.executeUpdate();
			sql = "DELETE FROM film WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getId());
			updateCount = stmt.executeUpdate();
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}

		}

	}

	@Override
	public Film addFilm(Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "INSERT INTO film (title, languageId, rental_duration, rental_rate, replacement_cost) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, film.getTitle());
//			stmt.setString(2, film.getDescription());
//			stmt.setInt(3, film.getReleaseYear());
			stmt.setInt(2, film.getLanguageId());
			stmt.setInt(3, film.getRentalDuration());
			stmt.setDouble(4, film.getRentalRate());
//			stmt.setInt(7, film.getLength());
			stmt.setDouble(5, film.getReplacementCost());
//			stmt.setString(9, film.getRating());
//			stmt.setString(10, film.getSpecialFeatures());

			

			int updateCount = stmt.executeUpdate();
			if (updateCount == 1) {
				ResultSet keys = stmt.getGeneratedKeys();
				if (keys.next()) {
					int newFilmId = keys.getInt(1);
					film.setId(newFilmId);
					
				}
			} else {
				film = null;
			}
			conn.commit(); // COMMIT TRANSACTION
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			
		}
		return film;
	}

	@Override
	public boolean updateFilm(Film film) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, user, pass);
			conn.setAutoCommit(false); // START TRANSACTION
			String sql = "UPDATE film SET id=?, title=?, description=?, releaseYear=?, languageId=?, rentalDuration=?,"
					+ " rentalRate=?, length=?, replacementCost=?, rating=?, specialFeatures=? " + " WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, film.getId());
			stmt.setString(2, film.getTitle());
			stmt.setString(3, film.getDescription());
			stmt.setInt(4, film.getReleaseYear());
			stmt.setInt(5, film.getLanguageId());
			stmt.setInt(6, film.getRentalDuration());
			stmt.setDouble(7, film.getRentalRate());
			stmt.setInt(8, film.getLength());
			stmt.setDouble(9, film.getReplacementCost());
			stmt.setString(10, film.getRating());
			stmt.setString(11, film.getSpecialFeatures());

			int updateCount = stmt.executeUpdate();
			if (updateCount == 1) {

				sql = "DELETE FROM film_actor WHERE film_id = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, film.getId());
				updateCount = stmt.executeUpdate();
				sql = "INSERT INTO film_actor (film_id, actor_id) VALUES (?,?)";
				stmt = conn.prepareStatement(sql);
				for (Actor actor : film.getActors()) {
					stmt.setInt(1, film.getId());
					stmt.setInt(2, actor.getId());
					updateCount = stmt.executeUpdate();
				}
				conn.commit(); // COMMIT TRANSACTION
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} // ROLLBACK TRANSACTION ON ERROR
				catch (SQLException sqle2) {
					System.err.println("Error trying to rollback");
				}
			}
			return false;
		}
		return true;
	}

}