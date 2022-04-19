Use delivery;

# drop table if exists itemLine;
# drop table if exists items;
# drop table if exists Orders;
# drop table if exists Customers;
# drop table if exists Drones;
# drop table if exists Stores;
# drop table if exists Pilots;
# drop table if exists Users;
# drop table if exists System_Log;


create table IF not exists Users
(
	username varchar(100) not null,
    password varchar(100) not null,
    adminType varchar(50) not null default 'Admin',
    primary key(username)
);

REPLACE INTO Users(username, password, adminType)
values("Security_Admin", "1234", "Security Admin");

create table IF not exists System_Log
(
	logID int not null auto_increment,
    username varchar(100) not null,
    commandLine varchar(100) not null,
    resultMessage varchar(100) not null,
    timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
    primary key(logID)
);

create table IF not exists Customers
(
   customerID varchar(100) not null,
   firstName varchar(100) not null,
   lastName varchar(100) not null,
   phoneNumber varchar(100) not null,
   rating int not null,
   credits int not null,
   remainingCredit int not null,
   timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
   flag boolean DEFAULT false,
   primary key (customerID)
);

create table IF not exists Stores
(
	storeName varchar(100) not null,
	revenue int not null,
    timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
    flag boolean DEFAULT false,
	primary key (storeName)
);

create table IF not exists Orders
(
    storeName varchar(100) not null,
    orderID varchar(100) not null,
	droneID varchar(100) not null,
    totalCost int not null,
    totalWeight int not null,
    customerID varchar(100) not null,
    status varchar(100) not null,
    timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
    flag boolean DEFAULT false,
    primary key(storeName, orderID)
);

create table IF not exists Drones
(
	droneID varchar(100) not null,
    storeName varchar(100) not null,
    capacity int not null,
    remainingCap int not null,
    numsOrders int not null,
    fuel int not null,
    remainFuel int not null,
    pilotID varchar(100),
    timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
    flag boolean DEFAULT false,
    primary key(droneID, storeName)
);

create table IF not exists items
(
	itemName varchar(100) not null,
    storeName varchar(100) not null,
    weight int not null,
    primary key(itemName, storeName)
);

create table IF not exists itemLines
(
    storeName varchar(100) not null,
	orderId varchar(100) not null,
    itemName varchar(100) not null,
    lineQuantity int not null,
    lineCost int not null,
    lineWeight int not null,
    primary key(storeName, orderId, itemName)
);

create table IF not exists Pilots
(
	accountID varchar(100) not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    phoneNumber varchar(100) not null,
    taxID varchar(100) not null,
    licenseID varchar(100) not null,
    expcLevel int not null,
    droneID varchar(100),
    timeStamp datetime DEFAULT CURRENT_TIMESTAMP,
    flag boolean DEFAULT false,
    primary key(accountID)

#     primary key(accountID),
#     unique key AK_nq_licenseID(licenseID)

);

# alter table Orders add constraint FK_order_drone foreign key (droneID)
#       references Drones(droneID) on delete restrict on update restrict;
# alter table Orders add constraint FK_order_customer foreign key (customerID)
#       references Customers (customerID) on delete restrict on update restrict;
# alter table Drones add constraint FK_drone_store foreign key (storeName)
#       references Stores (storeName) on delete restrict on update restrict;
# alter table Drones add constraint FK_drone_pilots foreign key (pilotID)
#       references Pilots (accountID) on delete restrict on update restrict;
# alter table items add constraint FK_item_store foreign key (storeName)
#       references Stores (storeName) on delete restrict on update restrict;
# alter table itemLines add constraint FK_itemLine_order foreign key (orderID)
#       references Orders (orderID) on delete restrict on update restrict;
# alter table itemLines add constraint FK_itemLine_item foreign key (itemName)
#       references Items (itemName) on delete restrict on update restrict;
# alter table Pilots add constraint FK_pilot_drone foreign key (droneID)
#       references Drones (droneID) on delete restrict on update restrict;
