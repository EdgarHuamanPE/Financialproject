const API_URL_ACTIVACION = "http://localhost:9082/api/customer/dashboard/mes/activacion";
const API_URL_COUNT_CUSTOMER = "http://localhost:9080/api/customers/dashboard/customer/count";
const API_URL_COUNT_PRODUCT = "http://localhost:9081/api/products/dashboard/product/count";

export async function countActivacionMes(token: string) {

    const response = await fetch(API_URL_ACTIVACION, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error("Credenciales inválidas");
    }

    return response.json() as Promise<{ count: number }>;
}

export async function countCustomerActive(token: string) {

    const response = await fetch(API_URL_COUNT_CUSTOMER, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error("Credenciales inválidas");
    }

    return response.json() as Promise<{ count: number }>;
}

export async function countProductActive(token: string) {

    const response = await fetch(API_URL_COUNT_PRODUCT, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`,
        },
    });

    if (!response.ok) {
        throw new Error("Credenciales inválidas");
    }

    return response.json() as Promise<{ count: number }>;
}