package pl.dawid.wishexposer.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String url;
    @JsonBackReference //avoids: 'Could not write JSON: Infinite recursion error'
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
}
