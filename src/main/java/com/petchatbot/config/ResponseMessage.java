package com.petchatbot.config;

public abstract class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String PASSWORD_WRONG = "패스워드 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String JOIN_FAIL = "회원 가입 입력란에서 Null값 발생";
    public static final String CHANGE_PW = "비밀번호 변경 성공";
    public static final String CHANGE_PW_FAIL = "비밀번호 변경 실패";
    public static final String WRONG_EMAIL_CODE = "인증번호 실패";
    public static final String SUCCESS_EMAIL_CODE = "인증번호 성공";
    public static final String DUPLICATE_EMAIL = "중복된 이메일";
    public static final String GET_EMAIL = "사용자 이메일";
    public static final String AVAILABLE_EMAIL = "사용가능한 이메일";
    public static final String ENTER_JOIN_INFORMATION = "회원 가입 정보 입력 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INCORRECT_APPROACH = "잘못된 접근";
    public static final String SEND_EMAIL = "이메일 발송";

    public static final String ADD_PET = "반려동물추가 성공";
    public static final String SUCCESS_CHANGE_PET_INFO = "반려동물 프로필 변경 성공";
    public static final String SUCCESS_GET_PET_INFO = "반려동물 정보 제공";
    public static final String FAIL_GET_PET_INFO = "반려동물 정보 없음";
    public static final String SUCCESS_GET_PET_LIST = "반려동물 list 정보 제공";
    public static final String FAIL_GET_PET_LIST = "반려동물 list 정보 없음";

    public static final String SUCCESS_DELETE_PET = "반려동물 삭제 성공";

    public static final String NULL_SEARCH_DISEASES = "질병백과 검색어 없음";
    public static final String SUCCESS_SEARCH_DISEASES = "질병백과 검색결과 제공";
    public static final String SUCCESS_MINI_DISEASEDICTIONARY = "질병백과 페이지 전송 성공";
    public static final String FAIL_SEARCH_DISEASES = "질병백과 검색결과 없음";

    public static final String SUCCESS_GET_DISEASE_INFO = "질병 정보 제공";

    public static final String SUCCESS_GET_HOSPITAL_LIST = "주변 동물병원 list 제공";
    public static final String FAIL_GET_HOSPITAL_LIST = "주변 동물병원 없음";

    public static final String SUCCESS_GET_PARTNER_LIST = "주변 연계병원 list 제공";
    public static final String FAIL_GET_PARTNER_LIST = "주변 연계병원 없음";

    public static final String SUCCESS_ADD_MEDICAL_FORM = "문진표 저장 성공";
    public static final String SUCCESS_GET_MEDICAL_FORM_LIST = "문진표 목록 불러오기 성공";
    public static final String FAIL_GET_MEDICAL_FORM_LIST = "문진표 목록 없음";
    public static final String SUCCESS_UPDATE_MEDICAL_FORM = "문진표 수정 성공";
    public static final String SUCCESS_MEDICAL_FORM_INFO = "문진표 세부정보 불러오기 성공";
    public static final String SUCCESS_DELETE_MEDICAL_FORM = "문진표 삭제 성공";

    public static final String SUCCESS_ADD_EXPECTDIAG = "예상진단 저장 성공";
    public static final String SUCCESS_GET_EXPECTDIAG_LIST = "예상진단 list 제공";

    public static final String SUCCESS_GET_EXPECTDIAG_INFO = "예상진단 세부정보 불러오기 성공";
    public static final String SUCCESS_DELETE_EXPECTDIAG = "예상진단 삭제 성공";

    public static final String SUCCESS_APPLY_TO_HOSPITAL = "연계병원 상담신청 성공";
    public static final String SUCCESS_GET_APPOINTMENT_LIST = "상담신청 조회 성공";
    public static final String SUCCESS_GET_APPOINTMENT_INFO = "상담신청 세부정보 조회 성공";
    public static final String SUCCESS_DELETE_APPOINTMENT = "상담신청 삭제 성공";





    public static final String SEND_EMAIL_FAIL = "이메일 발송 실패";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
}
