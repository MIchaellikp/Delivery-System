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

    public void archive_all(TreeMap<String, Store> stores, TreeMap<String, Customer> customers, ArrayList<Pilot> pilots) throws SQLException {

        Date date = new Date();

        //Archive stores and drones
        for(Map.Entry<String,Store> s: stores.entrySet()) {
            long diff = date.getTime() - s.getValue().getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold){
                s.getValue().setFlag(true);
                this.insertLog("Store: " + s.getKey());
            }
            for (Drone d: s.getValue().getDrones()){
                diff = date.getTime() - d.getTimeStamp().getTime();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes >= threshold) {
                    d.setFlag(true);
                    this.insertLog("Drone: " + d.getId());
                }
            }

            for (Order o: s.getValue().getOrders()){
                diff = date.getTime() - o.getTimeStamp().getTime();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes >= threshold) {
                    s.getValue().cancelOrder(o.getOrderId());
                    this.insertLog("Order: " + o.getOrderId());
                }
            }
        }

        //Archive customers
        for(Map.Entry<String,Customer> c: customers.entrySet()) {
            long diff = date.getTime() - c.getValue().getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold) {
                c.getValue().setFlag(true);
                this.insertLog("Customer: " + c.getKey());
            }
        }
        //Archive pilots
        for(Pilot p: pilots){
            long diff = date.getTime() - p.getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold) {
                p.setFlag(true);
                this.insertLog("Pilot: " + p.getAccountID());
            }
        }

        //Orders are archived immediately after delivered in Store.finishOrder()
    }
}
