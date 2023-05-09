package com.example.webnovelservice.novel.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateNovelRequest
 {
	 @NotBlank(message = "Title is required")
	 private String title;
	 @NotBlank(message = "Description is required")
	 private String description;
	 @NotBlank(message = "Genre is required")
	 private String genre;
	 @NotNull(message = "Author ID is required")
	 private Long authorId;
}
