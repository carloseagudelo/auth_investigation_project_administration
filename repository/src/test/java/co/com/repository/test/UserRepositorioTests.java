package co.com.repository.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import co.com.entity.entity.UserEntity;
import co.com.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataMongoTest
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserRepositorioTests {
	
	@InjectMocks
	private UserRepository userRepository;
	
	@Test
	public void AllFieldsAreGood() {
		UserEntity goodUser = UserEntity.builder()
				.name("Pedro perez")
				.email("pperez@gmail.com")
				.password("123456789")
				.role("user")
				.build();
		goodUser = userRepository.create(goodUser);
		assertThat(userRepository.byId(goodUser.getId())).isEqualTo((goodUser));
	}
	
	@Test
	public void withoutName() {
		UserEntity badUserByName = UserEntity.builder()
				.name("")
				.email("pperez@gmail.com")
				.password("123456789")
				.role("user")
				.build();
		badUserByName = userRepository.create(badUserByName);
		assertThat(badUserByName).isNull();
	}
	
	@Test
	public void withoutEmail() {
		UserEntity badUserByEmail = UserEntity.builder()
				.name("Pedro perez")
				.email("")
				.password("123456789")
				.role("user")
				.build();
		badUserByEmail = userRepository.create(badUserByEmail);
		assertThat(badUserByEmail).isNull();
	}
	
	@Test
	public void withoutPassword() {
		UserEntity badUserByPassword = UserEntity.builder()
				.name("Pedro perez")
				.email("pperez@gmail.com")
				.password("")
				.role("user")
				.build();
		badUserByPassword = userRepository.create(badUserByPassword);
		assertThat(badUserByPassword).isNull();
	}
	
	@Test
	public void withoutRole() {
		UserEntity badUserByRole = UserEntity.builder()
				.name("Pedro perez")
				.email("pperez@gmail.com")
				.password("123456789")
				.role("")
				.build();
		badUserByRole = userRepository.create(badUserByRole);
		assertThat(badUserByRole).isNull();
	}

}
