package com.scientificcenter.model.users;

import com.scientificcenter.model.enums.Role;
import lombok.*;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public Long id;

    @Column
    public String name;

    @Column
    public String lastname;

    @Column
    public String city;

    @Column
    public String country;

    @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}")
    @Column
    public String email;

    @Size(min = 6, max = 26)
    @Column
    public String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    public List<Role> roles;


    public static ObjectValue usersToVariable(List<User>users) {
        Map<String, String> dataForForm = new HashMap<String, String>();
        for (User obj : users) {
            dataForForm.put(obj.id.toString(), obj.name.concat(" ").concat(obj.getLastname()));
        }
        return Variables.objectValue(dataForForm)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON)
                .create();
    }


}
