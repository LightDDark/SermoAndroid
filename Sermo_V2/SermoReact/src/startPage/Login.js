import { useRef, useState } from "react";
import users from "../dataBase/UserData";
import "./startPage.css";
import { Link, Navigate } from "react-router-dom";

function Login(props) {
  const setLoggedUser = props.setLoggedUser;
  const pass = useRef(null);
  const user = useRef(null);
  const [nav, setNav] = useState("");
  const loginUser = async function () {
    if (pass.current.value === "" || user.current.value === "") {
      console.log("Error, cannot have empty fields.");
    } else {
      setLoggedUser(await users.login(user.current.value, pass.current.value));
      setNav(<Navigate to="chat" />);
    }
  };
  return (
    <div className="Forms">
      {nav}
      <label className={"Welcome"}>Welcome to Sermo</label>
      <div className="User">
        <label htmlFor="formGroupExampleInput" className="form-label">
          Username
          <input
            type="username"
            ref={user}
            className="form-control"
            id="formGroupExampleInput"
            placeholder="username"
          ></input>
        </label>
      </div>
      <div className="Pass">
        <label htmlFor="formGroupExampleInput2" className="form-label">
          Password
          <input
            type="password"
            ref={pass}
            className="form-control"
            id="formGroupExampleInput2"
            placeholder="password"
          ></input>
        </label>
      </div>
      <div className="LogButton">
        <button onClick={loginUser}>Login</button>
      </div>
      <div className="Unreg">
        <label>
          Not registered yet? sign up <Link to="/register">here</Link>
        </label>
      </div>
    </div>
  );
}

export default Login;
