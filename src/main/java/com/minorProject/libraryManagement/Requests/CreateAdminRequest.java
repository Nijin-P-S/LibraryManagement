package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Admin;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAdminRequest {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    public Admin to(){
        return Admin.builder()
                .name(this.getName())
                .email(this.getEmail())
                .build();
    }
}
