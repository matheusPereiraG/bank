## Run the application

In order to run the application you just need java 21 installed on your machine, i recommend using sdkman to handle java
versions.

Then, navigate to the root folder of the project and run:

```bash 
./gradlew bootRun
```

This should open your application on your localhost with port 8080

## Business Logic

### Users

Users can be created solely with first name and last name.
Users can be deactivated, once deactivated every functionality of this deactivated user gets invalidated, ie, it cannot
create accounts, check balances, do and check transactions.

### Account

Users can create accounts with initial deposit amounts. The same user can have multiple accounts and make transfers
between these accounts.

### Transactions

There are three types of transactions:

- Withdraw -> The user can withdraw some amount from one account.
- Deposit -> The user can deposit some amount to one account.
- Transfer -> A user can transfer money to another account. The operation gets cancelled if the user tries to transfer
  money to the same account.

Then, we can also check the account history. The history includes all transactions that were done in that particular
account and which accounts were involved.

### Considerations

On a production level application, we would check the user permissions using authorization to determine if certain
operation could be completed, ex: deactivate user, withdraw from accounts, etc.

When making a money transaction we should make sure our update operation doesn't run into race conditions. This was not
considered also for simplicity.

The identifiers used in this solution are of type Long. In a production level application I would use Strings to store
these since it can store more identifiers than a Long or Integer.

BigDecimal was chosen as a data holder for the amount since it provides more precision than double or float. It is used
primarily for these financial purposes.

Currency was not approached in this solution. On a production level application, each user would have a default
currency (that could be changed anytime) and transactions would probably need to be aware of currencies before making
operations

## Test the API

While running these commands please make sure you are located in the root folder of the project. Otherwise, change the
path
for json files accordingly.

## User

### Add user

```bash
curl -i -X POST http://127.0.0.1:8080/user  \
     -H "Content-Type: application/json" \
     -d @src/main/resources/addUser.json
```

### Deactivate User

```bash
curl -i -X PATCH http://127.0.0.1:8080/user/deactivate/{user_id}
```

## Account

### Add Account

```bash 
curl -i -X POST http://127.0.0.1:8080/account  \
     -H "Content-Type: application/json" \
     -d @src/main/resources/addAccount.json
```

### Check balance

```bash 
curl -i -X GET http://127.0.0.1:8080/account/balance/{account_id}
```

## Transactions

### Withdraw from account

```bash
curl -i -X POST http://127.0.0.1:8080/transaction/withdraw \
     -H "Content-Type: application/json" \
     -d @src/main/resources/withdraw.json
```

### Deposit to account

```bash
curl -i -X POST http://127.0.0.1:8080/transaction/deposit \
     -H "Content-Type: application/json" \
     -d @src/main/resources/deposit.json
```

### Transfer to account

```bash
curl -i -X POST http://127.0.0.1:8080/transaction/transfer \
     -H "Content-Type: application/json" \
     -d @src/main/resources/transfer.json
```

### Transaction History

```bash
curl -i -X GET http://127.0.0.1:8080/transaction/history/{account_id}
```