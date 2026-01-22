import { createContext, useContext } from "react";

const modules: AdminModule[] = [
      {
    id: "admin",
    title: "Administrador",
    route: "/admin",
    showInDashboard: false,
  },
  
  {
    id: "products",
    title: "Productos",
    description: "Gestión y reportes de productos",
    route: "/admin/productos",
    reportUrl:"/admin/productos/reportes",
    showInDashboard: true,
  },
  {
    id: "clients",
    title: "Clientes",
    description: "Análisis de clientes",
    route: "/admin/clientes",
    reportUrl:"/admin/clientes/reportes",
    showInDashboard: true,
  },
  {
    id: "sales",
    title: "Ventas",
    description: "Ingresos y métricas",
    route: "/admin/ventas",
    reportUrl:"/admin/Ventas/reportes",
    showInDashboard: true,
  },
];

export interface AdminModule {
  id: string;
  title: string;
  description?: string;
  route: string;
  showInDashboard: boolean;
  reportUrl?:string;
}

interface AdminContextType {
  modules: AdminModule[];
}

const AdminContext = createContext<AdminContextType | null>(null);

export function AdminProvider({ children }: { children: React.ReactNode }) {
  return (
    <AdminContext.Provider value={{ modules }}>
      {children}
    </AdminContext.Provider>
  );
}

export function useAdmin() {
  const context = useContext(AdminContext);
  if (!context) {
    throw new Error("useAdmin debe usarse dentro de AdminProvider");
  }
  return context;
}
