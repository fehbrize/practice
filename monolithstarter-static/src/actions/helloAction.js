import axios from 'axios';

export async function getHelloMessage() {
  console.debug(axios.get('/api/hello'));
  return (await axios.get('/api/hello')).data;
}
