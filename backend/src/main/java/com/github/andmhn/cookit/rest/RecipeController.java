package com.github.andmhn.cookit.rest;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.model.Recipe;
import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.repository.RecipeRepository;
import com.github.andmhn.cookit.security.CustomUserDetails;
import com.github.andmhn.cookit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
	private final UserService userService;
	private final RecipeRepository recipeRepository;

	// create / update recipe
	@PostMapping
	public Recipe createNewRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestBody Recipe recipe
			) {
		User user = userService.getUserByEmail(currentUser.getEmail()).get();
		
		recipe.setUser(user);
		
		recipeRepository.save(recipe);
		
		return recipe;
	}

	// delete a recipe
	@GetMapping("/{recipeId}")
	public Recipe getRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable Long recipeId) {
		// TODO: check if exist
		Recipe recipe = recipeRepository.findById(recipeId).get();
		return recipe;
	}

	// delete a recipe
	@DeleteMapping("/{recipeId}")
	public Recipe deleteRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable Long recipeId) {
		// TODO: check if exist
		Recipe recipe = recipeRepository.findById(recipeId).get();
		recipeRepository.deleteById(recipeId);
		return recipe;
	}

	// get all recipe
    @GetMapping("/all")
    public List<Recipe> getAllRecipes(
    		@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());
        return recipeRepository.findByUser(user);
    }

	// get total numbers of recipe
    @GetMapping("/total")
    public int totalRecipes(
    		@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());
        return recipeRepository.findByUser(user).size();
    }
}
