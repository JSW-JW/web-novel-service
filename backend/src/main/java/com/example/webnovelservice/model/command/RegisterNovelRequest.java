package com.example.webnovelservice.model.command;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterNovelRequest
 {
	 @NotBlank(message = "Title is required")
	 private String title;
	 @NotBlank(message = "Description is required")
	 private String description;
	 @NotBlank(message = "Genre is required")
	 private String genre;
	 @NotNull(message = "Author ID is required")
	 private Long authorId;
	 public RegisterNovelRequest(String title, String description, String genre, Long authorId) {
		 this.title = title;
		 this.description = description;
		 this.genre = genre;
		 this.authorId = authorId;
	 }

	 public RegisterNovelRequest() {}
}
