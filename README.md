# Anti-fraud-system
A basic Anti fraud detection system for financial transaction part of Jetbrains Hyperskill Anti Fraud System project

The project uses fundamental Spring Boot features like:
> 
> Authentication
> 
> Authorization
> 
> Databases
> 
> Transactional Operations
> 
> Spring Beans, Components and Configurations
> 
> Project Lombok
> 
> Exceptions



## Description

This project demonstrates (in a simplified form) the principles of anti-fraud systems in the financial sector. The system contains an expanded role model, a set of REST endpoints responsible for interacting with users, and an internal transaction validation logic based on a set of heuristic rules.


#### Transaction Validation

This Anti-fraud System consists of the following validations:
- Heuristics rules that prevents fraudsters from illegally transferring money from an account.
- Card number and Ip Address (IPv4) block list.
- Rule-based system (Ip Address/Regions correlation). Ex:
    - A transaction containing a card number is `PROHIBITED` if:
        - There are transactions from more than 2 regions of the world other than the region of the transaction that is being verified in the last hour in the transaction history;
        - There are transactions from more than 2 unique IP addresses other than the IP of the transaction that is being verified in the last hour in the transaction history.
    - A transaction containing a card number is sent for `MANUAL_PROCESSING` if:
        - There are transactions from 2 regions of the world other than the region of the transaction that is being verified in the last hour in the transaction history;
        - There are transactions from 2 unique IP addresses other than the IP of the transaction that is being verified in the last hour in the transaction history.

  ##### Region Codes:
  | Code | Description                      |
  	|------|----------------------------------|
  | EAP  | East Asia and Pacific            |
  | ECA  | Europe and Central Asia          |
  | HIC  | High-Income countries            |
  | LAC  | Latin America and the Caribbean  |
  | MENA | The Middle East and North Africa |
  | SA   | South Asia                       |
  | SSA  | Sub-Saharan Africa               |


#### Authentication and Authorization

Enterprise applications like anti-fraud systems are used by different types of users with various access levels. In this project system different users have different rights to access various system parts following the role model below:

##### Role Model:
|                                 | ANONYMOUS | MERCHANT | ADMINISTRATOR | SUPPORT |  
|---------------------------------|-----------|----------|---------------|---------|  
| POST /api/auth/user             | +         | +        | +             | +       |  
| DELETE /api/auth/user           | -         | -        | +             | -       |  
| GET /api/auth/list              | -         | -        | +             | +       |  
| POST /api/antifraud/transaction | -         | +        | -             | -       |  
| /api/antifraud/suspicious-ip    | -         | -        | -             | +       |  
| /api/antifraud/stolencard       | -         | -        | -             | +       |  
| GET /api/antifraud/history      | -         | -        | -             | +       |  
| PUT /api/antifraud/transaction  | -         | -        | -             | +       |  

(+) = authorized | (-) = unauthorized

### Feedback

When working on an anti-fraud system, it is necessary to consider that the environment of transactions is constantly changing. There are many factors like the country's economy, the behavior of fraudsters, and the number of transactions happening concurrently that influence what we can call fraud.

This system contains a adaptation mechanisms called **feedback**. Feedback will be carried out manually by a `SUPPORT` specialist for completed transactions. Based on the feedback results, the system will change the limits of fraud detection algorithms following the special rules. The following table shows the logic of feedback system:

##### Feedback system:
| Transaction Feedback → Transaction Validity ↓ | ALLOWED              | MANUAL_PROCESSING | PROHIBITED           |
|-----------------------------------------------|----------------------|-------------------|----------------------|
| ALLOWED                                       | Exception            | ↓ max ALLOWED     | ↓ max ALLOWED/MANUAL |
| MANUAL_PROCESSING                             | ↑ max ALLOWED        | Exception         | ↓ max MANUAL         |
| PROHIBITED                                    | ↑ max ALLOWED/MANUAL | ↑ max MANUAL      | Exception            |

Formula:
- increasing the limit: new_limit = 0.8 * current_limit + 0.2 * value_from_transaction
- decreasing the limit: new_limit = 0.8 * current_limit - 0.2 * value_from_transaction

## Requirements

- Java 11+
- IntelliJ IDEA / Netbeans / Eclipse

## Usage

Start application in IDE 

```  
./Anti fraud system/src/main/java/com/example/antifraud/Antifraudsystem/AntiFraudSystemApplication.java
```  

or via command line:

```  
./gradlew bootRun  
```  