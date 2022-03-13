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
    private Integer studentId;
    @NotNull
    private Integer bookId;
    @NotNull
    private String requestType;

    public Request toRequest(){
        return Request.builder()
                .book(this.bookId==null?null: Book.builder().id(this.bookId).build())
                .student(this.studentId==null?null: Student.builder().id(this.studentId).build())
                .requestType(RequestType.valueOf(this.requestType)) //valueof to convert String to Enum
                .requestStatus(RequestStatus.PENDING)
                .requestId(UUID.randomUUID().toString())
                .build();
    }

    public Request toRequest(Admin admin){
        return Request.builder()
                .admin(admin)
                .book(this.bookId == null ?null: Book.builder().id(this.bookId).build())
                .student(this.studentId == null?null:Student.builder().id(this.studentId).build())
                .requestType(RequestType.valueOf(this.requestType)) //valueof to convert String to Enum
                .requestStatus(RequestStatus.PENDING)
                .requestId(UUID.randomUUID().toString())
                .build();
    }
}
