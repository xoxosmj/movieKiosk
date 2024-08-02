package users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class UsersDTO {

    @NonNull
    private String userID;
    @NonNull
    private String userPassword;
    @NonNull
    private int userAge;

}
