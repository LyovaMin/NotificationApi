package by.lyofchik.mainpushservice.Model.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Response {
    private String status;
    private String message;
    private Object data;

    public static Response success(Object data) {
        return Response.builder()
                .status("200")
                .message("Success")
                .data(data)
                .build();
    }

    public static Response success() {
        return Response.builder()
                .status("200")
                .message("Success")
                .data(null)
                .build();
    }

    public static Response error(String status, String message) {
        return Response.builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }

    public static Response error() {
        return Response.builder()
                .status("Error")
                .message("Error")
                .data(null)
                .build();
    }
}