//const API_URL = import.meta.env.VITE_API_URL;
import type { ProductPage, updateProductForm } from "../type/product.types";

const API_URL = "http://localhost:9081/api/products";


export const getProducts = async (
  token: string,
  page: number,
  size: number
): Promise<ProductPage> => {

  const res = await fetch(
    `${API_URL}?page=${page}&size=${size}&sort=name,asc`,
    {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );

  if (!res.ok) throw new Error("Error cargando productos");

  return res.json();
};


export const addProduct = async (token: string, product: any) => {
  const res = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(product),
  });

  if (!res.ok) {
    const err = await res.json();
    throw new Error(err.message || "Error creando producto");
  }

  return res.json();
};

export const updateProduct = async (
  token: string,
  id: number,
  product: updateProductForm
) => {

  const res = await fetch(`${API_URL}/${id}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(product),
  });

  if (!res.ok) throw new Error("Error actualizando");

  return res.json();
};


export const deleteProduct = async (token: string, id: number) => {
  const res = await fetch(`${API_URL}/${id}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) throw new Error("Error eliminando el producto");

  return true; // simplemente confirmamos que se eliminó
};






