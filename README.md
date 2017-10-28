Simple Spring Boot REST HAL API written in Kotlin (except one Java file), for demonstration purposes.

This application stores the dates when a listener last listened to [mixes by DJ Zyron](http://zyron.c64.org/mixes.php).  It also stores the URIs of [associated list data at Discogs](https://www.discogs.com/user/Zyron/lists?page=1&limit=100&header=1).

This is a companion project to the [Android Zyronator](https://github.com/jmkapp/Android-Kotlin-Zyronator), which uses this API to store and retrieve listener/mix data.

This API is [live at Amazon Web Services](http://jsbr.us-west-2.elasticbeanstalk.com).  Most areas are restricted to authenticated users only.

Restricted areas can be accessed using basic authentication:
* username: guest
* password: guest

This repository demonstrates:

* use of Spring Boot
* implementation of a REST HAL API
* hashing and authentication of passwords with BCrypt
* restricting access to authenticated users (only GET requests on root URL and mixes subdirectory are permitted without authentication)
* validation of input
* use of new programming language (Kotlin)
