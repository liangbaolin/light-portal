/**
 * Returns a mock response from server
 * @param {integer} status - Status server code
 * @param {string} statusText - Server text
 * @param {string} response - Server return in strings
 * @returns Response object
 */
export default (status, statusText, response) =>
  new window.Response(response, {
    status,
    statusText,
    headers: {
      'Content-type': 'application/json',
    },
  });
