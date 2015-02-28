package com.github.knightliao.hermesjsonrpc.core.test.codec;

/**
 * @author liaoqiqi
 * @version 2014-9-2
 */
public class TestData {

    private int a = 0;
    private String data = "kjfdkljf";
    private Long cc = 45L;

    public int getA() {
        return a;
    }

    public Long getCc() {
        return cc;
    }

    public void setCc(Long cc) {
        this.cc = cc;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestData [a=" + a + ", data=" + data + ", cc=" + cc + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
        result = prime * result + ((cc == null) ? 0 : cc.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TestData other = (TestData) obj;
        if (a != other.a) {
            return false;
        }
        if (cc == null) {
            if (other.cc != null) {
                return false;
            }
        } else if (!cc.equals(other.cc)) {
            return false;
        }
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        return true;
    }

}
