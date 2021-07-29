package com.liveme.demo.other;


public class A {

    private String name;

    private B bs;

    public A(String name, B bs) {
        this.name = name;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public B getBs() {
        return bs;
    }

    public void setBs(B bs) {
        this.bs = bs;
    }

}
