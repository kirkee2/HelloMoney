package com.beta.tacademy.hellomoneycustomer.module.cookie;

import okhttp3.CookieJar;

/**
 * 쿠키를 저장하고 관리하기 위한 클래스(쿠키를 거부 할 수도 있다)
 * CookieJar 기본 인터페이스를 상속받아 구현하며 우리 프로젝트를 위해 메소드를 2개 추가
 */
public interface ClearableCookieJar extends CookieJar {
    /**
     * 지속적인(휘발성이 아닌)쿠키를 유지 하면서 나머지(휘발성쿠키)는 지운다
     */
    void clearSession();

    /**
     * 지속성을 가진 쿠키와 휘발성인 쿠키를 모두 지우기 위한 메소드
     */
    void clear();
}
