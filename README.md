Simple Spring Boot REST HATEOAS API written in Kotlin (except one Java file), for demonstration purposes.

This demonstrates:

* implementation of a RESTful HATEOAS HAL API
* hashing and authentication of passwords with BCrypt
* restricting access to authenticated clients (only GET requests on root URL and mixes subdirectory are permitted without authentication)
* validation of input

API can be accessed live at: 

http://jsbr.us-west-2.elasticbeanstalk.com

Based on DJ Zyron mixes, it just enables a user to keep track of the last time they listened to a particular mix.

http://zyron.c64.org
https://www.discogs.com/user/Zyron/lists
