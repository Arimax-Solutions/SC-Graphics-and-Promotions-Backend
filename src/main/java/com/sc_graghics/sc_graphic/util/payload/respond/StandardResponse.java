package com.sc_graghics.sc_graphic.util.payload.respond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StandardResponse {
    private Integer status;
    private String message;
    private Object data;
}
