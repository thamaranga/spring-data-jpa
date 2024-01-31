# spring-data-jpa

This is a sample  spring boot rest api which demonstrates 
how to use  spring boot data jpa.

This application is used mysql db called jpa.

In this application jpa auditing has also been implemented.
With JPA auditing created_by, created_time , last_modified_by , last_modified_time 
columns are included into product table by the jpa.
Further with adding spring-data-envers into this application, seperate two new
tables called product_aud and revinfo are created by jpa.
This product_aud table  maintains the  history of product table.

For eccryption and decryption  jasypt  library has been used.
Master password for encryption/decryption has been provided as VM option.
(If you are using Intelij Edit Configuration -> Modify options -> Add VM options -> -Djasypt.encryptor.password=Value of the master password)

#For encrypting a value run below command in intelij terminal (Here root123 is the value we need to encrypt. Pizza@123 is our encryption password)
mvn jasypt:encrypt-value "-Djasypt.encryptor.password=Pizza@123" "-Djasypt.plugin.value=root123"

#For decrypting a value run below command in intelij terminal
mvn jasypt:decrypt-value "-Djasypt.encryptor.password=Pizza@123" "-Djasypt.plugin.value=ENC(2b+9eryMe3kMX0PG1HfJqXlTw4zVEKk35BCIH7e7Y+qficNAqNjzpo2kaIEMv3pj)"

For implementing spring boot default cache there are several ways.

Spring boot internally uses a concurrent hashmap for
implementing default cache.

If we stop/restart the application then cached data will be cleared.
