
package com.beta.tacademy.hellomoneycustomer.module.cookie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/*
  Okhttp3에서 제공하는 CookieJar를 확장하고 등록하는 클래스
 */
public class PersistentCookieJar implements ClearableCookieJar {

    private NonPersistentCookieCache cache;
    private CookiePersistor persistor;

    //처음 OkHttpClient를 생성시 사용함.
    public PersistentCookieJar(NonPersistentCookieCache cache, CookiePersistor persistor) {
        this.cache = cache;
        this.persistor = persistor;
        this.cache.addNonPersistenceAll(persistor.loadAll());
    }
    /*
      서버 응답에 쿠키를 저장하는 부분을 재정의하여 구현
     */
    @Override
     public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cache.addNonPersistenceAll(cookies);
        persistor.saveAll(filterPersistentCookies(cookies));
    }
    private static List<Cookie> filterPersistentCookies(List<Cookie> cookies) {
        List<Cookie> persistentCookies = new ArrayList<>();

        for (Cookie cookie : cookies) {
            if (cookie.persistent()) { //영속성 저장 쿠키인지를 체크
                persistentCookies.add(cookie);
            }
        }
        return persistentCookies;
    }
    /*
      요청헤더에 쿠키를 세팅하기 위해 쿠키를 빼오는 메소드를 재정의(CookieJar)
     */
    @Override
     public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Cookie> it = cache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();

            if (isCookieExpired(currentCookie)) { //소멸시간이 지난 쿠키는 제거
                cookiesToRemove.add(currentCookie);
                it.remove();

            } else if (currentCookie.matches(url)) { //쿠키 도메인이 맞는지 체크
                validCookies.add(currentCookie);
            }
        }
        //현재 저장된 쿠키의 유효기간이 끝난 것은 모두 제거
        persistor.removeAll(cookiesToRemove);

        return validCookies;
    }
    /*
      쿠키의 유효기간(소멸시간)을 체크
     */
    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }
    /*
      휘발성의 쿠키를 모두 제거하고 영속적인 쿠키를 세팅 하는 곳
     */
    @Override
    public synchronized void clearSession() {
        cache.clearNonPersistendceCookies();
        cache.addNonPersistenceAll(persistor.loadAll());

    }
    /*
     모두 제거(영속성쿠키 포함)
     */
    @Override
    public synchronized void clear() {
        cache.clearNonPersistendceCookies();
        persistor.clear();
    }
}
