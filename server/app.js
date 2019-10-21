const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());

app.post('/signup', (req, res) => {
  
});

app.listen(3100, () => { console.log('Listening on port 3100'); });