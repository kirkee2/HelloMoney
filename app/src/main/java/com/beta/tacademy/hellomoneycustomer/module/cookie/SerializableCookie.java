
package com.beta.tacademy.hellomoneycustomer.module.cookie;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/*
  영속성을 보장하기 위한  직렬화 클래스
 */
public class SerializableCookie implements Serializable {
    private static final String TAG = SerializableCookie.class.getSimpleName();

    private static final long serialVersionUID = -8594045714036645534L;

    private transient Cookie cookie; //직렬화 시에 쿠키객체는 제외

    //영속적 객체로 만들기 위해 스트림으로 감싼다.
    public String serialWriteObject(Cookie cookie) {
        this.cookie = cookie;

        ByteArrayOutputStream byteArrayObj = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;

        try {
            objectOutputStream = new ObjectOutputStream(byteArrayObj);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        } catch (IOException e) {
            Log.d(TAG, "쿠키직렬화 중 문제 발생", e);
            return null;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "쿠키 직렬화 중 문제 발생!", e);
                }
            }
        }

        return byteArrayToHexString(byteArrayObj.toByteArray());
    }
    /*
      16진수로 값을 변환/복원 한다.(간단한 암호화)
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
    /*
      역직렬화를 통해 쿠키를 객체화 한다
     */
    public Cookie deserialReadObject(String encodedCookie) {

        byte[] bytes = hexStringToByteArray(encodedCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                bytes);

        Cookie cookie = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).cookie;
        } catch (IOException e) {
            Log.d(TAG, "쿠키 역직렬화 중 문제 발생", e);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "역직렬화 중~~ 원본 클래스를 찾을 수 없습니다.", e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, " 문제 발생!", e);
                }
            }
        }
        return cookie;
    }

    private static long NON_VALID_EXPIRES_AT = -1L; //유효기간 flag field

    /*
     직렬화 역직렬화를 세분화 시킨다.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeLong(cookie.persistent() ? cookie.expiresAt() : NON_VALID_EXPIRES_AT);
        out.writeObject(cookie.domain());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
        out.writeBoolean(cookie.httpOnly());
        out.writeBoolean(cookie.hostOnly());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder();

        builder.name((String) in.readObject());

        builder.value((String) in.readObject());

        long expiresAt = in.readLong();
        if (expiresAt != NON_VALID_EXPIRES_AT) {
            builder.expiresAt(expiresAt);
        }

        final String domain = (String) in.readObject();
        builder.domain(domain);

        builder.path((String) in.readObject());

        if (in.readBoolean())
            builder.secure();

        if (in.readBoolean())
            builder.httpOnly();

        if (in.readBoolean())
            builder.hostOnlyDomain(domain);

        cookie = builder.build();
    }
}