import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Shorten URL (random code)
export const shortenUrl = async (originalUrl) => {
  try {
    const response = await api.post('/shorten', { originalUrl });
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};

// Shorten URL with custom alias (optional)
export const shortenUrlWithAlias = async (originalUrl, customAlias) => {
  try {
    const response = await api.post('/shorten/custom', {
      originalUrl,
      customAlias: customAlias || null,
    });
    return response.data;
  } catch (error) {
    throw error.response?.data || error.message;
  }
};