package com.minorProject.libraryManagement.Responses;

import com.minorProject.libraryManagement.models.RequestStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessResponse {

    private RequestStatus requestStatus;

    private String systemComment;

    private String adminComment;
}
