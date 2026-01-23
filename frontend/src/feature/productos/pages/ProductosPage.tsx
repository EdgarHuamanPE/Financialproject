import { useEffect, useState } from "react";
import { useAuth } from "@/app/providers/AuthProvider";
import { getProducts, addProduct, updateProduct, deleteProduct } from "@/feature/productos/service/services";
import type { Product, ProductForm, updateProductForm } from "@/feature/productos/type/product.types";



export function ProductsPage() {
  const { token } = useAuth();
  const [products, setProducts] = useState<Product[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [search, setSearch] = useState("");
  const [open, setOpen] = useState(false);
  const size = 5;

  const [form, setForm] = useState({
    code: "",
    name: "",
    type: "",
    category: "",
    currency: "",
    interestRate: "",
    description: "",
  });

  const fields: (keyof ProductForm)[] = [
    "code",
    "name",
    "type",
    "category",
    "currency",
    "interestRate",
  ];

  const [editingProduct, setEditingProduct] = useState<Product | null>(null);

  /* ================= LOAD ================= */
  useEffect(() => {
    const load = async () => {
      if (!token) return;
      const data = await getProducts(token, page, size);
      setProducts(data.content);
      setTotalPages(data.totalPages);
    };
    load();
  }, [token, page]);

  const filtered = products.filter((p) =>
    p.name.toLowerCase().includes(search.toLowerCase())
  );

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setForm(prev => ({ ...prev, [e.target.placeholder]: e.target.value }));
  };

  const handleAddProduct = async () => {
    if (!token) return;
    setEditingProduct(null);
    // Mapear placeholders a la estructura que espera el backend
    const newProduct = {
      code: form.code,
      name: form.name,
      type: form.type,
      category: form.category,
      currency: form.currency,
      interestRate: parseFloat(form.interestRate) || 0,
      description: form.description
    };

    try {
      const added = await addProduct(token, newProduct);
      setProducts(prev => [added, ...prev]); // Agregar al inicio de la tabla
      setOpen(false); // Cerrar modal
      setForm({
        code: "",
        name: "",
        type: "",
        category: "",
        currency: "",
        interestRate: "",
        description: "",
      });
    } catch (err: any) {
      console.error(err.message);
    }
  };



  // Al presionar editar
  const handleUpdateProduct = async () => {
    if (!token || !editingProduct) return;

    // Solo enviamos los campos que permite updateProductForm
    const updatedData: updateProductForm = {
      name: form.name,
      type: form.type,
      category: form.category,
      currency: form.currency,
      interestRate: form.interestRate,
      description: form.description,
    };

    try {
      const saved = await updateProduct(token, editingProduct.id, updatedData);
      setProducts(prev =>
        prev.map(p => (p.id === saved.id ? saved : p))
      );
      setOpen(false);
      setEditingProduct(null);
      setForm({
        code: "",
        name: "",
        type: "",
        category: "",
        currency: "",
        interestRate: "",
        description: "",
      });
    } catch (err: any) {
      console.error(err.message);
    }
  };

  const handleDeleteProduct = async (productId: number) => {
    if (!token) return;

    // Confirmación opcional
    if (!confirm("¿Estás seguro de eliminar este producto?")) return;

    try {
      await deleteProduct(token, productId);

      // Remover producto de la lista local
      setProducts(prev => prev.filter(p => p.id !== productId));
    } catch (err: any) {
      console.error(err.message);
    }
  };


  return (
    <div className="w-full max-w-full space-y-6">

      {/* ================= HEADER ================= */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        <h1 className="text-xl sm:text-2xl font-bold text-slate-800">
          Productos
        </h1>
        <button
          onClick={() => {
            setForm({
              code: "",
              name: "",
              type: "",
              category: "",
              currency: "",
              interestRate: "",
              description: "",
            }); // Limpiar campos
            setOpen(true); // Abrir modal
          }}
          className="w-full sm:w-auto bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl"
        >
          + Nuevo
        </button>
      </div>

      {/* ================= FILTROS ================= */}
      <div className="bg-white p-4 rounded-2xl shadow">
        <div className="flex flex-col md:flex-row gap-3">
          <input
            placeholder="Buscar producto..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            className="border rounded-xl px-3 py-2 w-full md:flex-1 min-w-0"
          />

        </div>
      </div>

      {/* ================= TABLA ================= */}
      <div className="bg-white rounded-2xl shadow overflow-x-auto">
        <table className="w-full min-w-225 text-sm">
          <thead className="bg-slate-50 text-slate-600">
            <tr>
              <th className="p-4 text-left">#</th>
              <th className="p-4 text-left">Código</th>
              <th className="p-4 text-left">Nombre</th>
              <th className="p-4 text-left">Tipo</th>
              <th className="p-4 text-left">Tasa</th>
              <th className="p-4 text-left">Estado</th>
              <th className="p-4 text-right">Acciones</th>
            </tr>
          </thead>

          <tbody>
            {filtered.map((p, index) => (
              <tr key={p.id} className="border-t hover:bg-slate-50">
                <td className="p-4">{page * size + index + 1}</td>
                <td className="p-4">{p.code}</td>
                <td className="p-4 font-medium whitespace-nowrap">{p.name}</td>
                <td className="p-4">{p.type}</td>
                <td className="p-4">{p.interestRate}%</td>
                <td className="p-4">
                  <span
                    className={`px-3 py-1 rounded-full text-xs font-semibold ${p.status === "ACTIVE"
                        ? "bg-green-100 text-green-700"
                        : "bg-red-100 text-red-700"
                      }`}
                  >
                    {p.status}
                  </span>
                </td>
                <td className="p-4 text-right space-x-3 whitespace-nowrap">
                  <button
                    className="text-blue-600 hover:underline"
                    onClick={() => {
                      setEditingProduct(p); // p = el producto de la fila
                      setForm({
                        code: p.code,
                        name: p.name,
                        type: p.type,
                        category: p.category,
                        currency: p.currency,
                        interestRate: p.interestRate.toString(),
                        description: p.description,
                      });
                      setOpen(true);
                    }}
                  >Editar</button>

                  <button
                    className="text-yellow-600 hover:underline"
                    onClick={() => handleDeleteProduct(p.id)}
                  >Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* ================= PAGINACIÓN ================= */}
      <div className="flex flex-col sm:flex-row items-center justify-between gap-3">
        <span className="text-sm text-slate-600">
          Página {page + 1} de {totalPages}
        </span>
        <div className="flex gap-2">
          <button
            disabled={page === 0}
            onClick={() => setPage((p) => p - 1)}
            className="px-3 py-1 border rounded-lg disabled:opacity-40 hover:bg-slate-600 hover:text-white hover:cursor-pointer border-slate-400"
          >
            Atras
          </button>
          <button
            disabled={page + 1 === totalPages}
            onClick={() => setPage((p) => p + 1)}
            className="px-3 py-1 border border-slate-400 rounded-lg disabled:opacity-40 hover:bg-slate-600 hover:text-white hover:cursor-pointer"
          >
            Siguiente
          </button>
        </div>
      </div>

      {/* ================= MODAL ================= */}
      {open && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center p-4">
          <div className="bg-white rounded-2xl w-full max-w-md p-6 space-y-4">
            <h2 className="font-semibold text-lg">
              {editingProduct ? "Editar Producto" : "Nuevo Producto"}
            </h2>

            {fields.map((label) => (
              <input
                key={label}
                placeholder={label}
                value={form[label] || ""}
                onChange={handleChange}
                className="w-full border rounded-xl px-3 py-2"
              />
            ))}

            <textarea
              placeholder="description"
              value={form["description"] || ""}
              onChange={handleChange}
              className="w-full border rounded-xl px-3 py-2"
            />

            <div className="flex justify-end gap-2">
              <button
                onClick={() => setOpen(false)}
                className="border px-4 py-2 rounded-xl"
              >
                Cancelar
              </button>
              <button
                onClick={editingProduct ? handleUpdateProduct : handleAddProduct}
                className="bg-emerald-600 text-white px-4 py-2 rounded-xl hover:bg-emerald-700 transition-colors"
              >
                {editingProduct ? "Actualizar" : "Guardar"}
              </button>

            </div>
          </div>
        </div>
      )}
    </div>
  );
}
