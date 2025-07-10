package com.example.talky.global.response;

import com.example.talky.global.response.code.BaseResponseCode;
import com.example.talky.global.response.code.SuccessResponseCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@JsonPropertyOrder({"isSuccess", "timestamp","code","httpStatus","message","data"})
public class SuccessResponse<T> extends BaseResponse{
    private final int HttpStatus;
    private final T data;

    @Builder
    public SuccessResponse(T data, BaseResponseCode baseResponseCode){
        super(true, baseResponseCode.getCode(), baseResponseCode.getMessage());
        this.HttpStatus = baseResponseCode.getHttpStatus();
        this.data = data;
    }

    public static <T> SuccessResponse<T> OK(T data){
        return new SuccessResponse<>(data, SuccessResponseCode.SUCCESS_OK);
    }

    // 201 Created 응답
    public static <T> SuccessResponse<T> created(T data) {
        return new SuccessResponse<>(data, SuccessResponseCode.SUCCESS_CREATED);
    }

    public static SuccessResponse<?> empty(){
        return new SuccessResponse<>(null, SuccessResponseCode.SUCCESS_OK);
    }

    public static <T> SuccessResponse<T> of(T data, BaseResponseCode baseResponseCode){
        return new SuccessResponse<>(data, baseResponseCode);
    }

    public static SuccessResponse<?> from(BaseResponseCode baseResponseCode){
        return new SuccessResponse<>(null, baseResponseCode);
    }
}