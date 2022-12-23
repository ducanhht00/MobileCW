package com.example.mobilecw;

public class Expensive {
    int Id;
    String ExpensiveName;
    String Amount;
    String Date;
    String Comment;

    public Expensive(int id, String expensiveName, String amount, String date, String comment) {
        Id = id;
        ExpensiveName = expensiveName;
        Amount = amount;
        Date = date;
        Comment = comment;

    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getExpensiveName() {
        return ExpensiveName;
    }

    public void setExpensiveName(String expensiveName) {
        ExpensiveName = expensiveName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
