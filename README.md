# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVzofClRAf6gnCTA8SPMBBicAK4Hlp8IJqTs3xgqcmm6dpHZUEiMAIEJ4oYvp4oEkSYCktuhi7jS+4MkyU7KVypg3qFd5LsKMBihKboynKZbvEqpgqsGGoAJJoCA0AouAGVGtAMA2QCsW8vF4muS6MA9n2QUgdU-oAHIQFGaLivYbowFGMZxoUoGYcgqYwOmwQAIwETmqh5vM0FFiW9Q+NMl7QEgABeKC7HRTY1QuApJr6zpdhu7pbq1IW1fy9QYkgABmMBUCaSDrm8xIwMMMCHnIKDPvE56Xtex37veiWPgGYM3W+KBnQ8sJ6Z5aAwBkqgAaYTltRJYGEZZy1jN8FFUfW0ENlp40lGAOF4TAFmZZ8pGk5e5PIazMCHQxnjeH4-heCg6AxHEiRCyL+m+FgomCqB9QNNIEb8RG7QRt0PRyaoCnDGTiHoKYY0fsZqMGbj4wUdtO15AUBYADx60h5SOSbzkI-UHn2NL3lCdLflqAFhsXYKw4PRwKDcMel6gy+2iBaHi6VMuRgR0yhjAzKT2vVaNqBbdxso6WUunhj-4IFgmC40j8uM7RQf4zTk24U4v3o+MaH0Yx-MBCi67+Ng4oavxaIwAA4kqGiy-Vkmj6rGv2Equvs-rhTUwXX71OPczI1+teW1Au025zMAO8vTsu4XcsXeFYBb2o3lonfqj+yS9cNcFVK3g9b2UC9ljAzHK8cdTAJ1OknYUjJmAZ20DALOMAc5gFUHnBGIcv70hgJAp+GJ45oLAUKG+Y8lSZ30MadyaJAooKRk5TeTIn6lyxuXHGrs8Y1BeMsBeOYCwNHGBwlAeVpAFhmuEYIgQQSbHiLqFAbpOR7G+MkUAappGQUWN8XhHUlQqIuDATomkjbJgmthGAzdW6M3YRPLhPClT8MEcI0RyxxGSKUSzEmIJ5EgEUURYmqilTqLmJo7RmlO582Yv4DgAB2NwTgUAt38BGYIcAuIADZ4ATkMHfGARQDFiXOvjBWrQOjz0XpteCK8sxqKVLo9qzlqGEO3ubMY+9D4jXto7dA5QQTlJZqhKmVccnv3qADdEd8MRwFSXfF+gdbqfzit-Gof8AEUXBqAqGtRIGw1jvIWBL14HWkQcg4Od0ToDLGUqDEvClm4JWcaNctSUAenzjpV29RRlHhQOkzG2NK7MOrlUthYxeHWPqEIkR3M34YSRqJemLc-ojDMXMQFMBgWBFBUEpiAtLARw8psUWSAEhgAxX2CA2KABSEBxS3JiG4tUmTaZX1yY0JozIZI9F4UvEpSEszYAQMADFUA4AQA8lANYALpAXEGGC6pTyKX1MadbZp9RT7svQNZblvL+WCoLGMAA6iwPKatvHwoEdBJFPTvl9LcgAKzJWgYZpLxTjJQISAOFCDnTPuuguZz1-7R0WcAiG8VwEEOgZsuBOcchILfoON1RyFTYC0EM055y-XLISutONgNbmZ22WGvZkb3yPMLvUO1NqlQfMYV8y+Pz6UjEqQ3CFWSoUmJraYVF3dBZQB5TivFXhO2IGDLAYA2AuWECPhkqefTJJKxVmrDWxgJWChqdIAAQjvYCzaK273qm5EA3A8DLuwXmj+MhcEDN3e6JdB7-UOlTSnSOhgTQICumsO+axgbHvkIe9sNS+14DLRXKu08wK1sTPo2mjaYU80wEAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
