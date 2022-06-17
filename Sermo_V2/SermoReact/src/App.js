import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import MainChat from "./chatPage/MainChat";
import ProtectedRoute from "./startPage/ProtectedRoute";
import { Route, Routes } from "react-router-dom";
import { useState } from "react";
import Login from "./startPage/Login";
import Register from "./startPage/Register";

function App() {
  const [loggedUser, setLoggedUser] = useState(null);
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login setLoggedUser={setLoggedUser} />} />
        <Route path="register" element={<Register />} />
        <Route
          path="chat"
          element={
            <ProtectedRoute user={loggedUser}>
              <MainChat user={loggedUser} />
            </ProtectedRoute>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
