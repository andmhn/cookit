package com.github.andmhn.cookit.rest;

import java.util.List;
import java.util.stream.Collectors;

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
import com.github.andmhn.cookit.rest.dto.RecipeDto;
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
	public RecipeDto createNewRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestBody RecipeDto recipeDto
			) {
		User user = userService.getUserByEmail(currentUser.getEmail()).get();

		Recipe recipe = mapFromRecipeDto(recipeDto);
		recipe.setUser(user);
		return mapToRecipeDto(recipeRepository.save(recipe));
	}

	// Get a recipe by id
	@GetMapping("/{recipeId}")
	public RecipeDto getRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable Long recipeId) {
		// TODO: check if exist
		Recipe recipe = recipeRepository.findById(recipeId).get();
		return mapToRecipeDto(recipe);
	}	

	// delete a recipe
	@DeleteMapping("/{recipeId}")
	public RecipeDto deleteRecipe(
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@PathVariable Long recipeId) {
		// TODO: check if exist
		Recipe recipe = recipeRepository.findById(recipeId).get();
		recipeRepository.deleteById(recipeId);
		return mapToRecipeDto(recipe);
	}

	// get all recipe
    @GetMapping("/all")
    public List<RecipeDto> getAllRecipes(
    		@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());

        List<Recipe> recipeList =  recipeRepository.findByUser(user);

        return recipeList.stream()
        		.map(recipe -> mapToRecipeDto(recipe))
        		.collect(Collectors.toList());
    }

	// get total numbers of recipe
    @GetMapping("/total")
    public int totalRecipes(
    		@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());
        return recipeRepository.findByUser(user).size();
    }

    private RecipeDto mapToRecipeDto(Recipe recipe) {
    	RecipeDto recipeDto = new RecipeDto(
    			recipe.getId(),
    			recipe.getName(),
    			recipe.getDifficulty(),
    			recipe.getType(),
    			recipe.getMain_ingredients(),
    			recipe.getRecipe_link(),
    			recipe.getRating(),
    			recipe.getPrep_time()
    	);

    	return recipeDto;
    }

    private Recipe mapFromRecipeDto(RecipeDto recipeDto) {
    	Recipe recipe= new Recipe();

    	recipe.setId(recipeDto.id());
    	recipe.setName(recipeDto.name());
    	recipe.setDifficulty(recipeDto.difficulty());
    	recipe.setType(recipeDto.type());
    	recipe.setMain_ingredients(recipeDto.main_ingredients());
    	recipe.setRecipe_link(recipeDto.recipe_link());
    	recipe.setRating(recipeDto.rating());
    	recipe.setPrep_time(recipeDto.prep_time());

    	return recipe;
    }
}
