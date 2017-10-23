package com.dj.studyframe.http.download;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class BaseEntity<T> implements Serializable{
    private static final long serialVersionUID = 1L;
    public BaseEntity() {
    }

    //序列化与反序列化,序列化的前提是所有的成员变量都要序列化
    public T copy() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object result = objectInputStream.readObject();
            return (T) result;
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClassNotFoundException classNot) {
            classNot.printStackTrace();
        } finally {
            if(byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

            if(objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

        }

        return null;
    }



}
