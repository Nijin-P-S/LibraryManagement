package com.minorProject.libraryManagement.Requests;

import com.minorProject.libraryManagement.models.Request;
import com.minorProject.libraryManagement.models.RequestStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessRequest {

    @NotNull
    private int adminId;

    @NotNull
    private int requestId;

    @NotNull
    private RequestStatus requestStatus;

    private String comment;

}
