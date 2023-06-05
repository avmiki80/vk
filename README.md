Создать приложение в **Vk**.  
Задать состояние "**Приложение включено и видимо всем**".  
Включить "**Open API**".  
Задать Адрес сайта: **http://127.0.0.1**,  
Задать Базовый домен: **localhost, 127.0.0.1**,  
Задать Доверенный redirect URL: **http://localhost:8080/get-code, http://127.0.0.1:8080/get-code, http://127.0.0.1/get-code, http://localhost/get-code**  
  
При запустке с профилем prod в файле **application-prod.properties** задать свои настройки соединения с БД.  
spring.datasource.username=**username**  
spring.datasource.password=**password**  
spring.datasource.url=**jdbc:postgresql://DB-host:5432/postgres?currentSchema=currentSchema**  
  
Запуск ПО через коммандную строку. Задать параметры **vk.app_id** = *{ID приложения}*,  **vk.client_secret** = *{CLIENT SECRET приложения}*, **vk.redirect_url** = *{ссылка получения authorization code}*.  
  
java.exe -XX:+UseG1GC -XX:InitiatingHeapOccupancyPercent=70 -XX:MaxGCPauseMillis=400 -Dserver.port=8080 -Dvk.app_id=ID_приложения -Dvk.client_secret=CLIENT_SECRET -jar vk-test-0.0.1-SNAPSHOT.jar fully.qualified.package.Application  
  
ссылка на swagger **http://127.0.0.1:8080/swagger-ui/index.html#/**  
