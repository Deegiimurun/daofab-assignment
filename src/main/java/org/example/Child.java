package org.example;

public class Child {
    int id;
    int paidAmount;
    Parent parent;

    public Child(int id, int paidAmount) {
        this.id = id;
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", paidAmount=" + paidAmount +
                ", parent=" + parent.id +
                '}';
    }
}
