chcp 1251
java.exe -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=70 -XX:MaxGCPauseMillis=400 -Dserver.port=8080 -Dvk.app_id=some_app_id -Dvk.client_secret=some_client_secret -jar vk-test-0.0.1-SNAPSHOT.jar fully.qualified.package.Application
pause
