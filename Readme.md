Bank Application Rest API

Rest API application for bank using Spring Boot

Offered services:
-Customer creation, updation, getting customer details, delete customer
-Account creation
-Account transactions: deposit, withdrawal and transfer
-Account information: account details, list of transaction

It uses a PostgreSql database and spring data to handles the persistence layer.

API
GET  /allCustomers
POST /createCustomer
GET  /getCustomer/{customerNumber}
POST /updateCustomer/{customerNumber}
POST /deleteCustomer/{customerNumber}
POST /addCustomerAccount/{customerNumber}
GET  /getAccount/{accountNumber}
POST /depositFunds/{customerNumber}
POST /transferFunds/{customerNumber}
POST /getTransactions/{accountNumber}