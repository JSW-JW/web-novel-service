package com.example.webnovelservice.novel.dto.request;


import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	 @NotNull(message = "Genres are required")
	 private Set<Long> genreIds;

	 private MultipartFile file;
}
