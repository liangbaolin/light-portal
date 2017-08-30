# Light-portal-- User Management Service

User Management Service is the portal for Oath2 user manage


Basic features a user registration system should have.

As a user, I want to register, so that I can get access to content which requires registration

As a user, I want to confirm my email address after registration

As a user, I want to login and log out

As a user, I want to change my password

As a user, I want to change my email address

As a user, I want to be able to reset my password, so that I can login after I lost my password

As a user, I want to update my profile, so that I can provide my correct contact data

As a user, I want to close my account, so that I can close my relationship with that service I signed up for

As an admin, I want to manage (create/delete/update) users manually, so that staff members wouldn’t have to go over the registration process

As an admin, I want to create users manually, so that staff members wouldn’t have to go over the registration process

As an admin, I want to list all users, even those once who closed their account

As an admin, I want to be able to see users’ activity (login, logout, password reset, confirmation, profile update), so that I can comply with external audit requirements




Build project and run verification.


Get the light-portal project from github:

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

-- user change (change email)
```
curl -X PUT \
  http://localhost:8080/v1/user/1080408346077690 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{"screenName":"testUser","contactData":{"email":"aaa.bbb2@gmail.com","gender":"UNKNOWN"},"timezone":"CANADA_EASTERN","locale":"English (Canada)","emailChange":true,"passwordReset":false,"screenNameChange":false}'
```

