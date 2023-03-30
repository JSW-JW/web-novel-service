package com.example.webnovelservice.security.oauth2;

import com.example.webnovelservice.model.AuthProvider;
import com.example.webnovelservice.model.User;
import com.example.webnovelservice.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomOAuth2UserServiceTest {

	@InjectMocks
	private CustomOAuth2UserService customOAuth2UserService;

	@Mock
	private UserRepository userRepository;

	private OAuth2UserRequest oAuth2UserRequest;
	private OAuth2User oAuth2User;

	@BeforeEach
	public void setUp() {
		oAuth2UserRequest = TestUtil.createOAuth2UserRequest();
		oAuth2User = TestUtil.createOAuth2User();
	}

	@Test
	public void loadUser_whenUserExists_shouldUpdateUser() {
		User user = TestUtil.createExistingUser();
		when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
		when(userRepository.save(any())).thenReturn(user);

		OAuth2User result = customOAuth2UserService.loadUser(oAuth2UserRequest);

		assertEquals(user.getName(), result.getAttribute("name"));
		assertEquals(user.getEmail(), result.getAttribute("email"));
		verify(userRepository, times(1)).findByEmail(any());
		verify(userRepository, times(1)).save(any());
	}

	@Test
	public void loadUser_whenUserDoesNotExist_shouldRegisterUser() {
		User user = TestUtil.createNonExistingUser();
		when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
		when(userRepository.save(any())).thenReturn(user);

		OAuth2User result = customOAuth2UserService.loadUser(oAuth2UserRequest);

		assertEquals(user.getName(), result.getAttribute("name"));
		assertEquals(user.getEmail(), result.getAttribute("email"));
		verify(userRepository, times(1)).findByEmail(any());
		verify(userRepository, times(1)).save(any());
	}
}

class TestUtil {
	static OAuth2UserRequest createOAuth2UserRequest() {
		// Create a mock OAuth2UserRequest for testing
		return mock(OAuth2UserRequest.class);
	}

	static OAuth2User createOAuth2User() {
		// Create a mock OAuth2User with some test attributes
		OAuth2User oAuth2User = mock(OAuth2User.class);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("name", "John Doe");
		attributes.put("email", "johndoe@example.com");
		when(oAuth2User.getAttributes()).thenReturn(attributes);
		return oAuth2User;
	}

	static User createExistingUser() {
		User user = new User();
		user.setId(1L);
		user.setName("John Doe");
		user.setEmail("johndoe@example.com");
		user.setProvider(AuthProvider.google);
		user.setProviderId("123");
		return user;
	}

	static User createNonExistingUser() {
		User user = new User();
		user.setId(2L);
		user.setName("Jane Doe");
		user.setEmail("janedoe@example.com");
		user.setProvider(AuthProvider.google);
		user.setProviderId("456");
		return user;
	}
}
