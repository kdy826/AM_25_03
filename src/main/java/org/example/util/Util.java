package org.example.util;

import java.time.LocalDateTime;  // LocalDateTime 클래스에게서 임포트 받음
import java.time.format.DateTimeFormatter;

public class Util {
    public static String getNowStr() {  //현재 날짜 및 시간을 가져오는 함수(str)
        LocalDateTime now = LocalDateTime.now();  //지금시간 확인
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));// 시간 재설정
        return formatedNow;
    }
}