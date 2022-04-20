# CS6310 - Delivery System Assignment 5 - Group 3
## Group Members
* Gary Yen (gyen6)
* Kunpeng Li (kli465)
* Zhaonan Sun (zsun300)
* Wen Luo (wluo72)
* Wa Chu (wchu42)
## Setup
Please run the docker script to set up a new container that hosts MySQL database.
```cmd
<project root>/2022-01-A3/setup_mysql_docker.sh
```
Run application .jar file provided.
```cmd
CS6310.jar
```
Alternatively, request access to the github repo and pull project to build and run Main.java.
## Non-functional Enhancement Areas
1) Configurability
   1) archive threshold (in minutes, default 30 minutes)
   2) display weight unit (LB/KG, default LB)
   3) display currency (USD/EUR/CNY/JPY, default USD)
2) Archivability
   1) archive orders when purchase order
   2) archive objects when threshold met and related objects are archived
3) Auditability (assigned)
   1) simple sign up and log in system
   2) log operation calls for each user
   3) different log display based on user role

## Test Cases
### Configurability

### Archivability
We have a threshold to check whether the system should archive orders, stores, drones, customers and pilots. In our system, the default archiving threshold is 30 minutes. 

1. To test the archivability, we firstly set our archiving threshold to 1 minutes.
```cmd
edit_settings,threshold,1
> edit_settings,threshold,1
OK:edit_setting_completed
> display_settings
Archive Threshold:	1 minutes
Display Currency:	USD
Display Weight Unit:	LB
OK:display_completed
```
2. Make two stores and added two items to one of the stores
```cmd
> make_store,kroger,33000
OK:change_completed
> make_store,publix,33000
OK:change_completed
> sell_item,kroger,pot_roast,5
OK:change_completed
> sell_item,kroger,cheesecake,4
OK:change_completed
```
3. Make one pilot and two drones. Assign one drone to the pilot
```cmd
> make_pilot,ffig8,Finneas,Fig,888-888-8888,890-12-3456,panam_10,33
OK:change_completed
> make_drone,kroger,1,40,1
OK:change_completed
> make_drone,kroger,2,20,3
OK:change_completed
> fly_drone,kroger,1,ffig8
OK:change_completed
```
4. Make two customers and added three orders to one of the customers
```cmd
> make_customer,aapple2,Alana,Apple,222-222-2222,4,100
OK:change_completed
> make_customer,ccherry4,Carlos,Cherry,444-444-4444,5,300
OK:change_completed
> start_order,kroger,purchaseA,1,aapple2
OK:change_completed
> start_order,kroger,purchaseB,1,aapple2
OK:change_completed
> start_order,kroger,purchaseC,1,aapple2
OK:change_completed
```
5. Add one item to the first two orders and deliver one order. The order is expected to be archived after it's delivered.
```cmd
> request_item,kroger,purchaseA,pot_roast,3,10
OK:change_completed
> request_item,kroger,purchaseB,pot_roast,3,10
OK:change_completed
> purchase_order,kroger,purchaseA
OK:change_completed
```
6. We stop the system and the system will now automatically archive related objects.
```cmd
> stop
stop acknowledged
simulation terminated
```
7. Restart the system and sign in to the system
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
Please type L to Log in or type S to Sign up or E to Exit:
L
Please enter username
a
Please enter password
1234
Welcome user, a
```
8. Display all stores. Store publix is archived because it doesn't contain any items and its last activity has past the 1-minute threshold. If we use display_stores, it will only show active stores by default
```cmd
> display_all_stores
name:kroger,revenue:33030.00USD (Active)
name:publix,revenue:33000.00USD (Archived)
OK:display_completed
> display_stores
name:kroger,revenue:33030.00USD
OK:display_completed
```
9. Display all drone in kroger. Drone 2 is archived because neither it is assigned to a pilot nor it is assigned any orders. Its last activity has past the 1-minute threshold.
```cmd
> display_all_drones,kroger
droneID:1,total_cap:40.0LB,num_orders:0,remaining_cap:40.0LB,trips_left:0,flown_by:Finneas_Fig (Active)
droneID:2,total_cap:20.0LB,num_orders:0,remaining_cap:20.0LB,trips_left:3 (Archived)
OK:display_completed
> display_drones,kroger
droneID:1,total_cap:40.0LB,num_orders:0,remaining_cap:40.0LB,trips_left:0,flown_by:Finneas_Fig
OK:display_completed
```
10. Display all customers. Carlos_Cherry is archived because she doesn't have any pending offers and its last activity has past the 1-minute threshold.
```
> display_all_customers
name:Alana_Apple,phone:222-222-2222,rating:4,credit:70.00USD (Active)
name:Carlos_Cherry,phone:444-444-4444,rating:5,credit:300.00USD (Archived)
OK:display_completed
> display_customers
name:Alana_Apple,phone:222-222-2222,rating:4,credit:70.00USD
OK:display_completed
```
11. Display all pilots. The only pilot has been assigned one drone, so it's not archived even if its last activity has past the 1-minute threshold.
```
> display_all_pilots
name:Finneas_Fig,phone:888-888-8888,taxID:890-12-3456,licenseID:panam_10,experience:34 (Active)
OK:display_completed
> display_pilots
name:Finneas_Fig,phone:888-888-8888,taxID:890-12-3456,licenseID:panam_10,experience:34
OK:display_completed
```
12. Display all orders in kroger. Order purchaseA is archived because it's delivered. Order purchase B and C are archived because their last activity has past the 1-minute threshold. They are also cancelled as they are archived.
```cmd
> display_all_orders,kroger
orderID:purchaseA (Archived)
item_name:pot_roast,total_quantity:3,total_cost:30.00USD,total_weight:15.0LB
orderID:purchaseB (Archived)
item_name:pot_roast,total_quantity:3,total_cost:30.00USD,total_weight:15.0LB
orderID:purchaseC (Archived)
OK:display_completed
display_orders,kroger
> display_orders,kroger
OK:display_completed
```


### Auditability
There is a default "Security_Admin" user that has the highest privilege of viewing system logs.
Other users are added to the database through the Log-in/Sign-up menu before the main delivery system application.

1. This is the initial screen of the application.
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
```
2. Signing up for an account. Completing sign up will automatically log the user in.
```cmd
Please type L to Log in or type S to Sign up or E to Exit:
S
Please enter username
aaaa
Please enter password
aaaa
Please enter password again (Note: it must matches with password)
aaaa
Welcome user, aaaa
```
3. Complete a few commands to store as sample log as user *aaaa*
```cmd
make_store,kroger,1000
> make_store,kroger,1000
OK:change_completed
make_store,kroger,2000
> make_store,kroger,2000
ERROR:store_identifier_already_exists
```
4. Exit the system to test another sign up functionality.
```cmd
> stop
stop acknowledged
simulation terminated
```
5. Run the application again. This time test the sign up functionality for error cases.
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
Please type L to Log in or type S to Sign up or E to Exit:
S
Please enter username
aaaa
Please enter password
aaaa
Please enter password again (Note: it must matches with password)
aaaa
Username already exists.
```
6. When username already exist, sign up will not be successful. You have the option to type Y to return to sign in menu 
or N to sign up again.
```cmd
Do you want to quit? (Y/N)
N
Please enter username
bbbb
Please enter password
bbbb
Please enter password again (Note: it must matches with password)
aaaa
Passwords do not match.
```
7. When passwords in sign up do not match, sign up will not be successful. We will sign up another account successfully this time.
```cmd
Do you want to quit? (Y/N)
N
Please enter username
bbbb
Please enter password
bbbb
Please enter password again (Note: it must matches with password)
bbbb
Welcome user, bbbb
```
8. Perform more commands under this account for sample log as user *bbbb*.
```cmd
make_pilot,ffig8,Finneas,Fig,888-888-8888,890-12-3456,panam_10,33
> make_pilot,ffig8,Finneas,Fig,888-888-8888,890-12-3456,panam_10,33
OK:change_completed
make_pilot,ggrape17,Gillian,Grape,999-999-9999,234-56-7890,twa_21,31
> make_pilot,ggrape17,Gillian,Grape,999-999-9999,234-56-7890,twa_21,31
OK:change_completed
> stop
stop acknowledged
simulation terminated

