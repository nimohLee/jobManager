const {
  createProxyMiddleware
} = require('http-proxy-middleware');

require('dotenv').config();

module.exports = function (app) {
  app.use(
    createProxyMiddleware('/api', {
      target: process.env.LOCAL_HOST,
      changeOrigin: true
    })
  );
  app.use(
    createProxyMiddleware('/openapi/v3', {
      target: 'https://openapi.map.naver.com',
      changeOrigin: true,
    }),
  );
};