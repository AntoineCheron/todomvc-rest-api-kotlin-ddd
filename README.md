# TODOMVC

> This is a fork of my own repository [todomvc](https://github.com/AntoineCheron/todomvc). Here I only keep the Kotlin implementation of the REST API and apply a Domain Driven Design Architecture.

Here I propose a REST API to enable the design of new versions of TODOMVC that use a backend.

In my opinion, it could be a nice addition to TODOMVC in order to help developers learn how to use a REST API within a MVC frontend.
I consider this as an interesting point as the design of frontends using a REST API became an industry standard.

## Project Structure

The project structure respect the Kotlin standards, with Gradle. In addition, is follows a [Domain Driven Design architecture](https://altkomsoftware.pl/en/blog/create-better-code-using-domain-driven-design/).

## Prerequisites

You need to have:

- A Java Development Kit (JDK) version 8 or later

## How to install?

Open a terminal, go to the folder where you want to save this project and then type:

```sh
git clone https://github.com/AntoineCheron/todomvc-rest-api-kotlin-ddd.git
cd todomvc-rest-api-kotlin-ddd
```

## How to run?

```bash
./gradlew bootRun
```

## How to use?

The default port of the server is 8080. This can be changed in the same manner as any Spring Boot project. Check Google for more information on that.

You can use the [OpenAPI documentation of the REST API](/openapi.yml) to discover how to use it. Its base URL is `http://localhost:8080/rest`.

## Contributing

Simply [open an issue](https://github.com/AntoineCheron/todomvc-rest-api-kotlin-ddd/issues/new/choose) to get in touch and propose your contribution.

## License

The [license of this project](./LICENSE.txt) is MIT. It lets people do almost anything they want with this project, like making and distributing closed source versions.
