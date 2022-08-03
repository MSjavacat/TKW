package com.ms.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MS
 * @create 2022-07-15-16:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{

    private Integer code;

    private String msg;
}
