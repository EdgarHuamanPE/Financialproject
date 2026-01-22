package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper;

import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//public class UserMapper {

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    /**
     *     * convert  userDomain to userEntity
     *      * @param  domain
     *      * @return
     */

    UserEntity toEntity(User domain);

    /**
     * convert userEntity to userDomain
     * @param  entity
     * @return
     */
    User toDomain(UserEntity entity);


    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
     */
    User toDomain(UserRequest request);

    UserResponse toResponse(User createUser);


    /**
     * convert userEntity to userDomain
     * @param  domain
     * @return

    public static UserEntity toEntity(User domain){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(domain.getId());
        userEntity.setName(domain.getName());
        userEntity.setEmail(domain.getEmail());
        return userEntity;
    }



     * convert userEntity to userDomain
     * @param  entity
     * @return


    public  static  User toDomain(UserEntity entity){
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        return user;
    }
     */
}
