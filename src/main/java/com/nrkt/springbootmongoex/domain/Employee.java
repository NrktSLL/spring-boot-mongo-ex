package com.nrkt.springbootmongoex.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document("employee")
public class Employee {
    @Id
    String id;
    @NotBlank
    String firstName;
    String secondName;
    @NotBlank
    String lastName;
    @Email
    String email;
    @PastOrPresent
    Date hiredDate;
}
