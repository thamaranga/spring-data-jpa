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
