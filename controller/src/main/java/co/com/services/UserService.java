package co.com.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;

import co.com.common.config.GeneralConfigurationManager;
import co.com.common.encrypt.Encryption;
import co.com.common.exception.NetC2ServiceException;
import co.com.common.messages.MessageUtils;
import co.com.common.response.FinalResponse;
import co.com.common.response.IResponse;
import co.com.common.utils.Utils;
import co.com.entity.entity.UserEntity;
import co.com.entity.request.UserRequestDTO;
import co.com.entity.response.UserResponseDTO;
import co.com.irepository.IUserRepository;
import co.com.iservices.IUserService;
import io.jsonwebtoken.Claims;

import java.util.Objects;

import javax.validation.ConstraintViolationException;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Service
public class UserService implements IUserService {
	
	@Autowired MessageUtils messages;
	@Autowired FinalResponse finalResponse;
	@Autowired IUserRepository userRepository;
	@Autowired GeneralConfigurationManager generalConfiguration;
	
	IResponse response;
	HttpHeaders responseHeaders = new HttpHeaders();
	ModelMapper modelMapper = new ModelMapper();

	@Override
	public ResponseEntity<String> singup(UserRequestDTO userRequest) throws Exception {
		
		try {
			UserEntity existUser = userRepository.byEmail(userRequest.getEmail());
			if( Objects.isNull(existUser) ) {
				UserEntity user = UserEntity.builder()
						.name(userRequest.getName())
						.email(userRequest.getEmail())
						.password(Encryption.encrypt(userRequest.getPassword()))
						.role(userRequest.getRole())
						.build();
				
				UserEntity savedUser = userRepository.create(user);
				if( !Objects.isNull(savedUser) ) {
					String token = Utils.encodeAdditionalJWTToken(user.getEmail(), user.getName(), user.getId(), generalConfiguration.getProperty("USER_ROLE"));
					responseHeaders.add("Authorization", "Bearer " + token);	
					response = finalResponse.getResponse(messages.getMessage("USER_CREATED_SUCCESS", user.getName()), responseHeaders, HttpStatus.OK);
				}else {
					throw new NetC2ServiceException(messages.getMessage("USER_CREATED_UNSUCCESS", user.getName()), HttpStatus.FORBIDDEN);
				}
				return response.toResponseEntity();
			} else {
				throw new NetC2ServiceException(messages.getMessage("USER_EXISTS", userRequest.getEmail()), HttpStatus.CONFLICT);
			}
		} catch (ConstraintViolationException e) {
			throw new NetC2ServiceException(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@Override
	public ResponseEntity<String> getAll() throws Exception {
		IResponse response;
		HttpHeaders responseHeaders = new HttpHeaders();
		List<UserEntity> usersFound = userRepository.getAll();
		List<UserResponseDTO> userResponse = modelMapper.map(usersFound, new TypeToken<List<UserResponseDTO>>() {}.getType());
		response = finalResponse.getResponse(userResponse, responseHeaders, HttpStatus.OK);
		return response.toResponseEntity();
	}

	@Override
	public ResponseEntity<String> validateToken(String token) throws Exception {
		UserEntity user = getUserByToken(token);
		if( !Objects.isNull(user) ) {
			UserResponseDTO userResponse = modelMapper.map(user, new TypeToken<UserResponseDTO>() {}.getType());
			response = finalResponse.getResponse(userResponse, responseHeaders, HttpStatus.OK);
		} else {
			response = finalResponse.getResponse(messages.getMessage("NO_ALLOWED_USER"), responseHeaders, HttpStatus.UNAUTHORIZED);
		}
		return response.toResponseEntity();
	}
	
	private UserEntity getUserByToken(String token) {
		Claims claims = Utils.decodeJWTToken(token);
		if( !Objects.isNull(claims) ) {
			String userId = new String((String) claims.get("userId"));
			return userRepository.byId(userId);
		}
		return null;
	}

}
