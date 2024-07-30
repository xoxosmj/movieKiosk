package user;


import lombok.*;

@Getter
@AllArgsConstructor
public class UserDTO {

    @NonNull
    private String id;
    @NonNull
    private String password;
    @NonNull
    private int age;

}
