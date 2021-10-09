package co.com.repository;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import co.com.entity.entity.UserEntity;
import co.com.irepository.IUserRepository;

@Service
public class UserRepository implements IUserRepository {
	
	@Autowired MongoTemplate mongoTemplate;

	@Override
	public UserEntity create(UserEntity user) throws ConstraintViolationException {
		return mongoTemplate.insert(user);
	}

	@Override
	public List<UserEntity> getAll() {
		return mongoTemplate.findAll(UserEntity.class);
	}

	@Override
	public UserEntity byEmail(String email) {
		Query query = new Query(); 
		query.addCriteria(Criteria.where("email").is(email));
		return mongoTemplate.findOne(query, UserEntity.class, "users");
	}
	
	@Override
	public UserEntity byId(String id) {
		return mongoTemplate.findById(id, UserEntity.class);
	}
	
}
