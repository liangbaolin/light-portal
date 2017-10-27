/* eslint-disable import/prefer-default-export */

import { MENU_SERVICE_SUCCESS, MENU_SERVICE_ERROR } from '../constants';
import fetchMenuByHost from '../api/menu';

export const menuFetchSuccess = payload => ({
  type: MENU_SERVICE_SUCCESS,
  payload,
});

export const menuFetchError = payload => ({
  type: MENU_SERVICE_ERROR,
  payload,
});

export const getMenuService = () => async dispatch => {
  try {
    dispatch(menuFetchSuccess(await fetchMenuByHost()));
  } catch (e) {
    dispatch(menuFetchError(e));
    console.error(e);
  }
};
