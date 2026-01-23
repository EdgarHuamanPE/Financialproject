import { useEffect, useState } from "react";
import { getSales } from "@/feature/ventas/services/ventas.service";
import type { SaleResponse } from "@/feature/ventas/types/ventas.types";
import { useAuth } from "@/app/providers/AuthProvider";

export function useSales(initialPage = 1, limit = 10) {
    const [sales, setSales] = useState<SaleResponse[]>([]);
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(initialPage);
    const [loading, setLoading] = useState(true);

    const { token } = useAuth();

    const loadSales = async (p = page) => {
        if (!token) return;

        try {
            setLoading(true);

            const res = await getSales(token, p, limit);

            setSales(res.content);          // ⭐ CLAVE
            setTotalPages(res.totalPages);  // ⭐ CLAVE
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadSales(page);
    }, [page, token]);

    return {
        sales,
        setSales,
        loading,
        page,
        limit,
        totalPages,
        setPage,
        reload: () => loadSales(page),
    };
}

