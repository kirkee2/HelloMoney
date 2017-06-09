package com.beta.tacademy.hellomoneycustomer.module.cookie;

import java.util.Collection;

import okhttp3.Cookie;

/**
 * 잠시 저장하는 쿠키를 추가하고, 삭제하는 인터페이스(바로쓰고 버리는 Session Cookie에 적당)
 * 반복자(Iterator)를 구현해야 함
 */
public interface NonPersistentCookieCache extends Iterable<Cookie> {

    void addNonPersistenceAll(Collection<Cookie> cookies);
    void clearNonPersistendceCookies();
}