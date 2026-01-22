// src/feature/productos/services/productService.ts
//const API_URL = import.meta.env.VITE_API_URL;

const API_URL = "http://localhost:9081/api/products";

export const getProducts = async (token: string) => {
  const res = await fetch(API_URL, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  if (!res.ok) throw new Error("Error cargando productos");
  return res.json(); // Devuelve un array de productos
};
