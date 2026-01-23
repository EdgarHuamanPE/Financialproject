import { useState } from "react";

export function DesktopHeader({ user, logout }: { user: any; logout: () => void }) {
  const [openDropdown, setOpenDropdown] = useState(false);

  return (
    <header className="hidden md:flex justify-end items-center bg-white shadow px-6 py-3 relative">
      <div className="flex items-center space-x-4 relative">
        {/* Email y Rol */}
        <div className="flex flex-col text-right">
          <span className="text-sm font-medium text-slate-800">{user.email}</span>
          <span className="text-xs text-slate-500">{user.role}</span>
        </div>

        {/* Avatar */}
        <div className="relative">
          <button
            onClick={() => setOpenDropdown(!openDropdown)}
            className="w-10 h-10 rounded-full overflow-hidden border-2 border-slate-300 focus:outline-none"
          >
            <img
              src={user.avatarUrl || "/default-avatar.png"}
              alt="avatar"
              className="w-full h-full object-cover"
            />
          </button>

          {/* Dropdown */}
          {openDropdown && (
            <div className="absolute right-0 mt-2 w-40 bg-white border rounded shadow-lg z-50">
              <button
                onClick={logout}
                className="w-full text-left px-4 py-2 text-sm text-shadow-black hover:bg-red-50"
              >
                Cerrar sesión
              </button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
