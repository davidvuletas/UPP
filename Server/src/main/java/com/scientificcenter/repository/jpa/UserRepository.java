package com.scientificcenter.repository.jpa;

import com.scientificcenter.model.enums.Role;
import com.scientificcenter.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsUserByEmail(String email);
    Boolean existsUserByEmailAndPassword(String email, String password);
    List<User> getAllUsersByRolesContains(Role role);
    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmailContains(String username);
    @Query(value = "SELECT * \n" +
            "FROM   user \n" +
            "WHERE  id IN (SELECT user_id \n" +
            "              FROM   editor \n" +
            "              WHERE  id IN (SELECT editor_id \n" +
            "                            FROM   editor_scientific_areas \n" +
            "                            WHERE  scientific_areas_id = :id))", nativeQuery = true)
    List<User> findUsersWhichAreCorrespondingForArea(@Param("id") Long areaId);
}
