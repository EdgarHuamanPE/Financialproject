export interface CreateSaleDTO {
    customerId: number;
    productId: number;
    accountNumber: string;
    balance: number;
    contractNumber: string;
    channelOrigin: string;
}

export const initialForm: CreateSaleDTO = {
    customerId: 0,
    productId: 0,
    accountNumber: "",
    balance: 0,
    contractNumber: "",
    channelOrigin: "WEB",
};

export interface SaleResponse {
    id: number;
    customerId: number;
    productId: number;
    balance: number;
    channelOrigin: string;
    status: string;
    createdAt: string;
}

export interface SalePage {
    content: SaleResponse[];
    totalPages: number;
    totalElements: number;
    number: number;
    size: number;
}

