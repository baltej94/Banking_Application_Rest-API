Bank Application Rest API

Rest API application for bank using Spring Boot

Offered services:
-Customer creation, updation, getting customer details, delete customer
-Account creation
-Account transactions: deposit, withdrawal and transfer
-Account information: account details, list of transaction

It uses a PostgreSql database to handle the persistence layer.

PREREQUISITES:
lombok jar
Postgres database

Open your IDE and Import as existing maven project in your workspace
- Import existing maven project
- Run mvn clean install
- Run As Spring Boot App


API:

-GET  /allCustomers

-POST /createCustomer

-GET  /getCustomer/{customerNumber}

-POST /updateCustomer/{customerNumber}

-POST /deleteCustomer/{customerNumber}

-POST /addCustomerAccount/{customerNumber}

-GET  /getAccount/{accountNumber}

-POST /depositFunds/{customerNumber}

-POST /transferFunds/{customerNumber}

-POST /getTransactions/{accountNumber}

