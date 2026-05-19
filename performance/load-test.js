import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 50 },
    { duration: '30s', target: 100 },
    { duration: '30s', target: 200 },
    { duration: '30s', target: 0 },
  ],

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
  },
};

const BASE_URL = 'http://localhost:8080/api/v1/tasks';

export default function () {

  // GET all tasks
  let getAllRes = http.get(BASE_URL);

  check(getAllRes, {
    'GET /tasks status is 200': (r) => r.status === 200,
  });

  sleep(1);

  // CREATE task
  const payload = JSON.stringify({
    title: `Task ${Date.now()}`,
    description: 'k6 performance test',
    completed: false,
  });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  let createRes = http.post(
    BASE_URL,
    payload,
    params
  );

  check(createRes, {
    'POST /tasks status is 201 or 200':
      (r) => r.status === 201 || r.status === 200,
  });

  sleep(1);

  // GET single task
  let getOneRes = http.get(`${BASE_URL}/1`);

  check(getOneRes, {
    'GET /tasks/1 status valid':
      (r) => r.status === 200 || r.status === 404,
  });

  sleep(1);
}
