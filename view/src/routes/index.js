import Layout from '../components/Layout/Layout';
import Home from './home/Home';
import Login from './login/Login';

export const routes = [
  {
    component: Layout,
    routes: [
      {
        path: '/',
        exact: true,
        component: Home,
      },
      {
        path: '/login',
        component: Login,
      },
    ],
  },
];
