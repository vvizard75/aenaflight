# ETL application

## Default setting
- Name of source table - aenaflight_2017_01
- Username for database in environment variable PG_USER 
- Password for database in environment variable PG_PASSWORD
- Default string fo rconnect to database  - localhost:5432/aenaflight

OR

You can use settings file "application.properties"

```
spring.datasource.url=jdbc:postgresql://localhost:5432/aenaflight
spring.datasource.username=${PG_USER}
spring.datasource.password=${PG_PASSWORD}
aenaflight.tablename=aenaflight_2017_01
```

If you interrupted process, application start from stopped location.