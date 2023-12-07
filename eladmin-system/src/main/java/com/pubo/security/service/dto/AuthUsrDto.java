package com.pubo.security.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author:pubo
 * @Date:2023/12/317:06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUsrDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

}
