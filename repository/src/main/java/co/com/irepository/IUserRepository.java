package co.com.irepository;

import java.util.List;

import javax.validation.ConstraintViolationException;

import co.com.entity.entity.UserEntity;

public interface IUserRepository {

	UserEntity create(UserEntity user) throws ConstraintViolationException;
	
	List<UserEntity> getAll();
	
	UserEntity byEmail(String email);
	
	UserEntity byId(String email);
	
}
