import './App.scss';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Nav from "./components/Nav";
import Login from "./pages/Login";
import Delays from "./pages/Delays";
import Items from "./pages/Items";
import Shortages from "./pages/Shortages";
import Users from "./pages/Users";


import Settings from "./pages/Settings";


function App() {

  return (
    <Router>
    <Nav/>
      <Routes>
        <Route path="delay" element={<Delays/>} />
        <Route path="item" element={<Items/>} />
        <Route path="shortage" element={<Shortages/>} />
        <Route path="user" element={<Users/>} />

        <Route path="login" element={<Login/>} />

        <Route path="settings" element={<Settings/>} />

        <Route path="/" element={<Home/>} />
      </Routes>
    </Router>
  );
}

export default App;
