# Time is Money

To debug this project simply run:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
Then connect to port 5005 with remote in debug mode.