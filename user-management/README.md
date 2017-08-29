# Light-portal-- User Management Service

User Management Service is the portal for Oath2 user manage


Checkout related projects.


Get the example project from github:

```
cd ~/networknt
git clone git@github.com:networknt/light-portal.git

https://github.com/networknt/light-portal.git
cd ~/networknt/light-portal/user-management
mvn clean install
```




 Run docker compose to start microservices

   cd ~/networknt/light-portal/user-management

   -- docker-compose up



# Test to verify result:

— new user signing
```
curl -X POST \
  http://localhost:8080/v1/user \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
   -d '{"screenName":"testUser","contactData":{"email":"aaa.bbb@gmail.com","firstName":"test1","lastName":"bbb1","addresses":[{"country":"CA","state":"AK","city":"BaBa","addressLine1":"222 Bay Street","addressType":"SHIPPING"}],"gender":"MALE"},"timezone":"CANADA_EASTERN","locale":"English (Canada)","password":"12345678","host":"google","emailChange":false,"passwordReset":false,"screenNameChange":false}
'
```

— active user by the link in the confirm email (replace the token from DB)
```
curl -X PUT \
  http://localhost:8080/v1/user/token/0000015e2a49af26-0242ac1200020000 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json'
```

— get ALL user list (for admin user)
```
curl -X GET \
  http://localhost:8080/v1/user \
  -H 'cache-control: no-cache' \
```

— get user by login in name
```
curl -X GET \
  'http://localhost:8080/v1/user/name?name=testUser' \
  -H 'cache-control: no-cache' \
 ```

— user login
```
curl -X PUT \
  http://localhost:8080//v1/user/login \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"nameOrEmail":"testUser","password":"12345678"}'
```