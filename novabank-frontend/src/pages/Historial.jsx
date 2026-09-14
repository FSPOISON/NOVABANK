import { useState } from "react";
import api from "../api/api";

export default function Historial() {
  const [cuenta, setCuenta] = useState("");
  const [transacciones, setTransacciones] = useState([]);

  const buscar = async (e) => {
    e.preventDefault();
    const res = await api.get(`/transactions/account/${cuenta}`);
    setTransacciones(res.data);
  };

  return (
    <div>
      <h2>Histórico de transacciones</h2>
      <div className="card">
        <form onSubmit={buscar}>
          <input placeholder="Número de cuenta" value={cuenta}
                 onChange={(e) => setCuenta(e.target.value)} required />
          <button type="submit">Buscar</button>
        </form>
      </div>

      <div className="card">
        <table>
          <thead>
            <tr><th>ID</th><th>Origen</th><th>Destino</th><th>Monto</th><th>Fecha</th></tr>
          </thead>
          <tbody>
            {transacciones.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>{t.senderAccountNumber}</td>
                <td>{t.receiverAccountNumber}</td>
                <td>${t.amount.toLocaleString()}</td>
                <td>{new Date(t.timestamp).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}