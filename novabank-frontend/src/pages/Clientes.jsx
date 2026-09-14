import { useEffect, useState } from "react";
import { UserPlus, Users as UsersIcon } from "lucide-react";
import api from "../api/api";

const formatCOP = (valor) =>
  new Intl.NumberFormat("es-CO", { style: "currency", currency: "COP", maximumFractionDigits: 0 }).format(valor);

const iniciales = (nombre, apellido) =>
  `${nombre?.[0] ?? ""}${apellido?.[0] ?? ""}`.toUpperCase();

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [nuevo, setNuevo] = useState({ firstName: "", lastName: "", accountNumber: "", balance: "" });
  const [cargando, setCargando] = useState(false);
  const [error, setError] = useState(null);

  const cargarClientes = () => {
    api.get("/customers").then((res) => setClientes(res.data)).catch(() => setError("No se pudo conectar con el servidor."));
  };

  useEffect(() => { cargarClientes(); }, []);

  const crearCliente = async (e) => {
    e.preventDefault();
    setError(null);
    setCargando(true);
    try {
      await api.post("/customers", { ...nuevo, balance: Number(nuevo.balance) });
      setNuevo({ firstName: "", lastName: "", accountNumber: "", balance: "" });
      cargarClientes();
    } catch (err) {
      setError(err.response?.data?.message || "No se pudo crear el cliente. Revisa la consola del navegador.");
    } finally {
      setCargando(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Clientes</h2>
        <p>Consulta y registra los clientes de NovaBank.</p>
      </div>

      <div className="card">
        <h3><UserPlus size={17} style={{ verticalAlign: "-3px", marginRight: 6 }} />Crear nuevo cliente</h3>
        <form onSubmit={crearCliente}>
          <div className="form-grid">
            <div className="field">
              <label>Nombre</label>
              <input value={nuevo.firstName} onChange={(e) => setNuevo({ ...nuevo, firstName: e.target.value })} required />
            </div>
            <div className="field">
              <label>Apellido</label>
              <input value={nuevo.lastName} onChange={(e) => setNuevo({ ...nuevo, lastName: e.target.value })} required />
            </div>
            <div className="field">
              <label>Número de cuenta</label>
              <input value={nuevo.accountNumber} onChange={(e) => setNuevo({ ...nuevo, accountNumber: e.target.value })} required />
            </div>
            <div className="field">
              <label>Saldo inicial (COP)</label>
              <input type="number" value={nuevo.balance} onChange={(e) => setNuevo({ ...nuevo, balance: e.target.value })} required />
            </div>
          </div>
          <div style={{ marginTop: 20 }}>
            <button type="submit" disabled={cargando}>{cargando ? "Creando..." : "Crear cliente"}</button>
          </div>
          {error && <div className="alert alert-error">{error}</div>}
        </form>
      </div>

      <div className="card">
        <h3>Listado de clientes</h3>
        {clientes.length === 0 ? (
          <div className="empty-state">
            <UsersIcon size={32} />
            <p>Aún no hay clientes registrados.</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr><th>Cliente</th><th>Cuenta</th><th>Saldo</th></tr>
            </thead>
            <tbody>
              {clientes.map((c) => (
                <tr key={c.id}>
                  <td>
                    <div className="avatar-name">
                      <div className="avatar-circle">{iniciales(c.firstName, c.lastName)}</div>
                      {c.firstName} {c.lastName}
                    </div>
                  </td>
                  <td>{c.accountNumber}</td>
                  <td className="balance-cell">{formatCOP(c.balance)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}