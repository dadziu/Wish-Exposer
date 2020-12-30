package pl.dawid.wishexposer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawid.wishexposer.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {  //object/class to manage, type of PrimaryKey

    User findUserByUsername(String username); //JPA convention like a select: find user by username
    User findUserByUserId(String userId);

    User findUserByEmail(String email);
}
