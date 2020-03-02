[![Build Status](https://travis-ci.com/b-reynolds/postie-api.svg?branch=master)](https://travis-ci.com/b-reynolds/postie)
[![Docker Build Status](https://img.shields.io/docker/cloud/build/bornylsdeen/postie)](https://hub.docker.com/r/bornylsdeen/postie)

<img src="https://github.com/b-reynolds/postie-api/blob/master/logo.png" width="64px" align="left"/>

# Postie (API)
<br>
Postie is an in-development RESTful API for a plain text hosting service.

## Getting Started

### Installation/Usage

#### Docker Compose

The easiest way to run the Postie API is using the `docker-compose.yml` file within the root directory. 


```
docker-compose up
```

This command will initialize a PostgreSQL database with the Postie schema and start up the API. 

The default database credentials, API key and ports can be overridden using the environment variables within this file.

#### Docker

The `api` folder contains a `Dockerfile` that can be used to build a Docker image of the Postie API.

```
cd api/
docker build -t bornylsdeen/postie . 
```

Alternatively, you can pull down and use a pre-built image from the 
[Docker Registry](https://hub.docker.com/r/bornylsdeen/postie).

```
docker pull bornylsdeen/postie
```

You can then run the image pointing it towards a PostgreSQL database that has been setup with the Postie database schema (see `database/init.sql`).

```
docker run \
  --env POSTIE_PORT=7000 \
  --env POSTIE_API_KEY=postie \
  --env POSTIE_POSTGRES_HOST=jdbc:postgresql://localhost \
  --env POSTIE_POSTGRES_PORT=5432 \
  --env POSTIE_POSTGRES_USER=postie \
  --env POSTIE_POSTGRES_PASSWORD=postie \
  -d \
  bornylsdeen/postie
```

#### Java

If you want to run the Postie API outside of Docker you can create a fat JAR using the `shadowJar` gradle task.

*The Postie API has only been tested against Java 1.8*

```
cd api/
./gradlew shadowJar
```

You can then start up the API using the `run` command and configure it using the command-line options (environment 
variables will also work here but the command-line options take precedence).

```
java -jar /build/libs/api-0.0.1-all.jar run \
  --port 7000 \
  --api-key postie \
  --postgres-host jdbc:postgresql://localhost \
  --postgres-port 5432 \
  --postgres-user postie \
  --postgres-password postie
```
