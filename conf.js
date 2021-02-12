'use strict'
const path = require('path');
// const specs = path.relative(process.cwd(), path.join(__dirname, './tests/*.spec.js'));
const specs = path.relative(process.cwd(), path.join(__dirname, '/app/admin/webapp/test/uiveri5/tests/*.spec.js'));

exports.config = {
  profile: "integration",
  baseUrl: "https://cf-dts-integration-de-opensap-capire-bookshop-approuter.cfapps.eu10.hana.ondemand.com/app/fiori.html#manage-books",
  specs: specs
};
