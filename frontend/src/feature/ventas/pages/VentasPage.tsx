import { useState } from "react";
import { Plus } from "lucide-react";

import { AddVentaModal } from "@/feature/ventas/components/AddVentaModal";
import { useSales } from "@/feature/ventas/hooks/useVentas";



export function VentasPage() {
    const [openModal, setOpenModal] = useState(false);
    const {
        sales = [],
        setSales,
        loading,
        reload,
        page,
        limit,
        totalPages,
        setPage,
    } = useSales();

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                <div>
                    <h1 className="text-2xl font-bold text-slate-800">
                        Ventas de productos financieros
                    </h1>
                    <p className="text-sm text-slate-500">
                        Gestiona las originaciones y contrataciones
                    </p>
                </div>

                <button
                    onClick={() => setOpenModal(true)}
                    className="flex items-center gap-2 bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl shadow"
                >
                    <Plus size={18} />
                    Nueva venta
                </button>
            </div>

            {/* KPIs */}
            <section className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
                <KpiCard title="Total hoy" value="24" />
                <KpiCard title="Aprobadas" value="18" />
                <KpiCard title="Pendientes" value="4" />
                <KpiCard title="Rechazadas" value="2" />
            </section>

            {/* Filtros */}
            <section className="bg-white rounded-2xl shadow p-4 flex flex-col md:flex-row gap-3 md:items-center">
                <input
                    placeholder="Buscar cliente..."
                    className="border rounded-lg px-3 py-2 text-sm flex-1 outline-none focus:ring-2 focus:ring-emerald-500"
                />

                <select className="border rounded-lg px-3 py-2 text-sm">
                    <option>Todos los estados</option>
                    <option>Pendiente</option>
                    <option>Aprobado</option>
                    <option>Rechazado</option>
                    <option>Activo</option>
                </select>

                <input
                    type="date"
                    className="border rounded-lg px-3 py-2 text-sm"
                />
            </section>

            {/* Tabla */}
            <section className="bg-white rounded-2xl shadow overflow-hidden">
                <table className="w-full text-sm">
                    <thead className="bg-slate-50 text-slate-600">
                        <tr>
                            <Th>#</Th>
                            <Th>Cliente</Th>
                            <Th>Producto</Th>
                            <Th>Saldo</Th>
                            <Th>Canal</Th>
                            <Th>Estado</Th>
                            <Th>Fecha</Th>
                            <Th>Acciones</Th>
                        </tr>
                    </thead>


                    <tbody className="divide-y">
                        {loading ? (
                            <tr>
                                <td colSpan={8} className="text-center py-6 text-slate-400">
                                    Cargando ventas...
                                </td>
                            </tr>
                        ) : sales.length === 0 ? (
                            <tr>
                                <td colSpan={8} className="text-center py-6 text-slate-400">
                                    No hay ventas registradas
                                </td>
                            </tr>
                        ) : (
                            sales.map((sale, index) => (
                                <Row
                                    key={sale.id}
                                    index={index}
                                    client={sale.customerId ?? `Cliente ${sale.id}`}
                                    product={sale.productId ?? "Producto"}
                                    balance={sale.balance}
                                    channelOrigin={sale.channelOrigin}
                                    status={sale.status}
                                    date={new Date(sale.createdAt).toLocaleDateString()}
                                />
                            ))
                        )}
                    </tbody>


                </table>
                {/* ================= PAGINACIÓN ================= */}
                <div className="flex flex-col sm:flex-row items-center justify-between gap-3 p-4 border-t">
                    <span className="text-sm text-slate-600">
                        Página {page} de {totalPages}
                    </span>

                    <div className="flex gap-2">
                        <button
                            disabled={page === 1}
                            onClick={() => setPage((p) => p - 1)}
                            className="px-3 py-1 border rounded-lg disabled:opacity-40 hover:bg-slate-600 hover:text-white border-slate-400"
                        >
                            Atrás
                        </button>

                        <button
                            disabled={page === totalPages}
                            onClick={() => setPage((p) => p + 1)}
                            className="px-3 py-1 border rounded-lg disabled:opacity-40 hover:bg-slate-600 hover:text-white border-slate-400"
                        >
                            Siguiente
                        </button>
                    </div>
                </div>

            </section>
            <AddVentaModal
                open={openModal}
                onClose={() => setOpenModal(false)}
                onSuccess={() => reload()}
            />

        </div>
    );
}

/* ------------------- Subcomponentes ------------------- */

function KpiCard({ title, value }: { title: string; value: string }) {
    return (
        <div className="bg-white rounded-2xl shadow p-4">
            <p className="text-sm text-slate-500">{title}</p>
            <h3 className="text-2xl font-bold text-slate-800 mt-1">{value}</h3>
        </div>
    );
}

function Th({ children }: { children: React.ReactNode }) {
    return (
        <th className="text-left px-4 py-3 font-medium">{children}</th>
    );
}

function Row({
    index,
    client,
    product,
    balance,
    channelOrigin,
    status,
    date,
}: {
    index: number;
    client: number;
    product: number;
    balance: number;
    channelOrigin: string;
    status: string;
    date: string;
}) {
    const color =
        status === "APROBADO"
            ? "bg-emerald-100 text-emerald-700"
            : status === "PENDIENTE"
                ? "bg-yellow-100 text-yellow-700"
                : "bg-red-100 text-red-700";

    return (
        <tr className="hover:bg-slate-50">
            <td className="px-4 py-3">{index + 1}</td>
            <td className="px-4 py-3">{client}</td>
            <td className="px-4 py-3">{product}</td>
            <td className="px-4 py-3 font-medium">
                {balance.toLocaleString("es-PE", {
                    style: "currency",
                    currency: "PEN",
                })}
            </td>
            <td className="px-4 py-3">{channelOrigin}</td>
            <td className="px-4 py-3">
                <span className={`px-2 py-1 text-xs rounded-full font-medium ${color}`}>
                    {status}
                </span>
            </td>
            <td className="px-4 py-3">{date}</td>
            <td className="px-4 py-3 space-x-3">
                <button className="text-emerald-600 hover:underline">Ver</button>
                <button className="text-blue-600 hover:underline">Editar</button>
                <button className="text-red-600 hover:underline">Cancelar</button>
            </td>
        </tr>
    );
}


