const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const { pool } = require('./db-conf');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());

app.get('/', async (req, res) => {
  const [rows, field] = await pool.query("SELECT * FROM Users");
  res.status(200).send(JSON.stringify(rows));
});

app.post('/signup', (req, res) => {

});

app.listen(3100, () => { console.log('Listening on port 3100'); });