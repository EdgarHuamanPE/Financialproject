export interface Product {
  id: number;
  code: string;
  name: string;
  type: string;
  category: string;
  currency: string;
  description: string;
  interestRate: number;
  status: "ACTIVE" | "INACTIVE" | "SUSPENDED" | "CLOSED";
}

export interface ProductPage {
  content: Product[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
}

export type ProductForm = {
  code: string;
  name: string;
  type: string;
  category: string;
  currency: string;
  interestRate: string;
  description: string;
};

export type updateProductForm = {
  name: string;
  type: string;
  category: string;
  currency: string;
  interestRate: string;
  description: string;
};