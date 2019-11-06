# Server module

In order to let server connect to our MySQL database successfully, please create a `db-credentials.json` file in this directory with the following content:
```json
{ 
  "host": <server address without port number>,
  "user": <our user name>,
  "password": <our password>,
  "database": "tUtorSearCher",
  "connectionLimit": <an int, suggested 1~4>
}
```
