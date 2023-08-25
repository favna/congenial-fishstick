# Repro for issue with liquibase

Steps to reproduce:
1. Setup Java 17
2. Run `./gradlew update`
3. Gradle will fail with the following error:
```
> Task :update FAILED
liquibase-plugin: Running the 'main' activity...

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':update'.
> Could not find method getMain() for arguments [] on task ':update' of type org.liquibase.gradle.LiquibaseTask.

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

BUILD FAILED in 1s
1 actionable task: 1 executed
```
