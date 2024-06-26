package com.sparta.delivery_app.common.exception.errorcode;


import com.sparta.delivery_app.common.status.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    INVALID_STORE(StatusCode.NOT_FOUND.getCode(), "해당 매장을 조회할 수 없습니다."),
    DELETED_STORE(StatusCode.BAD_REQUEST.getCode(), "영업 중인 매장이 아닙니다."),
    DUPLICATED_STORE(StatusCode.FORBIDDEN.getCode(), "매장 등록권한이 존재하지 않습니다(1인 1점포)"),
    DUPLICATED_STORE_BUSINESS_NUMBER(StatusCode.BAD_REQUEST.getCode(), "이미 등록되어 있는 사업자번호입니다."),
    STORE_REGISTERED_HISTORY(StatusCode.UNAUTHORIZED.getCode(), "더 이상 매장을 등록할 수 없습니다."),
    ;
    private final Integer httpStatusCode;
    private final String description;
}
