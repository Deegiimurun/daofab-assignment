package org.example;

import org.json.JSONObject;

import java.util.ArrayList;

public class Parent {
    int id;
    String sender;
    String receiver;
    int totalAmount;
    ArrayList<Child> childs = new ArrayList<>();

    public Parent(int id, String sender, String receiver, int totalAmount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.totalAmount = totalAmount;
    }

    public int getTotalPaidAmount () {
        int totalPaidAmount = 0;

        for (Child child : childs) {
            totalAmount += child.paidAmount;
        }

        return totalPaidAmount;
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("sender", sender)
                .put("receiver", receiver)
                .put("totalAmount", totalAmount)
                .put("totalPaidAmount", this.getTotalPaidAmount());
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", totalAmount=" + totalAmount +
                ", childs=" + childs +
                '}';
    }
}
