import { Link } from "react-router-dom";

export function AdminBienvenida() {
  return (
    <section className="w-full max-w-6xl mx-auto px-4 py-8">
      {/* Header */}
      <header className="mb-10">
        <h1 className="text-2xl md:text-3xl font-bold text-slate-900">
          Bienvenido al Sistema de Ventas Financieros
        </h1>
        <p className="mt-2 text-slate-500 max-w-2xl">
          Desde este panel podrás monitorear ventas, gestionar productos
          financieros y analizar el comportamiento de tus clientes.
        </p>
      </header>

      {/* KPIs */}
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 mb-10">
        <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
          <h3 className="text-sm font-medium text-slate-500">
            Ventas del mes
          </h3>
          <p className="mt-2 text-2xl font-bold text-emerald-600">
            S/ 45,800
          </p>
          <span className="mt-1 block text-xs text-slate-400">
            Actualizado hoy
          </span>
        </div>

        <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
          <h3 className="text-sm font-medium text-slate-500">
            Clientes activos
          </h3>
          <p className="mt-2 text-2xl font-bold text-slate-900">
            1,248
          </p>
          <span className="mt-1 block text-xs text-slate-400">
            Últimos 30 días
          </span>
        </div>

        <div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
          <h3 className="text-sm font-medium text-slate-500">
            Productos financieros
          </h3>
          <p className="mt-2 text-2xl font-bold text-slate-900">
            18
          </p>
          <span className="mt-1 block text-xs text-slate-400">
            Activos en el sistema
          </span>
        </div>
      </div>

      {/* Accesos rápidos */}
      <section>
        <h2 className="text-lg font-semibold text-slate-900 mb-4">
          Accesos rápidos
        </h2>

        <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {/* Productos */}
          <Link
            to="/admin/productos/reportes"
            className="block rounded-xl bg-slate-900 text-white p-6
            focus:ring-slate-900 transition"
                       hover:bg-slate-800 focus:outline-none focus:ring-2
          >
            <h3 className="font-semibold text-lg">
              Gestión de Productos
            </h3>
            <p className="mt-2 text-sm text-slate-300">
              Consulta y administra los productos financieros disponibles.
            </p>
          </Link>

          {/* Clientes */}
          <Link
            to="/admin/clientes/reportes"
            className="block rounded-xl bg-slate-900 text-white p-6
                       hover:bg-slate-800 focus:outline-none focus:ring-2
                       focus:ring-slate-900 transition"
          >
            <h3 className="font-semibold text-lg">
              Reportes de Clientes
            </h3>
            <p className="mt-2 text-sm text-slate-300">
              Analiza el comportamiento y segmentación de clientes.
            </p>
          </Link>

          {/* Ventas */}
          <Link
            to="/admin/Ventas/reportes"
            className="block rounded-xl bg-slate-900 text-white p-6
                       hover:bg-slate-800 focus:outline-none focus:ring-2
                       focus:ring-slate-900 transition"
          >
            <h3 className="font-semibold text-lg ">
              Reportes de Ventas
            </h3>
            <p className="mt-2 text-sm text-slate-300">
              Visualiza métricas, ingresos y tendencias de ventas.
            </p>
          </Link>
        </div>

      </section>
    </section>
  );
}
