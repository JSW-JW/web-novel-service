package com.example.webnovelservice.security.oauth2;

import com.example.webnovelservice.MainApplication;
import com.example.webnovelservice.model.AuthProvider;
import com.example.webnovelservice.model.User;
import com.example.webnovelservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
public class CustomOAuth2UserServiceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		// configure RestAssured to use MockMvc instance created and injected by Spring Boot.
		// the MockMvc instance is autoconfigured by @AutoConfigureMockMvc annoation.
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	public void testRegisterNewUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setProvider(AuthProvider.google);
		user.setProviderId("123456");
		user.setName("John Doe");
		user.setEmail("john.doe@example.com");
		user.setImageUrl("https://example.com/image.jpg");

		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Add your OAuth2 test logic here to trigger the registerNewUser method in CustomOAuth2UserService

		mockMvc.perform(post("/oauth2/authorization/google"))
			// status code 302 - redirect url
			.andExpect(status().isFound())
			// generate document with identifier as "register-new-user"
			.andDo(document("register-new-user"));
	}

	@Test
	public void testUpdateExistingUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setProvider(AuthProvider.google);
		user.setProviderId("123456");
		user.setName("John Doe");
		user.setEmail("john.doe@example.com");
		user.setImageUrl("https://example.com/image.jpg");

		when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		// Add your OAuth2 test logic here to trigger the updateExistingUser method in CustomOAuth2UserService

		mockMvc.perform(post("/oauth2/authorization/google"))
			.andExpect(status().isFound())
			.andDo(document("update-existing-user"));
	}
}
