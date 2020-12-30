package pl.dawid.wishexposer.service;

import org.springframework.web.multipart.MultipartFile;
import pl.dawid.wishexposer.domain.Item;
import pl.dawid.wishexposer.domain.User;
import pl.dawid.wishexposer.exception.domain.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException, UnsupportedEncodingException;
    List<User> getUsers();
    User findUserByUsername(String username);
    User getItemsByUserId(String userId);
    User findUserByEmail(String email);
    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
    User updateUser(String currentUser, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
    void deleteUser(String username) throws IOException;
    void resetPassword(String email) throws MessagingException, EmailNotFoundException, UnsupportedEncodingException;
    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
