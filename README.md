# jacobdemo

Demo project built by Bedrock.


## Getting Started
The following instructions should be used whenever you first start with a fresh download of the project.

- Use `git` to clone the repository on your local machine.  
- With _IntelliJ_, select "File" > "Open..." from the menu, navigate to your local clone directory, then click "Open."
- Open the *"Gradle projects"* tool window, then click "Refresh all Gradle projects."
- Refreshing projects may take a little while to complete your initial build, however once it is done, you're all set!

Your IDE is now setup.  You should be able to execute any available Run/Debug Configurations.


## Build
Builds are performed using [Gradle](https://gradle.org/getting-started-gradle/#toggle-id-1).  From Terminal, execute 
`./gradlew`; by default, this will build (imagine that!) all available modules, creating artifacts for deploying/running 
your applications.

As is the norm with _Gradle_, use `./gradlew tasks` to list all available tasks.  The following describes the more common
tasks:
- **build** - compiles and tests all modules
- **dockerBuildImage** - creates/updates the deployable _Docker_ image for all modules


_This project was originally generated using the [Bedrock Initializr](https://initializr.arcus.coop/)._
