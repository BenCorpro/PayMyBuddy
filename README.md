# PayMyBuddy

Easy Money Transfering App

##Table of contents
1. [General Info](#general-info)
2. [Deliverable: UML Diagram](#uml-diagram)
3. [Deliverable: Data Physical Model](#data-physical-model)
4. [SQL Scripts](#dbs-creation-scripts)
5. [Technologies](#technologies)
6. [Installation](#installation)

##General Info

Project prototype for PayMyBuddy
Project 6 of Java Application Developper Course

##Uml Diagram

![UML_Diagram](./deliverables/pmb_diagrammeUML.png)


##Data physical model

![Physical_Model](./deliverables/pmb_modelephysique.png)


##Dbs creation scripts

[DB Scripts](https://github.com/BenCorpro/PayMyBuddy/resources)


##Technologies

* [Java](https://www.oracle.com/java/technologies/downloads/): Version 17
* [MySQL](https://dev.mysql.com/downloads/mysql/): Version 8.0.28
* [Spring](https://start.spring.io/): Version 2.6.4
* Spring Web, Spring Data JPA, MySQL Connector, Spring Security and Thymeleaf


##Installation


1. Clone the repository
<br/>
```
git clone https://github.com/BenCorpro/PayMyBuddy
```
2. Get into the project folder
<br/>
```
cd ../path/to/the/file
```
3. Package the application
<br/>
```
mvnw package
```
4. Copy the application.properties file in a "config" folder, and copy the jar next to it (for database id and password)
<br/>
5. Execute the jar
<br/>
```
java -jar paymybuddy-0.0.1-SNAPSHOT.jar
```
6. Go to [http://localhost:8080](http://localhost:8080) on your browser
<br/>

Side information: To use the application database needs to be created and filled with the provided SQL scripts