Process finished with exit code 0
```
9. Rerun the application to use the Log In function.
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
Please type L to Log in or type S to Sign up or E to Exit:
L
Please enter username
cccc
Please enter password
cccc
Username or password is wrong.
```
10. Wrong credentials will display the error and allow user to either try again or return to log in menu.
```cmd
Do you want to quit? (Y/N)
N
Please enter username
aaaa
Please enter password
aaaa
Welcome user, aaaa
```
11. Test the user functionality by performing more commands as user *aaaa*.
```cmd
display_stores
> display_stores
name:kroger,revenue:1000.00USD
OK:display_completed
display_pilots
> display_pilots
name:Finneas_Fig,phone:888-888-8888,taxID:890-12-3456,licenseID:panam_10,experience:33
name:Gillian_Grape,phone:999-999-9999,taxID:234-56-7890,licenseID:twa_21,experience:31
OK:display_completed
```
12. Viewing system log as user *aaaa* will only display commands done by user aaaa.
```cmd
display_system_log
> display_system_log
aaaa / make_store,kroger,1000 / OK:change_completed / 2022-04-19 21:36:00
aaaa / make_store,kroger,2000 / ERROR:store_identifier_already_exists / 2022-04-19 21:36:59
aaaa / stop / stop acknowledged / 2022-04-19 21:38:19
aaaa / display_stores / OK:display_completed / 2022-04-19 21:50:45
aaaa / display_pilots / OK:display_completed / 2022-04-19 21:50:51
OK:display_completed
```
13. Exit from the system and rerun the application. This time test the exit function at the log in screen.
```cmd
stop
> stop
stop acknowledged
simulation terminated

Process finished with exit code 0
```
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
Please type L to Log in or type S to Sign up or E to Exit:
E

