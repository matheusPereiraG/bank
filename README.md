## Run the application

In order to run the application you just need java 21 installed on your machine, i recommend using sdkman to handle java
versions.

Then, navigate to the root folder of the project and run:

```bash 
./gradlew bootRun
```

This should open your application on your localhost with port 8080

## Test the API

While running these commands please make sure you are located in the root folder of the project. Otherwise change the path
for json files accordingly

## User

### Add user

```bash
curl -i -X POST http://127.0.0.1:8080/user  \
     -H "Content-Type: application/json" \
     -d @src/main/resources/addUser.json
```

### Deactivate User
```bash
curl -i -X PATCH http://127.0.0.1:8080/user/deactivate/0
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
curl -i -X GET http://127.0.0.1:8080/account/balance/0
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
curl -i -X GET http://127.0.0.1:8080/transaction/history/0
```