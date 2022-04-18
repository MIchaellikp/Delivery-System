package edu.gatech.cs6310;
import edu.gatech.cs6310.util.Archive;
import edu.gatech.cs6310.util.Init;
import edu.gatech.cs6310.util.logTool;
import edu.gatech.cs6310.util.SQLend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.Connection;
import java.text.ParseException;
import java.util.*;

public class DeliveryService {

    public void commandLoop(String username, Init data, Connection con, Scanner commandLineInput) throws ParseException {
        String wholeInputLine;
        String[] tokens;
        ArrayList<Pilot> pilots = data.getPilots();
        TreeMap<String,Customer> customers = data.getCustomers();
        TreeMap<String,Store> stores = data.getStores();
        logTool logTool = new logTool(con);

        Archive archive = new Archive(30, logTool);

        final String DELIMITER = ",";

        while (true) {
            Date date = new Date();
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);
                String result = null;
                if (tokens[0].equals("make_store")) {
                    /**
                     * Helper method to add a new store into system
                     *
                     * @param tokens[1] the content of storename
                     * @param tokens[2 the content of store revenue
                     */
                    Store newStore = new Store(tokens[1], Integer.parseInt(tokens[2]));
                    if(stores.containsKey(newStore.getName())){
                        result = "ERROR:store_identifier_already_exists";
                        System.out.println("ERROR:store_identifier_already_exists");
                    }else{
                        stores.put(newStore.getName(), newStore);
                        result = "OK:change_completed";
                        System.out.println("OK:change_completed");
                    }
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_stores")) {
                    /**
                     * Helper method to display all stores in system
                     */
                    for(Map.Entry<String,Store> s: stores.entrySet()){
                        if (!s.getValue().isFlag())
                            System.out.println(s.getValue().toString());
                    }
                    result = "OK:display_completed";
                    System.out.println("OK:display_completed");
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_all_stores")) {
                    /**
                     * Helper method to display all stores in system
                     */
                    // 1. use SQL Select ALL
                    for(Map.Entry<String,Store> s: stores.entrySet()){
                        System.out.println(s.getValue().toString_withArchiveState());
                    }
                    result = "OK:display_completed";
                    System.out.println("OK:display_completed");
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("sell_item")) {
                    /**
                     * Helper method to add a new item into the store
                     *
                     * @param tokens[1] the content of storename
                     * @param tokens[2] the content of new item's name
                     * @param tokens[3] the content of new item's weight
                     */
                    Item newItem = new Item(tokens[2], Integer.parseInt(tokens[3]));
                    if(stores.containsKey(tokens[1])){
                        Store store = stores.get(tokens[1]);
                        boolean addResult = store.addItem(newItem);
                        if (addResult) {
                            result = "OK:change_completed";
                            System.out.println("OK:change_completed");
                        } else {
                            result = "ERROR:item_identifier_already_exists";
                            System.out.println("ERROR:item_identifier_already_exists");
                        }
                    }else{
                        result = "ERROR:store_identifier_does_not_exist";
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    logTool.insertLog(username, date, wholeInputLine, result);
                } else if (tokens[0].equals("display_items")) {
                    /**
                     * Helper method to display all items in store
                     */
                    if(stores.containsKey(tokens[1])){
                        result = stores.get(tokens[1]).displayItems();
                        System.out.println(result);
                    }else{
                        result = "ERROR:store_identifier_does_not_exist";
                        System.out.println("ERROR:store_identifier_does_not_exist");
                    }
                    logTool.insertLog(username, date, wholeInputLine, result);
                } else if (tokens[0].equals("make_pilot")) {
                    /**
                     * Helper method to add a new item into the store
                     *
                     * @param tokens[1] the content of accountID
                     * @param tokens[2] the content of firstName
                     * @param tokens[3] the content of lastName
                     * @param tokens[4] the content of phoneNumber
                     * @param tokens[5] the content of taxID
                     * @param tokens[6] the content of licenseID
                     * @param tokens[7] the content of expcLevel
                     */
                    Pilot newp = new Pilot(tokens[1],tokens[2],tokens[3],tokens[4],
                                            tokens[5],tokens[6],Integer.parseInt(tokens[7]));
                    boolean finishLoop = true;
                    for(Pilot p: pilots){
                        if(p.getAccountID().equals(newp.getAccountID()) ){
                            finishLoop = false;
                            result = "ERROR:pilot_identifier_already_exists";
                            System.out.println("ERROR:pilot_identifier_already_exists");
                            break;
                        }else if( p.getLicenseID().equals(newp.getLicenseID())){
                            finishLoop = false;
                            result = "ERROR:pilot_license_already_exists";
                            System.out.println("ERROR:pilot_license_already_exists");
                            break;
                        }
                    }
                    if(finishLoop) {
                        pilots.add(newp);
                        Collections.sort(pilots);
                        result = "OK:change_completed";
                        System.out.println("OK:change_completed");
                    }
                    logTool.insertLog(username, date, wholeInputLine, result);
                } else if (tokens[0].equals("display_pilots")) {
                    /**
                     * Helper method to display all pilots in system
                     */
                    for(Pilot p : pilots){
                        if (!p.isFlag())
                            System.out.println(p.toString());
                    }
                    result = "OK:display_completed";
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_all_pilots")) {
                    /**
                     * Helper method to display all pilots in system
                     */
                    for(Pilot p : pilots){
                        System.out.println(p.toString_withArchiveState());
                    }
                    result = "OK:display_completed";
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("make_drone")) {
                    /**
                     * Helper method to add a new drone into the store
                     *
                     * @param tokens[1] the content of storename
                     * @param tokens[2] the content of drone's id
                     * @param tokens[3] the content of new drone's weight
                     * @param tokens[3] the content of new drone's fuel
                     */
                    Drone newDrone = new Drone(tokens[1], tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                    if(stores.containsKey(tokens[1])){
                        result = stores.get(tokens[1]).addDrone(newDrone);
                    }else{
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_drones")) {
                    /**
                     * Helper method to display all pilots in a store
                     *
                     * @param tokens[1] the content of store's name
                     */

                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).displayDrones();
                    }else {
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_all_drones")) {
                    /**
                     * Helper method to display all pilots in a store
                     *
                     * @param tokens[1] the content of store's name
                     */

                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).displayDrones_withArchiveState();
                    }else {
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("fly_drone")) {
                    /**
                     * Helper method to assign a pilot to a drone
                     *
                     * @param tokens[1] the content of store's name
                     * @param tokens[2] the content of drone's id
                     * @param tokens[3] the content of pilot's id
                     */
                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).flyDrone(tokens[2],tokens[3], pilots);
                    }else {
                        result =  "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("make_customer")) {
                    /**
                     * Helper method to create a new customer in system
                     *
                     */
                    Customer newCustomer = new Customer(tokens[1],tokens[2],tokens[3],tokens[4],Integer.parseInt(tokens[5]),Integer.parseInt(tokens[6]));
                    if(customers.containsKey(newCustomer.getAccount())){
                        result =  "ERROR:customer_identifier_already_exists";
                    }else{
                        customers.put(newCustomer.getAccount(), newCustomer);
                        result =  "OK:change_completed";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_customers")) {
                    /**
                     * Helper method to display all customers in system
                     */
                    for(Map.Entry<String,Customer> c: customers.entrySet()){
                        if (!c.getValue().isFlag())
                            System.out.println(c.getValue().toString());
                    }
                    result = "OK:display_completed";
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_all_customers")) {
                    /**
                     * Helper method to display all customers in system
                     */
                    for(Map.Entry<String,Customer> c: customers.entrySet()){
                        System.out.println(c.getValue().toString_withArchiveState());
                    }
                    result = "OK:display_completed";
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("start_order")) {
                    /**
                     * Helper method to new order for a customer
                     */
                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).createOrder(tokens[2],tokens[3],tokens[4], customers);
                    }else{
                        //System.out.println("ERROR:store_identifier_does_not_exist");
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_orders")) {
                    /**
                     * Helper method to display all orders in store
                     *
                     * @param tokens[1] the content of storename
                     */
                    if(stores.containsKey(tokens[1])) {
                        stores.get(tokens[1]).displayOrders();
                        result = "OK:display_completed";
                    }else {
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("display_all_orders")) {
                    /**
                     * Helper method to display all orders in store
                     *
                     * @param tokens[1] the content of storename
                     */
                    if(stores.containsKey(tokens[1])) {
                        stores.get(tokens[1]).displayOrders_withArchiveState();
                        result = "OK:display_completed";
                    }else {
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("request_item")) {
                    /**
                     * Helper method to add items into the order
                     */
                    if(stores.containsKey(tokens[1])) {
                        int q = Integer.parseInt(tokens[4]);
                        int p = Integer.parseInt(tokens[5]);
                        result = stores.get(tokens[1]).requestItem(tokens[2],tokens[3],q,p);
                    }else{
                        //System.out.println("ERROR:store_identifier_does_not_exist");
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);

                } else if (tokens[0].equals("purchase_order")) {
                    /**
                     * Helper method to help customer to purchase an order
                     */
                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).finishOrder(tokens[2]);
                    }else{
                        //System.out.println("ERROR:store_identifier_does_not_exist");
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);
                } else if (tokens[0].equals("cancel_order")) {
                    if(stores.containsKey(tokens[1])) {
                        result = stores.get(tokens[1]).cancelOrder(tokens[2]);
                    }else{
                        //System.out.println("ERROR:store_identifier_does_not_exist");
                        result = "ERROR:store_identifier_does_not_exist";
                    }
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);
                // } else if (tokens[0].equals("edit_settings")) { todo - add command loop for edit settings

                } else if (tokens[0].equals("display_system_log")){ // { todo - add command loop for display system log
                    result = logTool.printLog(username);
                    System.out.println(result);
                    logTool.insertLog(username, date, wholeInputLine, result);
                } else if (tokens[0].equals("stop")) {
                    result = "stop acknowledged";
                    System.out.println(result);
                    // todo - determine archive flag before storing back to DB
                    // Archive all classes on exit of DeliveryService
                    logTool.insertLog(username, date, wholeInputLine, result);
                    archive.archive_all(stores, customers, pilots);
                    //Backupdatabase
                    SQLend sqLend = new SQLend(con,pilots,customers,stores);
                    break;

                } else {
                    System.out.println(wholeInputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }
}
