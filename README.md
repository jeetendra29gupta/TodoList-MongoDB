## TodoList-MongoDB

In this tutorial, we will learn to integrate MongoDB with a spring boot application and perform different CRUD operations through Spring Data MongoRepository. The different CRUD operations such as Create, Read, Update and Delete will be first implemented through MongoRepository 

#### Spring Boot MongoDB Maven Dependencies

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

#### Spring Boot MongoDB Properties Configuration

```
# MONGODB (MongoProperties)
spring.data.mongodb.uri=mongodb://localhost:27017/todoList
```