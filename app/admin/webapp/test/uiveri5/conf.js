'use strict'
const path = require('path');
const specs = path.relative(process.cwd(), path.join(__dirname, './tests/*.spec.js'));

exports.config = {
  profile: "integration",
  baseUrl: "http://localhost:4004/fiori.html#manage-books",
  specs: specs
};
