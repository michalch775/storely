import './App.css';
import { HashRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Nav from "./components/Nav/Nav";

function App() {
  return (


    <Router>
      <Nav/>
      <Routes>
        <Route path="/" element={<Home/>} />
        <Route path="expenses" element={<Home />} />
      </Routes>
    </Router>
  );
}

export default App;
