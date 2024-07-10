package com.github.andmhn.cookit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name =  "recipes")
@Data
public class Recipe {
	@Id
	@Column(name  = "recipe_id", unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String difficulty;
	private String type;
	private String main_ingredients;
	private String recipe_link;

	private int rating;
	private int prep_time;

	@ManyToOne
	@JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
	private User user;
}