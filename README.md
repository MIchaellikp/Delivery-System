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

### Auditability
There is a default "Security_Admin" user that has the highest privilege of viewing system logs.
Other users are added to the database through the Sign Up functionality before the main delivery system application.

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
3. Complete a few commands to store as sample log.
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
8. Perform more commands under this account for sample log.
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
11. Test the user functionality by performing more commands.
```cmd
> display_stores
name:kroger,revenue:1000.00USD
OK:display_completed
> display_pilots
name:Finneas_Fig,phone:888-888-8888,taxID:890-12-3456,licenseID:panam_10,experience:33
name:Gillian_Grape,phone:999-999-9999,taxID:234-56-7890,licenseID:twa_21,experience:31
OK:display_completed
```
12. Viewing system log as user aaaa will only display commands done by user aaaa.
```cmd
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
14. Lastly, log in as Security_Admin using password 1234. Viewing the system log should display all logs.
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
