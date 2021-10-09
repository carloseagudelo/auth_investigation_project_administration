package co.com.entity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;

	@NotBlank(message = "field cant be empty")
    private String name;
	
	@NotBlank(message = "field cant be empty")
	@Email(message = "field will be a vald email")
	@Indexed(unique=true)
    private String email;
	
	@NotBlank(message = "field can't be empty")
	@Length(min=5, message="Password dont have a valid format")
    private String password;
	
	@NotBlank(message = "field cant be empty")
    private String role;
	
}
