package pl.dawid.wishexposer.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id; //id for database
    private String userId; //public id for user
    private String firstName;
    private String lastName;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // password property will be not exposed to frontend
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String role; //ROLE_USER { read, edit }, ROLE_ADMIN { delete, update, create }
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;
    @JsonManagedReference //avoids: 'Could not write JSON: Infinite recursion error'
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST})
    private List<Item> items = new ArrayList<>();
}
