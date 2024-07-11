package com.github.andmhn.cookit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andmhn.cookit.model.Recipe;
import com.github.andmhn.cookit.model.User;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> findByUser(User user);

	void deleteAllByUser(User user);
}