Process finished with exit code 0
```
14. Lastly, log in as *Security_Admin* using password 1234. Viewing the system log should display all logs.
```cmd
Connection established!
Database initialization completed!
Welcome to the Grocery Express Delivery Service!
Please type L to Log in or type S to Sign up or E to Exit:
L
Please enter username
Security_Admin
Please enter password
1234
Welcome user, Security_Admin
display_system_log
> display_system_log
aaaa / make_store,kroger,1000 / OK:change_completed / 2022-04-19 21:36:00
aaaa / make_store,kroger,2000 / ERROR:store_identifier_already_exists / 2022-04-19 21:36:59
aaaa / stop / stop acknowledged / 2022-04-19 21:38:19
bbbb / make_pilot,ffig8,Finneas,Fig,888-888-8888,890-12-3456,panam_10,33 / OK:change_completed / 2022-04-19 21:45:33
bbbb / make_pilot,ggrape17,Gillian,Grape,999-999-9999,234-56-7890,twa_21,31 / OK:change_completed / 2022-04-19 21:46:50
bbbb / stop / stop acknowledged / 2022-04-19 21:47:14
aaaa / display_stores / OK:display_completed / 2022-04-19 21:50:45
aaaa / display_pilots / OK:display_completed / 2022-04-19 21:50:51
aaaa / display_system_log / OK:display_completed / 2022-04-19 21:52:03
aaaa / stop / stop acknowledged / 2022-04-19 21:52:03
OK:display_completed
stop
> stop
stop acknowledged
simulation terminated

Process finished with exit code 0
```
