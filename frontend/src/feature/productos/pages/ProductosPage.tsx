import { useState , useEffect} from "react";
import { getProducts } from "../service/services";
import { useAuth } from "@/app/providers/AuthProvider";

interface Product {
  id: number;
  code: string;
  name: string;
  type: string;
  interestRate: number;
  status: "ACTIVE" | "INACTIVE" | "SUSPENDED" | "CLOSED";
}

export function ProductsPage() {

    const { token } = useAuth();    
    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        const loadProducts = async () => {
            if (!token) return;
            try {
                const data = await getProducts(token);
                setProducts(data); // Llenamos la tabla
            } catch (err) {
                console.error(err);
            }
        };
        if (token) loadProducts();
    }, [token]);

  

  const [search, setSearch] = useState("");
  const [open, setOpen] = useState(false);

  const filtered = products.filter(p =>
    p.name.toLowerCase().includes(search.toLowerCase())
  );

  const toggleStatus = (id: number) => {
    setProducts(prev =>
      prev.map(p =>
        p.id === id
          ? { ...p, status: p.status === "ACTIVE" ? "INACTIVE" : "ACTIVE" }
          : p
      )
    );
  };

  return (
    <div className="space-y-6">

      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <h1 className="text-2xl font-bold text-slate-800">Productos</h1>

        <button
          onClick={() => setOpen(true)}
          className="bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl"
        >
          + Nuevo
        </button>
      </div>

      {/* Filtros */}
      <div className="bg-white p-4 rounded-2xl shadow flex flex-col md:flex-row gap-3">
        <input
          placeholder="Buscar..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="border rounded-xl px-3 py-2 w-full md:w-64"
        />

        <select className="border rounded-xl px-3 py-2 w-full md:w-48">
          <option>Todos los tipos</option>
          <option>Ahorro</option>
          <option>Crédito</option>
        </select>

        <select className="border rounded-xl px-3 py-2 w-full md:w-48">
          <option>Todos los estados</option>
          <option>Activo</option>
          <option>Inactivo</option>
        </select>
      </div>

      {/* Tabla */}
      <div className="bg-white rounded-2xl shadow overflow-x-auto">
        <table className="min-w-full text-sm">
          <thead className="bg-slate-50 text-slate-600">
            <tr>
              <th className="p-4 text-left">ID</th>
              <th className="p-4 text-left">Código</th>
              <th className="p-4 text-left">Nombre</th>
              <th className="p-4 text-left">Tipo</th>
              <th className="p-4 text-left">Tasa</th>
              <th className="p-4 text-left">Estado</th>
              <th className="p-4 text-right">Acciones</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((p) => (
              <tr key={p.id} className="border-t hover:bg-slate-50">
                <td className="p-4">{p.id}</td>
                <td className="p-4">{p.code}</td>
                <td className="p-4 font-medium">{p.name}</td>
                <td className="p-4">{p.type}</td>
                <td className="p-4">{p.interestRate}%</td>

                <td className="p-4">
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-semibold
                      ${p.status === "ACTIVE"
                        ? "bg-green-100 text-green-700"
                        : "bg-red-100 text-red-700"}`}
                  >
                    {p.status}
                  </span>
                </td>

                <td className="p-4 text-right space-x-3">
                  <button className="text-blue-600 hover:underline">
                    Editar
                  </button>

                  <button
                    onClick={() => toggleStatus(p.id)}
                    className="text-yellow-600 hover:underline"
                  >
                    Toggle
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Modal simple */}
      {open && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4">
          <div className="bg-white rounded-2xl w-full max-w-md p-6 space-y-4">
            <h2 className="font-semibold text-lg">Nuevo Producto</h2>

            <input className="w-full border rounded-xl px-3 py-2" placeholder="Código" />
            <input className="w-full border rounded-xl px-3 py-2" placeholder="Nombre" />

            <div className="flex justify-end gap-2">
              <button
                onClick={() => setOpen(false)}
                className="border px-4 py-2 rounded-xl"
              >
                Cancelar
              </button>

              <button className="bg-emerald-600 text-white px-4 py-2 rounded-xl">
                Guardar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
