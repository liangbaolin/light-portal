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
export const globalRoutes = [
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
        exact: true,
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

/**
 * Dynamic Routes
 * @param {object} routes - Initialize with dummy routes
 * @param {object} payload - Routes from server
 * @returns {object} new object routes
 */
export const dynamicRoutes = (routes, payload) => {
  const pos = routes[0].routes.length - 1;
  const notFound = routes[0].routes.splice(pos, 1)[0];
  payload.contains.forEach(element => {
    routes[0].routes.push({
      route: element.route,
      component: Login,
    });
  });
  routes[0].routes.push(notFound);
  return routes;
};
