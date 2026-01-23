import { useState } from "react";
import { useAuth } from "@/app/providers/AuthProvider";
import { useNavigate } from "react-router-dom";
import { loginUser } from "@/feature/auth/services/auth.service";


export function Login() {

  const navigate = useNavigate();
  const { login } = useAuth();
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState("");


  const formSubmit = async (e: React.FormEvent<HTMLFormElement>) => {

    e.preventDefault();
    setError("");
    try {
      const { token } = await loginUser({ email, password });
      login(token);
      navigate("/admin");
    } catch (err) {
      setError("Usuario o contraseña incorrectos");
    }
  }


  return (
    <div className="w-full max-w-4xl grid grid-cols-1 md:grid-cols-2 bg-white rounded-2xl shadow-2xl overflow-hidden">
      <div className="relative hidden md:block">
        <img
          src="https://images.unsplash.com/photo-1601597111158-2fceff292cdc"
          alt="Empresa financiera"
          className="absolute inset-0 h-full w-full object-cover"
        />
        <div className="absolute inset-0 bg-emerald-900/70"></div>

        <div className="relative z-10 h-full flex flex-col justify-end p-10 text-white">
          <h2 className="text-3xl font-semibold">Financiera CrediMass</h2>
          <p className="text-sm text-emerald-100 mt-2 max-w-sm">
            Soluciones financieras seguras para empresas y personas.
          </p>
        </div>
      </div>

      <div className="p-8 sm:p-10 flex flex-col justify-center">
        <h1 className="text-2xl font-semibold text-slate-900">Acceso al sistema</h1>
        <p className="text-sm text-slate-500 mb-8">Ingrese su correo y contraseña</p>

        <form className="space-y-6" onSubmit={formSubmit}>
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Email</label>
            <input
              type="text"
              placeholder="example@credimass.com"
              className="w-full rounded-lg border border-slate-300 px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-emerald-600"
              onChange={(e) => { setEmail(e.target.value) }}


            />
          </div>

          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Contraseña</label>
            <input
              type="password"
              placeholder="••••••••"
              className="w-full rounded-lg border border-slate-300 px-4 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-emerald-600"
              onChange={(e) => { setPassword(e.target.value) }}
            />
          </div>
          {error && <p className="text-red-500">{error}</p>}
          <button
            type="submit"
            className="cursor-pointer w-full bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-lg font-medium shadow-lg shadow-emerald-600/20"
          >
            Iniciar sesión
          </button>
        </form>

        <p className="mt-8 text-xs text-slate-500">
          Acceso seguro • Datos protegidos • Uso corporativo
        </p>
      </div>
    </div>
  );
}

