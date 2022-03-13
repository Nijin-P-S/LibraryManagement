package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Student;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotNull
    private String name;

    @Min(1)
    private int age;

    @NotNull
    private String rollNo;

    public Student to(){
        return Student.builder()
                .name(this.getName())
                .age(this.getAge())
                .rollNo(this.getRollNo())
                .build();
    }
}
