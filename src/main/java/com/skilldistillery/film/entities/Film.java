package com.skilldistillery.film.entities;

import java.util.List;
import java.util.Objects;

public class Film {

		private int id;
		private String title;
		private String description;
		private int releaseYear;
		private int languageId;
		private int rentalDuration;
		private double rentalRate;
		private int length;
		private double replacementCost;
		private String rating;
		private String specialFeatures;
		private List<Actor> actors;
		private String language;
		private String category;
		
		

		public Film() {
			super();
		}



		public Film(int id, String title, String description, int releaseYear, int languageId, int rentalDuration,
				double rentalRate, int length, double replacementCost, String rating, String specialFeatures,
				List<Actor> actors, String language, String category) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.releaseYear = releaseYear;
			this.languageId = languageId;
			this.rentalDuration = rentalDuration;
			this.rentalRate = rentalRate;
			this.length = length;
			this.replacementCost = replacementCost;
			this.rating = rating;
			this.specialFeatures = specialFeatures;
			this.actors = actors;
			this.language = language;
			this.category = category;
		}



		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}



		public String getTitle() {
			return title;
		}



		public void setTitle(String title) {
			this.title = title;
		}



		public String getDescription() {
			return description;
		}



		public void setDescription(String description) {
			this.description = description;
		}



		public int getReleaseYear() {
			return releaseYear;
		}



		public void setReleaseYear(int releaseYear) {
			this.releaseYear = releaseYear;
		}



		public int getLanguageId() {
			return languageId;
		}



		public void setLanguageId(int languageId) {
			this.languageId = languageId;
		}



		public int getRentalDuration() {
			return rentalDuration;
		}



		public void setRentalDuration(int rentalDuration) {
			this.rentalDuration = rentalDuration;
		}



		public double getRentalRate() {
			return rentalRate;
		}



		public void setRentalRate(double rentalRate) {
			this.rentalRate = rentalRate;
		}



		public int getLength() {
			return length;
		}



		public void setLength(int length) {
			this.length = length;
		}



		public double getReplacementCost() {
			return replacementCost;
		}



		public void setReplacementCost(double replacementCost) {
			this.replacementCost = replacementCost;
		}



		public String getRating() {
			return rating;
		}



		public void setRating(String rating) {
			this.rating = rating;
		}



		public String getSpecialFeatures() {
			return specialFeatures;
		}



		public void setSpecialFeatures(String specialFeatures) {
			this.specialFeatures = specialFeatures;
		}



		public List<Actor> getActors() {
			return actors;
		}



		public void setActors(List<Actor> actors) {
			this.actors = actors;
		}



		public String getLanguage() {
			return language;
		}



		public void setLanguage(String language) {
			this.language = language;
		}



		public String getCategory() {
			return category;
		}



		public void setCategory(String category) {
			this.category = category;
		}



		
	}


