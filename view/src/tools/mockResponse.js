/**
 * Returns a mock response from server
 * @param status {integer} - Status server code
 * @param statusText {string} - Server text
 * @param response {string} - Server return in strings
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
