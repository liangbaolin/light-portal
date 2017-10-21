import fetch from 'node-fetch';

export default function fetchMenuByHost() {
  return new Promise(resolve => {
    fetch('http://localhost:8082/api/json', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        host: 'lightapi.net',
        service: 'menu',
        menu: 'action',
        action: 'getMenuByHost',
        version: '0.1.0',
        data: {
          host: 'example.org',
        },
      }),
    })
      .then(res => res.json())
      .then(json => resolve(json));
  });
}
