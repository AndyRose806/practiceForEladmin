package com.pubo.security.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author:pubo
 * @Date:2023/12/317:06
 */

@Data
public class AuthUsrDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

}
