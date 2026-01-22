import { createBrowserRouter } from "react-router-dom";
import {ProtectedRoute} from "./ProtectedRoute";

import {Login} from "@/feature/auth/pages/Login";
import {AdminLayout} from "@/layout/AdminLayout";
import {AdminBienvenida} from "@/feature/bienbenida/AdminBienvenida";


export const router = createBrowserRouter([
  {
    path: "/",
    element: <Login />,
  },
  {
    element: <ProtectedRoute />,
    children: [
      {
        path: "/admin",
        element: <AdminLayout />,
        
        children: [
          { index: true, element: <AdminBienvenida/> },
          { path: "productos", element: <h1>Productos</h1> },
          { path: "clientes", element: <h1>Clientes</h1> },
          { path: "ventas", element: <h1>Ventas</h1> },
          { path: "productos/reportes", element: <h1>reportes poductos</h1> },
          { path: "clientes/reportes", element: <h1>reportes clientes</h1> },
          { path: "ventas/reportes", element: <h1>reportes ventas</h1> },
        ],
      },
    ],
  },
]);