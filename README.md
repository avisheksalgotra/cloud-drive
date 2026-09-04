# CloudDrive

CloudDrive is a backend project for building a scalable cloud-based file management system.

The goal of this project is to understand how file storage platforms work and to gain practical experience with Spring Boot, REST APIs, databases, authentication, and scalable backend architecture.

## Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Lombok

## Current Implementation

Currently, the project contains the core database entities:

### User

Represents a user in the CloudDrive system.

Fields:

- ID
- Name
- Email
- Password Hash
- Created At
- Updated At

A user can own multiple folders and files.

### Folder

Represents a folder created by a user.

Fields:

- ID
- Name
- Owner
- Parent Folder
- Created At
- Updated At

Folders support a hierarchical structure through the parent folder relationship.

### StoredFile

Represents a file stored in the CloudDrive system.

Fields:

- ID
- File Name
- Storage Key
- Content Type
- File Size
- Owner
- Folder
- Created At
- Updated At

## Database Relationships

- One User can have multiple Folders.
- One User can have multiple Files.
- One Folder can contain multiple Files.
- A Folder can have a parent Folder.

## Project Structure

```text
src/main/java/com/avishek/clouddrive
│
├── user
│   └── entity
│       └── User.java
│
├── folder
│   └── entity
│       └── Folder.java
│
└── file
    └── entity
        └── StoredFile.java
