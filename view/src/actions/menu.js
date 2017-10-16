/* eslint-disable import/prefer-default-export */

/**
 * https://github.com/kriasoft/react-starter-kit/blob/feature/react-intl/src/actions/intl.js
 */
import { MENU_SERVICE_SUCCESS } from '../constants';

export function getMenuService(payload) {
  return {
    type: MENU_SERVICE_SUCCESS,
    payload,
  };
}
