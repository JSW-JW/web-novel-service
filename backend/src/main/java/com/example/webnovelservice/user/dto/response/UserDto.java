package com.example.webnovelservice.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private String name;
	private String email;
	private String profileImageUrl;
	private boolean emailVerified;
}
