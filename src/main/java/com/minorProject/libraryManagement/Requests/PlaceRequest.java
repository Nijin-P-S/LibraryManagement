package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.*;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceRequest {

    @NotNull
    private Integer bookId;

    @NotNull
    private String requestType;

    public Request toRequest(Integer studentId){
        return toRequest(null, studentId);
    }

    public Request toRequest(Admin admin, Integer studentId){
        return Request.builder()
                .admin(admin)
                .book(this.bookId == null ?null: Book.builder().id(this.bookId).build())
                .student(studentId == null?null:Student.builder().id(studentId).build())
                .requestType(RequestType.valueOf(this.requestType)) //valueof to convert String to Enum
                .requestStatus(RequestStatus.PENDING)
                .requestId(UUID.randomUUID().toString())
                .build();
    }
}
