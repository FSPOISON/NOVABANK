import { NavLink } from "react-router-dom";
import { Users, ArrowLeftRight, History, Landmark } from "lucide-react";

export default function Sidebar() {
  return (
    <div className="sidebar">
      <div className="sidebar-brand">
        <div className="logo-dot"><Landmark size={18} /></div>
        <h1>NovaBank</h1>
      </div>
      <NavLink to="/" end><Users /> Clientes</NavLink>
      <NavLink to="/transferencia"><ArrowLeftRight /> Transferencia</NavLink>
      <NavLink to="/historial"><History /> Historial</NavLink>
    </div>
  );
}