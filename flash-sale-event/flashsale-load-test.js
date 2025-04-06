import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 1000,
  duration: '5s',
  thresholds: {
    // 95% 요청은 500ms 이내에 끝나야 함
    http_req_duration: ['p(95)<500'],

    // 총 5000건 이상 요청이 있어야 함
    http_reqs: ['count>999'],
  },
};

export default function () {
  const eventId = 1;
  const url = `http://localhost:8080/api/flashsale/${eventId}/order`;

  const res = http.post(url);

  check(res, {
    'status is 200 or 400': (r) => r.status === 200 || r.status === 400,
  });
}
