import { useState } from "react";
import { Send, CheckCircle2, AlertCircle } from "lucide-react";
import api from "../api/api";

export default function Transferencia() {
  const [form, setForm] = useState({ senderAccountNumber: "", receiverAccountNumber: "", amount: "" });
  const [mensaje, setMensaje] = useState(null);
  const [cargando, setCargando] = useState(false);

  const enviar = async (e) => {
    e.preventDefault();
    setMensaje(null);
    setCargando(true);
    try {
      await api.post("/transactions", { ...form, amount: Number(form.amount) });
      setMensaje({ tipo: "ok", texto: "Transferencia realizada con éxito." });
      setForm({ senderAccountNumber: "", receiverAccountNumber: "", amount: "" });
    } catch (err) {
      setMensaje({ tipo: "error", texto: err.response?.data?.message || "Error al transferir." });
    } finally {
      setCargando(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Transferencia entre cuentas</h2>
        <p>Mueve dinero entre dos cuentas de NovaBank de forma segura.</p>
      </div>
      <div className="card" style={{ maxWidth: 460 }}>
        <form onSubmit={enviar}>
          <div className="field" style={{ marginBottom: 14 }}>
            <label>Cuenta origen</label>
            <input value={form.senderAccountNumber} onChange={(e) => setForm({ ...form, senderAccountNumber: e.target.value })} required />
          </div>
          <div className="field" style={{ marginBottom: 14 }}>
            <label>Cuenta destino</label>
            <input value={form.receiverAccountNumber} onChange={(e) => setForm({ ...form, receiverAccountNumber: e.target.value })} required />
          </div>
          <div className="field" style={{ marginBottom: 20 }}>
            <label>Monto (COP)</label>
            <input type="number" value={form.amount} onChange={(e) => setForm({ ...form, amount: e.target.value })} required />
          </div>
          <button type="submit" disabled={cargando}>
            <Send size={15} style={{ verticalAlign: "-2px", marginRight: 6 }} />
            {cargando ? "Enviando..." : "Transferir"}
          </button>
          {mensaje && (
            <div className={`alert ${mensaje.tipo === "ok" ? "alert-ok" : "alert-error"}`}>
              {mensaje.tipo === "ok" ? <CheckCircle2 size={16} /> : <AlertCircle size={16} />}
              {mensaje.texto}
            </div>
          )}
        </form>
      </div>
    </div>
  );
}