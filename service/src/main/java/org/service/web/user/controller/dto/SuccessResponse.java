package org.service.web.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
public class SuccessResponse{

    private String message;
}
