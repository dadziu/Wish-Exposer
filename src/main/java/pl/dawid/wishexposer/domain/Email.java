package pl.dawid.wishexposer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Email {
    private String email;
    private String title;
    private String message;
}
