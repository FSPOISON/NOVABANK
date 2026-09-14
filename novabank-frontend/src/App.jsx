import { BrowserRouter, Routes, Route } from "react-router-dom";
import Sidebar from "./components/Sidebar";
import Clientes from "./pages/Clientes";
import Transferencia from "./pages/Transferencia";
import Historial from "./pages/Historial";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <div className="app-layout">
        <Sidebar />
        <div className="main-content">
          <Routes>
            <Route path="/" element={<Clientes />} />
            <Route path="/transferencia" element={<Transferencia />} />
            <Route path="/historial" element={<Historial />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}