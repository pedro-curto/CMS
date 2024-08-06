# CMS: Candidates Management System

## Dependencies

- Require download
  - [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
  - [Maven](https://maven.apache.org/download.cgi)
  - [Node 14+](https://nodejs.org/en/) ([Node Version Manager](https://github.com/nvm-sh/nvm) recommended)
  - [Docker](https://www.docker.com/)
- No download required
  - [Spring-boot](https://spring.io/)
  - [Vue.js](https://vuejs.org/)


## Run Locally

Clone the project

```bash
git clone git@gitlab.rnl.tecnico.ulisboa.pt:<REPO>
```

Go to the project directory

```bash
cd dei-cms-pedro-curto/
```

### Database

To run the database with Docker (recommended), run the following command:

```bash
docker compose up
```

Alternatively, you can create services that will be run in the background:

```bash
docker compose up -d
```

To stop the database, run the following command:

```bash
docker compose down
```

### Backend

Create a copy of the `application.properties` file.

```bash
cp ./backend/src/main/resources/application-local.properties.example ./backend/src/main/resources/application-local.properties
```

To build and run the backend, execute the following commands:

```bash
cd backend
mvn clean spring-boot:run
```

## Frontend

Create a copy of the `example.env` file named `.env`.

```bash
cp ./frontend/example.env ./frontend/.env
```

Install the dependencies:

```bash
cd ./frontend
npm i
```

To run the frontend, run the following command:

```bash
npm run dev
```

Access http://localhost:5173

## Access the Database

In order to access the database, you can use the generic command:

```bash
psql -h localhost -p <PORT> -U <USER> -d <DB_NAME>
```

For the current configuration, you can use the specific command:

```bash
psql -h localhost -p 7654 -U postgres dmsdb
```

Check [PostgreSQL documentation](https://www.postgresql.org/docs/current/app-psql.html) for useful commands.

### Getting Familiarized with the Project

You can check the documentation folder to see the domain model and a file 
called `feature_guide.md` with the project's features and implementation decisions.

I added a .sql file to easily populate the database with some data, which makes certain features easier to test. 
You can run it with the following command, after starting the backend:

```bash
psql -h localhost -U postgres -p 7654 dmsdb -f backend/src/main/resources/sqlData/bigtest.sql
```
