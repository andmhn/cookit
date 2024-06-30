package com.github.andmhn.cookit.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long user_id;
	
	@Column(unique = true)
	private String email;

	private String fullname;
	private String password;
	private String joindate;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
	private List<Recipe> recipies;
}
