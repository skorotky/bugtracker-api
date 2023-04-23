# About
This repository contains the code for the backend of the full-stack Bugtracker project. <br>
The frontend code can be found in the following [repository](https://github.com/skorotky/bugtracker-client).

# Tech Stack
* **Languages**: Java 19
* **Frameworks:** Spring Boot
* **Build tools:** Maven
* **Servers:** Tomcat
* **Databases:** PostgreSQL

# Dependencies
<ul type="square">
  <li>Spring Web</li>
  <li>Spring Data JPA</li>
  <li>Spring Security</li>
  <li>Spring HATEOAS</li>
  <li>PostgreSQL Driver</li>
  <li>Spring Boot Dev Tools</li>
  <li>Spring Validation</li>
  <li>Lombok</li>
  <li>Jackson Databind</li>
  <li>SpringDoc OpenAPI WebMVC UI</li>
</ul>

# API Features
* REST API
* Authentication (Basic)
* Authorization 

# Project Functionalities
- [ ] User registration 
- [x] User authentication
- [ ] Endpoint access control based on user roles/privileges 
- [x] Users can create, update, and delete projects  
- [ ] Project owners can configure their project visibility (private vs. public)
- [x] Project owners can add to or remove collaborators from their project
- [x] Inside the project, users can create, update, and delete bug reports
- [x] Users can leave comments under bug reports and update/delete them
- [x] Collection resource pagination

# Installation 
1. ```git clone https://github.com/skorotky/bugtracker-api.git```  
2. ```cd bugtracker-api ```  
3. In the ```application.properties``` file, put your own PostgreSQL credentials.
4. Run the Spring Boot application.
5. Open http://localhost:8080/ in the browser.
6. Navigate to ```/api/**``` routes to see the API.
7. To authenticate, enter ```user``` for username and ```password``` for password in the pop-up window.

# API Documentation & Testing
You can acess the OpenAPI Specification of the Bugtracker API in JSON format at http://localhost:8080/v3/api-docs. <br>
You can also download the documentation as a YAML file from http://localhost:8080/v3/api-docs.yaml.

You can explore and play with the Bugtracker API in an interactive environment generated by Swagger UI at http://localhost:8080/swagger-ui.html.