# Spring-security from a-z
### Protected Application
#### _Notes_
- Uses Java 17  
- Follow commit steps to understand the flow.
-[Create Client id and secrete in GCP]: (https://support.google.com/cloud/answer/6158849?hl=en)


#### Run the Application
```
./gradlew bootrun
```
#### Access below URL  
http://localhost:8080/  
http://localhost:8080/secured  
http://localhost:8080/logout


#### Test Custom filter
##### Success
```shell
 curl localhost:8080/secured -H "x-custom-password: cx-pword" -v 
 ```
```shell
 curl localhost:8080/secured -H "x-custom-password: cx-passwd" -v 
 ```
##### Failed
```shell
 curl localhost:8080/secured -H "x-custom-password: cx-pword-123" -v 
 ```