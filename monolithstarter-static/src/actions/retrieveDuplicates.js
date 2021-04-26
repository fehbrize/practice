import axios from 'axios';

export async function getDuplicates() {
  return (await axios.get('/api/find-dupes')).data;
}

export async function getAdvancedDuplicates() {
  return (await axios.get('/api/find-dupes-advanced')).data;
}
