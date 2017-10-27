const body = {
  host: 'lightapi.net',
  service: 'menu',
  action: 'getMenuByHost',
  version: '0.1.0',
  data: { host: 'example.org' },
};

export default async () => {
  const res = await fetch('/api/json', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  });
  return res.json();
};
