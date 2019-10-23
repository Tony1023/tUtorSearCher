const mysql = require('mysql2');
const fs = require('fs');

var pool;

const setupConnection = () => {
  let raw = fs.readFileSync('db-credentials.json');
  let credentials = JSON.parse(raw);
  pool = mysql.createPool(credentials).promise();
}

setupConnection();

module.exports = {
  pool: pool,
}