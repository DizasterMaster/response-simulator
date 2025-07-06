# Response Simulator

`response-simulator` is a lightweight Java application that initializes HTTP endpoints based on a YAML configuration. Each endpoint is fully configurable and can respond with custom response bodies, headers, and HTTP status codes.

## Features

- Define endpoints dynamically using `application.yml`
- Configure:
    - Response body
    - HTTP headers
    - HTTP status code
- Simple to run and extend
- Ideal for mocking APIs during development and testing

## Getting Started

### Prerequisites

- Java 24
- Maven

## Configuration

Endpoints are defined in `response-simulator/app/src/main/resources/application.yml` under the `endpoints` key.

Each HTTP method (getMapping, postMapping, etc.) is a section that holds one or more named endpoint configurations. Example:

- getMapping: This is a section for all GET endpoints.
- example: This is a custom name for a specific GET endpoint. Any (appropriate) name can be used here.
- endpoint: Defines the URL path (/example/foo/*) that this endpoint listens to. Wildcards are allowed (e.g. *).
- responseStatus: HTTP status code to return, like 200, 404, etc.
- responseHeaders: Optional key-value pairs to include as headers in the response.
- responseBody: The response content to return — typically a JSON string.

You can define multiple endpoints under each HTTP method.
