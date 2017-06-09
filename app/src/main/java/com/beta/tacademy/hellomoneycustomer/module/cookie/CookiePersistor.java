package com.beta.tacademy.hellomoneycustomer.module.cookie;

import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * 영속적인 쿠키를 저장 및 관리하는 인터페이스
 * 우리가 사용하는 쿠키는 okHttp3에서 사용하는 쿠키를 사용한다.
 */
public interface CookiePersistor {

    List<Cookie> loadAll();
    void saveAll(Collection<Cookie> cookies);
    void removeAll(Collection<Cookie> cookies);
    void clear();
}