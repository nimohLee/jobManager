const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
  app.use(
      createProxyMiddleware('/api', {
          target: 'http://localhost:8000',
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