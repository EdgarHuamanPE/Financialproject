import type { CreateSaleDTO, SalePage, SaleResponse } from "@/feature/ventas/types/ventas.types";

const BASE_URL = "http://localhost:9082/api/customer";

export const createSale = async (data: CreateSaleDTO, token: string) => {
    const res = await fetch(BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(data),
    });

    if (!res.ok) throw new Error("Error al crear la venta");
    return res.json();
};


export async function getSales(
    token: string,
    page: number,
    limit: number
): Promise<SalePage> {
    const res = await fetch(
        `${BASE_URL}?page=${page - 1}&size=${limit}`, // Spring usa 0-based
        {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        }
    );

    if (!res.ok) throw new Error("Error cargando ventas");

    return res.json();
}



