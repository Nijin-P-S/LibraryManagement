package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Admin;
import com.minorProject.libraryManagement.models.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAdminRequest {

    //This all will give as NULL as the class is not a bean
//    @Value("${ADMIN_AUTHORITY}")
//    private String ADMIN_AUTHORITY;
//
//    @Value("${BOOK_INFO_AUTHORITY}")
//    private String BOOK_INFO_AUTHORITY;
//
//    @Value("${STUDENT_INFO_AUTHORITY}")
//    private String STUDENT_INFO_AUTHORITY;
//
//    @Value("{$authorities.delimiter}")
//    private String delimiter;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    public Admin to(){
        return to(null);
    }

    public Admin to(User user){
        return Admin.builder()
                .name(this.getName())
                .email(this.getEmail())
                .user(user)
                .build();
    }

    public User toUser() {
        return User.builder()
                .username(this.username)
                .password(this.password)
//                .authorities(BOOK_INFO_AUTHORITY+delimiter+
//                        ADMIN_AUTHORITY+delimiter+
//                        STUDENT_INFO_AUTHORITY)
                .build();
    }
}
