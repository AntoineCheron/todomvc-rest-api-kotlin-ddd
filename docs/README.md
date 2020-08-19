# Main Project Documentation

## Automated Code Quality Checks

Tools:
* [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle)

### Set up

#### SonarQube

[Installation instructions](https://sajidrahman.github.io/2018-06-06-install-sonar-on-macosx/).

Then start it (`sonar start`) and go to the [administration page](http://localhost:9000).
Login (default credentials are admin/admin) and create a new project named `todomvc-ddd-kotlin`.

You can now run sonar analysis locally.

### How to use

* Lint code: `./gradlew ktlintCheck`
* Format code: `./gradlew ktlintFormat`
* Code analysis with Detekt: `./gradlew detekt`
* Code analysis with Sonar: `./scripts/run-sonar-scanner.sh`

## Others

For another example of a Domain-Driven Designed project, see [Hamza Bahassou's work](https://github.com/B-hamza/spring-aggregate-example).
