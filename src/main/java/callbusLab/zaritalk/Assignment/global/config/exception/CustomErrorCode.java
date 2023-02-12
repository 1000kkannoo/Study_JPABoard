package callbusLab.zaritalk.Assignment.global.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {

    //로그인 & 로그아웃 검증
    Authentication_Entry_Point( "만료된 JWT 토큰입니다."),
    NOT_EXISTS_EMAIL("해당 계정은 존재하지 않습니다."),
    NOT_MATCHED_ID_OR_PASSWORD("아이디 또는 비밀번호를 잘못 입력하였습니다."),

    INVALID_REQUEST_ARRANGE("잘못된 정렬 방식을 요청했습니다"),
    OVER_LIMIT("한 페이지에 16개 이상의 게시글을 요청할 수 없습니다."),
    INVALID_REQUEST_FILTER("업데이트와 좋아요 수 로만 정렬할 수 있습니다."),

    // 알수 없는 오류의 처리
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다.");

    private final String statusMessage;
}
