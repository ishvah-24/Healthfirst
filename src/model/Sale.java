package model;

import java.sql.Timestamp;

public class Sale {

    private int sale_id;
    private Timestamp sale_date;
    private double total_amount;
    private int user_id;

    public Sale(int sale_id, Timestamp sale_date, double total_amount, int user_id) {
        this.sale_id = sale_id;
        this.sale_date = sale_date;
        this.total_amount = total_amount;
        this.user_id = user_id;
    }

    public int getSale_id() {
        return sale_id;
    }

    public Timestamp getSale_date() {
        return sale_date;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
}