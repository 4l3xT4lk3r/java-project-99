# Менеджер задач

[![Actions Status](https://github.com/4l3xT4lk3r/java-project-99/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/4l3xT4lk3r/java-project-99/actions)
[![project99-build](https://github.com/4l3xT4lk3r/java-project-99/actions/workflows/project99-build.yml/badge.svg)](https://github.com/4l3xT4lk3r/java-project-99/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/d777071b64e48050da00/maintainability)](https://codeclimate.com/github/4l3xT4lk3r/java-project-99/maintainability)

[Demo on Render](https://taskmanager-4oxu.onrender.com/)

## Работа c пользователями

### Получение списка пользователей

Method: GET  
Endpoint: /api/users  
Example:

### Получение пользователя по идентификатору

Method: GET
Endpoint: /api/users/{id}
Example: `curl <URL servrice>/api/users/1`

### Cоздание пользователя

Method: POST  
Endpoint: /api/users  
Example: `curl -X POST -H 'Content-Type: application/json' -d '{"firstName":"Ellen","lastName":"Ripley","email":"e.ripley@weyland.com", "password":"In space, no one can hear you scream."}' <URL servrice>/api/users`

### Oбновление пользователя

Method: PUT  
Endpoint: /api/users/{id}  
Examples:  

- `curl -X PUT -H 'Content-Type: application/json' -d '{"firstName":"John","lastName":"Wayne","email":"duke@hollywood.com", "password":"Is that you, John Wayne? Is this me?"}' <URL servrice>/api/users/1`
- `curl -X PUT -H 'Content-Type: application/json' -d '{"firstName":"Peter"}' <URL servrice>/api/users/1`


### Удаление пользователя

Method: DELETE  
Endpoint: /api/users/{id}  
Example: `curl -X DELETE 127.0.0.1:8080/api/users/1`
