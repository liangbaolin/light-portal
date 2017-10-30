/* eslint-disable import/prefer-default-export */

import { MENU_SERVICE_SUCCESS, MENU_SERVICE_ERROR } from '../constants';
import fetchMenuByHost from '../api/menu';

/**
 * Action Creator for successful menu service
 * @param {object} payload - Routes
 */
export const menuFetchSuccess = payload => ({
  type: MENU_SERVICE_SUCCESS,
  payload,
});

/**
 * Action Creator for error menu service
 * @param {*} payload - Error object
 */
export const menuFetchError = payload => ({
  type: MENU_SERVICE_ERROR,
  payload,
});

/**
 * Dispatches an action to retrieve menu structure for Menu Service
 */
export const getMenuService = () => async dispatch => {
  try {
    dispatch(menuFetchSuccess(await fetchMenuByHost()));
  } catch (e) {
    dispatch(menuFetchError(e));
    console.error(e);
  }
};
