package com.vector.shop.user;

public class Test {
    enum Type {
        MAN,WOMAN;

        @Override
        public String toString() {
            return this.name().toLowerCase()+"Error";
        }
    }

    public static void main(String[] args) {
        System.out.println(Type.MAN );
    }
}
