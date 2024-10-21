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

### Add user

```bash
curl -X POST http://127.0.0.1:8080/user  \
     -H "Content-Type: application/json" \
     -d @src/main/resources/addUser.json
```