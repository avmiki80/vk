chcp 1251
java.exe -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=70 -XX:MaxGCPauseMillis=400 -Dserver.port=8080 -Dvk.app_id=51664415 -Dvk.client_secret=sqoR3C6Dn1mW5krjvxtB -jar vk-test-0.0.1-SNAPSHOT.jar fully.qualified.package.Application
pause
