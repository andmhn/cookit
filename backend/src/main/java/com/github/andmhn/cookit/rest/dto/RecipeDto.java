package com.github.andmhn.cookit.rest.dto;

public record RecipeDto (
		Long id,
		String name,
		String difficulty,
		String type,
		String main_ingredients,
		String recipe_link,
		int rating,
		int prep_time) {

}
