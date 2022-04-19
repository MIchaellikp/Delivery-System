package edu.gatech.cs6310.util;

import edu.gatech.cs6310.*;
import edu.gatech.cs6310.util.logTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Archive {

    private int threshold;
    private logTool logTool;
    private void insertLog(String s) throws SQLException {
        this.logTool.insertLog("system", new Date(), "system archiving " + s, "system automatically archived");
    }

    public Archive(int threshold, logTool logTool){
        this.threshold = threshold;
        this.logTool = logTool;
    }

    public void set_threshold(int threshold){
        this.threshold = threshold;
    }

    public void archive_all(TreeMap<String, Store> stores, TreeMap<String, Customer> customers, ArrayList<Pilot> pilots) throws SQLException {

        Date date = new Date();
        long diff;
        long minutes;
        //Archive stores and drones
        for(Map.Entry<String,Store> s: stores.entrySet()) {

            for (Order o: s.getValue().getOrders()){
                diff = date.getTime() - o.getTimeStamp().getTime();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes >= threshold && !o.isFlag()) {
                    s.getValue().cancelOrder(o.getOrderId());
                    this.insertLog("Order: " + o.getOrderId());
                }
            }

            diff = date.getTime() - s.getValue().getTimeStamp().getTime();
            minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold && s.getValue().getOrders().isEmpty() && s.getValue().getDrones().isEmpty() && s.getValue().getCatalog().isEmpty() && !s.getValue().isFlag()){
                s.getValue().setFlag(true);
                this.insertLog("Store: " + s.getKey());
            }

            for (Drone d: s.getValue().getDrones()){
                diff = date.getTime() - d.getTimeStamp().getTime();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes >= threshold && d.getPilot() == null && d.getNumOrders() == 0 && !d.isFlag()) {
                    d.setFlag(true);
                    this.insertLog("Drone: " + d.getId());
                }
            }
        }

        //Archive customers
        for(Map.Entry<String,Customer> c: customers.entrySet()) {
            diff = date.getTime() - c.getValue().getTimeStamp().getTime();
            minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold && c.getValue().getRemainingCredits() == c.getValue().getCredits() && !c.getValue().isFlag()) {
                c.getValue().setFlag(true);
                this.insertLog("Customer: " + c.getKey());
            }
        }
        //Archive pilots
        for(Pilot p: pilots){
            diff = date.getTime() - p.getTimeStamp().getTime();
            minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold && p.getDrone() == null && !p.isFlag()) {
                p.setFlag(true);
                this.insertLog("Pilot: " + p.getAccountID());
            }
        }

        //Orders are archived immediately after delivered in Store.finishOrder()
    }
}
