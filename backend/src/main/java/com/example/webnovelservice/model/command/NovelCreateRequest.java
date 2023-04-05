package com.example.webnovelservice.model.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NovelCreateRequest {
	@NotBlank(message = "Title is required")
	private String title;

	@NotBlank(message = "Description is required")
	private String description;

	@NotBlank(message = "Genre is required")
	private String genre;

	@NotNull(message = "Author ID is required")
	private Long authorId;
}
