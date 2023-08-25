# Repro for issue with liquibase

Steps to reproduce:
1. Setup Java 17
2. Start a Docker Postgres container:
    ```sh
    docker run -it --rm -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=postgres postgres:15-alpine
    ```
3. Run `./gradlew update`
4. Gradle will fail with the following error:
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

Output of `./gradlew tasks`:
```
> Task :tasks

------------------------------------------------------------
Tasks runnable from root project 'demo'
------------------------------------------------------------

Application tasks
-----------------
bootRun - Runs this project as a Spring Boot application.
bootTestRun - Runs this project as a Spring Boot application using the test runtime classpath.

Build tasks
-----------
assemble - Assembles the outputs of this project.
bootBuildImage - Builds an OCI image of the application using the output of the bootJar task
bootJar - Assembles an executable jar archive containing the main classes and their dependencies.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the classes of the 'main' feature.
resolveMainClassName - Resolves the name of the application's main class.
resolveTestMainClassName - Resolves the name of the application's test main class.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the 'main' feature.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'demo'.
dependencies - Displays all dependencies declared in root project 'demo'.
dependencyInsight - Displays the insight into a specific dependency in root project 'demo'.
help - Displays a help message.
javaToolchains - Displays the detected java toolchains.
kotlinDslAccessorsReport - Prints the Kotlin code for accessing the currently available project extensions and conventions.
outgoingVariants - Displays the outgoing variants of root project 'demo'.
projects - Displays the sub-projects of root project 'demo'.
properties - Displays the properties of root project 'demo'.
resolvableConfigurations - Displays the configurations that can be resolved in root project 'demo'.
tasks - Displays the tasks runnable from root project 'demo'.

Liquibase tasks
---------------
calculateCheckSum - Calculates and prints a checksum for the <liquibaseCommandValue> changeset with the given id in the format filepath::id::author.
changelogSync - Mark all changes as executed in the database.
changelogSyncSQL - Writes SQL to mark all changes as executed in the database to STDOUT.
clearChecksums - Removes all saved checksums from the database. On next run checksums will be recomputed.  Useful for "MD5Sum Check Failed" errors.
dbDoc - Generates Javadoc-like documentation based on current database and change log to the <liquibaseCommandValue> directory.
diff - Writes description of differences to standard out.
diffChangeLog - Writes Change Log to update the database to the reference database to standard out
dropAll - Drops all database objects owned by the user. Note that functions, procedures and packages are not dropped (Liquibase limitation)
executeSql - Executes SQL in the database given in <liquibaseCommandValue> in this format: -PliquibaseCommandValue="--sql=select 1" or -PliquibaseCommandValue="--sqlFile=myfile.sql"
futureRollbackCountSQL - Writes SQL to roll back <liquibaseCommandValue> changes the database after the changes in the changelog have been applied.
futureRollbackSQL - Writes SQL to roll back the database to the current state after the changes in the changeslog have been applied.
generateChangelog - Writes Change Log groovy to copy the current state of the database to standard out.
listLocks - Lists who currently has locks on the database changelog.
markNextChangesetRan - Mark the next change set as executed in the database.
markNextChangesetRanSQL - Writes SQL to mark the next change set as executed in the database to STDOUT.
releaseLocks - Releases all locks on the database changelog.
rollback - Rolls back the database to the state it was in when the <liquibaseCommandValue> tag was applied.
rollbackCount - Rolls back the last <liquibaseCommandValue> change sets.
rollbackCountSQL - Writes SQL to roll back the last <liquibaseCommandValue> change sets to STDOUT.
rollbackSQL - Writes SQL to roll back the database to the state it was in when the <liquibaseCommandValue> tag was applied to STDOUT.
rollbackToDate - Rolls back the database to the state it was in at the <liquibaseCommandValue> date/time.
rollbackToDateSQL - Writes SQL to roll back the database to the state it was in at the <liquibaseCommandValue> date/time to STDOUT.
snapshot - Writes the current state of the database to standard out
snapshotReference - Writes the current state of the referenceUrl database to standard out
status - Outputs count (list if liquibaseCommandValue is "--verbose") of unrun change sets.
tag - Tags the current database state with <liquibaseCommandValue> for future rollback.
tagExists - Checks whether the tag given in <liquibaseCommandValue> is already existing.
unexpectedChangeSets - Outputs count (list if liquibaseCommandValue is "--verbose") of changesets run in the database that do not exist in the changelog.
update - Updates the database to the current version.
updateCount - Applies the next <liquibaseCommandValue> change sets.
updateCountSql - Writes SQL to apply the next <liquibaseCommandValue> change sets to STDOUT.
updateSQL - Writes SQL to update the database to the current version to STDOUT.
updateTestingRollback - Updates the database, then rolls back changes before updating again.
updateToTag - Updates the database to the changeSet with the <liquibaseCommandValue> tag
updateToTagSQL - Writes (to standard out) the SQL to update to the changeSet with the <liquibaseCommandValue> tag
validate - Checks the changelog for errors.

Verification tasks
------------------
check - Runs all checks.
test - Runs the test suite.

Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.

To see all tasks and more detail, run gradlew tasks --all

To see more detail about a task, run gradlew help --task <task>

BUILD SUCCESSFUL in 611ms
1 actionable task: 1 executed
```
