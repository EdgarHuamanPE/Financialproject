import { useState } from "react";
import { createSale } from "@/feature/ventas/services/ventas.service";
import type { CreateSaleDTO } from "@/feature/ventas/types/ventas.types";
import { initialForm } from "@/feature/ventas/types/ventas.types";

interface Props {
    open: boolean;
    onClose: () => void;
    onSuccess?: (newSale?: any) => void;
}

export function AddVentaModal({ open, onClose, onSuccess }: Props) {
    const token = localStorage.getItem("token") || "";

    const [form, setForm] = useState<CreateSaleDTO>(initialForm);

    const [loading, setLoading] = useState(false);

    if (!open) return null;

    const handleChange = (key: keyof CreateSaleDTO, value: any) => {
        setForm((prev) => ({ ...prev, [key]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            setLoading(true);
            await createSale(form, token);

            setForm(initialForm); // ⭐ limpiar form
            onSuccess?.();
            onClose();
        } catch (err) {
            alert("Error creando venta");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
            <div className="bg-white w-full max-w-lg rounded-2xl shadow-xl p-6 space-y-5">
                {/* Header */}
                <div className="flex justify-between items-center">
                    <h2 className="text-lg font-semibold">Nueva venta</h2>
                    <button onClick={onClose} className="text-slate-500">✕</button>
                </div>

                {/* Form */}
                <form onSubmit={handleSubmit} className="space-y-4">

                    <Input
                        label="Customer ID"
                        type="number"
                        value={form.customerId}
                        onChange={(v) => handleChange("customerId", Number(v))}
                    />

                    <Input
                        label="Product ID"
                        type="number"
                        value={form.productId}
                        onChange={(v) => handleChange("productId", Number(v))}
                    />

                    <Input
                        label="Account Number"
                        value={form.accountNumber}
                        onChange={(v) => handleChange("accountNumber", v)}
                    />

                    <Input
                        label="Contract Number"
                        value={form.contractNumber}
                        onChange={(v) => handleChange("contractNumber", v)}
                    />

                    <Input
                        label="Balance"
                        type="number"
                        value={form.balance}
                        onChange={(v) => handleChange("balance", Number(v))}
                    />

                    <select
                        className="w-full border rounded-lg px-3 py-2 text-sm"
                        value={form.channelOrigin}
                        onChange={(e) => handleChange("channelOrigin", e.target.value)}
                    >
                        <option value="WEB">WEB</option>
                        <option value="APP_MOVIL">APP_MOVIL</option>
                        <option value="AGENCIA">AGENCIA</option>
                        <option value="CALL_CENTER">CALL_CENTER</option>
                    </select>

                    {/* Actions */}
                    <div className="flex justify-end gap-3 pt-3">
                        <button
                            type="button"
                            onClick={onClose}
                            className="px-4 py-2 border rounded-lg"
                        >
                            Cancelar
                        </button>

                        <button
                            type="submit"
                            disabled={loading}
                            className="bg-emerald-600 text-white px-4 py-2 rounded-lg"
                        >
                            {loading ? "Guardando..." : "Crear venta"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

/* -------- input reusable -------- */

function Input({
    label,
    value,
    onChange,
    type = "text",
}: {
    label: string;
    value: any;
    onChange: (v: string) => void;
    type?: string;
}) {
    return (
        <div className="flex flex-col gap-1">
            <label className="text-xs text-slate-500">{label}</label>
            <input
                type={type}
                value={value}
                onChange={(e) => onChange(e.target.value)}
                className="border rounded-lg px-3 py-2 text-sm"
                required
            />
        </div>
    );
}
