package com.practice.BookingService.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class JSendResponse<T> {
    private String status;
    private T data;
    private String message;
    private Integer code;

    public static <T> JSendResponse<T> success(T data) {
        return new JSendResponse<>("success", data, null, null);
    }

    public static <T> JSendResponse<T> fail(T data) {
        return new JSendResponse<>("fail", data, null, null);
    }

    public static <T> JSendResponse<T> error(String message) {
        return new JSendResponse<>("error", null, message, null);
    }

    public static <T> JSendResponse<T> error(String message, Integer code, T data) {
        return new JSendResponse<>("error", data, message, code);
    }
}

