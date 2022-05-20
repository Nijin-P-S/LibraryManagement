package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Student;
import com.minorProject.libraryManagement.models.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    //This will give as Null , as the class is not a bean
//    @Value("${BOOK_INFO_AUTHORITY}")
//    private String BOOK_INFO_AUTHORITY;
//
//    @Value("{STUDENT_ONLY_AUTHORITY}")
//    private String STUDENT_ONLY_AUTHORITY;
//
//    @Value("{$authorities.delimiter}")
//    private String delimiter;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @Min(1)
    private int age;

    @NotNull
    private String rollNo;

    public Student to(){
        return (null);
    }

    public Student to(User user){
        return Student.builder()
                .name(this.getName())
                .age(this.getAge())
                .rollNo(this.getRollNo())
                .user(user)
                .build();
    }

    public User toUser(){
        return User.builder()
                .username(this.username)
                .password(this.password)
//                .authorities(BOOK_INFO_AUTHORITY+delimiter+STUDENT_ONLY_AUTHORITY)
                .build();
    }
}
