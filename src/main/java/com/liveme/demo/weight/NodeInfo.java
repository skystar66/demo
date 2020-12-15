package com.liveme.demo.weight;

public class NodeInfo {

    private int weight;

    private String name;

    public NodeInfo(int weight, String name) {
        this.weight = weight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public NodeInfo(int weight) {
        this.weight = weight;
    }

    public NodeInfo() {
    }


    @Override
    public String toString() {
        return "NodeInfo{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                '}';
    }
}
