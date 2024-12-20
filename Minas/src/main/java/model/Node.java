package model;

public class Node<T> {
    private String name;
    private T data;

    public Node(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

