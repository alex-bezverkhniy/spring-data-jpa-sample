
# Task requests

See more details here - https://alex-bezverkhniy.github.io/Spring-Data-JPA-Sample-app/

## Create Task
*Task data:*
```json
{
  "title": "Sample Task",
  "description": "Just simple task",
  "isComplete": false
}
```

```ssh
curl -X POST \
-H 'Content-Type: application/json' \
-d '{
      "title": "Sample Task",
      "description": "Just simple task",
      "isComplete": false
    }' \
'http://localhost:8080/api/tasks/' \
| python -m json.tool
```

## Read Task
```ssh
curl -X GET 'http://localhost:8080/api/tasks/1' | python -m json.tool
```

## Update Task
*Task data:*
```json
{
  "title": "Sample Task",
  "description": "Just simple task",
  "isComplete": false
}
```

```ssh
curl -X PUT \
-H 'Content-Type: application/json' \
-d '{
      "title": "Sample Task",
      "description": "Just simple task",
      "isComplete": false
    }' \
'http://localhost:8080/api/tasks/1' \
| python -m json.tool
```
