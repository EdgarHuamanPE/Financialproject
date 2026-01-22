import { useState } from "react";
import {jwtDecode} from "jwt-decode";


import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "@/app/providers/AuthProvider";
import { useAdmin } from "@/app/providers/ModulesProvider";
import {DesktopHeader} from "@/shared/components/DesktopHeader";

export function AdminLayout(){
     
  const token = localStorage.getItem("token");
    let user = { email: "", role: "", avatarUrl: "" };

    if (token) {
      const decoded: any = jwtDecode(token);
      user = {
        email: decoded.email,
        role: decoded.role,
        avatarUrl: decoded.avatarUrl || "https://i.pravatar.cc/150?img=12",
      };
    }

    const { logout } = useAuth();
    const { modules }=useAdmin();
    const [open, setOpen] = useState(false);
    const [openUserMenu, setOpenUserMenu] = useState(false);

    
    return (
      <div className="flex h-full w-full bg-slate-100 overflow-hidden">
          {/* Overlay mobile */}
          {open && (
            <div
              className="fixed inset-0 bg-black/40 z-30 md:hidden"
              onClick={() => setOpen(false)}
            />
          )}

          {/* Sidebar */}
          <aside
            className={`
              fixed md:static inset-y-0 left-0 z-40
              w-64 bg-slate-900 text-white
              transform transition-transform duration-300
              ${open ? "translate-x-0" : "-translate-x-full"}
              md:translate-x-0
            `}
          >
            <div className="flex flex-col h-full p-4">
              <h2 className="text-xl font-bold mb-8 text-emerald-400">
                Financiera
              </h2>

              <nav className="flex-1 space-y-1">
                {modules.map(module => (
                  <NavLink
                    key={module.id}
                    to={module.route}
                    className={({ isActive }) =>
                      `block rounded-lg px-3 py-2 text-sm transition
                      ${isActive
                      ? "bg-slate-800 text-white"
                      : "text-slate-400 hover:text-white hover:bg-slate-800"}`
                    }
                  >
                    {module.title}
                  </NavLink>
                ))}
              </nav>

              <button
                onClick={logout}
                className="text-sm text-red-400 hover:text-red-300 mt-6"
              >
                Cerrar sesión
              </button>
            </div>
          </aside>

          {/* Contenido */}
          <div className="flex flex-col flex-1 h-full">
            {/* Header mobile */}
    <header className="md:hidden bg-white shadow px-4 py-3 flex items-center justify-between">
  
  {/* IZQUIERDA */}
  <div className="flex items-center gap-3">
    <button
      onClick={() => setOpen(true)}
      className="text-slate-700 text-xl font-bold"
    >
      ☰
    </button>

    <h1 className="font-semibold text-slate-800">
      Panel Financiero
    </h1>
  </div>

  {/* DERECHA: avatar + email */}
{user?.email && (
  <div className="relative">
    {/* Avatar + email */}
    <button
      onClick={() => setOpenUserMenu(prev => !prev)}
      className="flex flex-col items-end focus:outline-none"
    >
      <img
        src={user.avatarUrl || "https://i.pravatar.cc/150?img=12"}
        alt="avatar"
        className="w-8 h-8 rounded-full"
      />
      <span className="text-xs text-slate-800 mt-1 truncate max-w-30">
        {user.email.split("@")[0]}
      </span>
    </button>

    {/* Dropdown */}
    {openUserMenu && (
      <div className="absolute right-0 mt-2 w-36 bg-white rounded-lg shadow-lg border z-50">
        <button
          onClick={logout}
          className="w-full text-left px-4 py-2 text-sm text-shadow-black hover:bg-slate-100 rounded-lg"
        >
          Cerrar sesión
        </button>
      </div>
    )}
  </div>
)}

</header>
                
            <DesktopHeader user={user} logout={logout} />

            <main className="flex-1 h-full overflow-y-auto p-6">
              <Outlet />
            </main>
          </div>
      </div>
    );
}

