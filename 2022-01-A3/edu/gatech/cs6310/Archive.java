package edu.gatech.cs6310;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Archive {

    private int threshold;

    public Archive(int threshold){
        this.threshold = threshold;
    }

    public void archive_all(TreeMap<String,Store> stores, TreeMap<String,Customer> customers, ArrayList<Pilot> pilots){

        Date date = new Date();

        //Archive stores and drones
        for(Map.Entry<String,Store> s: stores.entrySet()) {
            long diff = date.getTime() - s.getValue().getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold)
                s.getValue().setFlag(true);
            for (Drone d: s.getValue().getDrones()){
                diff = date.getTime() - d.getTimeStamp().getTime();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes >= threshold)
                    d.setFlag(true);
            }
        }

        //Archive customers
        for(Map.Entry<String,Customer> c: customers.entrySet()) {
            long diff = date.getTime() - c.getValue().getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold)
                c.getValue().setFlag(true);
        }
        //Archive pilots
        for(Pilot p: pilots){
            long diff = date.getTime() - p.getTimeStamp().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (minutes >= threshold)
                p.setFlag(true);
        }

        //Orders are archived immediately after delivered in Store.finishOrder()
    }
}
