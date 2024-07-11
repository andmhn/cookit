package com.github.andmhn.cookit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.andmhn.cookit.model.Recipe;
import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecipeService {
	private final RecipeRepository recipeRepository;
	
	public boolean existById(Long id) {
		return recipeRepository.existsById(id);
	}
	
	public Optional<Recipe> findById(Long id) {
		return recipeRepository.findById(id);
	}

	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	public void delete(Recipe recipe) {
		recipeRepository.delete(recipe);
	}

	public void deleteById(Long id) {
		recipeRepository.deleteById(id);
	}
	
	public List<Recipe> findByUser(User user) {
		return recipeRepository.findByUser(user);
	}

	public void deleteAllByUser(User user) {
		for (Recipe recipe : findByUser(user)) {
            delete(recipe);
		}
	}

	public boolean checkUserHasRecipe(User user, Long recipeId) {
		if(!existById(recipeId)) { 
			return false;
		}
		Recipe recipe = recipeRepository.findById(recipeId).get();
		
		return recipe.getUser() == user;
	}
}
