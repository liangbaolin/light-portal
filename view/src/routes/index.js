/* eslint no-unused-vars: 0 */
import React from 'react';
import { connect } from 'react-redux';

import Home from './home/Home';
import Login from './login/Login';
import Admin from './admin/Admin';
import User from './user/User';
import NotFound from './notFound/NotFound';

/**
 * Global Routes
 */

export default [
  {
    component: Home,
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
      {
        path: '/admin',
        component: Admin,
      },
      {
        path: '/user',
        component: User,
      },
      {
        component: NotFound,
      },
    ],
  },
];
