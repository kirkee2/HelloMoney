package com.beta.tacademy.hellomoneycustomer.module.cookie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Cookie;

/*
  쿠키를  세팅하고 관리하는 클래스
 */
public class SetCookieCache implements NonPersistentCookieCache {

    private Set<IdentifiableCookie> cookies;

    public SetCookieCache() {
        cookies = new HashSet<>();
    }
    @Override
    public void addNonPersistenceAll(Collection<Cookie> cookies) {
        for (IdentifiableCookie cookie : IdentifiableCookie.decorateAll(cookies)) { ////
            //그전에 쿠키가 존재한다면 제거하고 새로운 쿠키를 세팅
            this.cookies.remove(cookie);
            this.cookies.add(cookie);
        }
    }
    @Override
    public void clearNonPersistendceCookies() {
        cookies.clear();
    }
    @Override
    public Iterator<Cookie> iterator() {
        return new SetCookieCacheIterator();
    }
    /*
      현재 설정된 쿠키를 반복자로 빼내기 위한 부분
     */
    private class SetCookieCacheIterator implements Iterator<Cookie> {

        private Iterator<IdentifiableCookie> iterator;

        public SetCookieCacheIterator() {
            iterator = cookies.iterator();
        }
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }
        @Override
        public Cookie next() {
            return iterator.next().getCookie();
        }
        @Override
        public void remove() {
            iterator.remove();
        }
    }
}