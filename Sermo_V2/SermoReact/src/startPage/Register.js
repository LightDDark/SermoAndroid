import { useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";
import users from "../dataBase/UserData";
import "./startPage.css";
import { Link, Navigate } from "react-router-dom";

function Register() {
  const usersDB = users;
  const pass = useRef(null);
  const pass2 = useRef(null);
  const user = useRef(null);
  const nick = useRef(null);
  const [nav, setNav] = useState("");
  const [validateConfirm, setValidateConfirm] = useState(true);
  const [validatePass, setValidatePass] = useState(true);
  const [validateName, setValidateName] = useState(true);

  const passConfirm = function () {
    setValidateConfirm(pass.current.value === pass2.current.value);
  };
  const nameVal = async function () {
    const u = await usersDB.getUser(user.current.value);
    setValidateName(u.getName() === undefined);
  };
  const passVal = function () {
    setValidatePass(pass.current.value.length >= 4);
    passConfirm();
  };
  const registerUser = function (event) {
    event.preventDefault();
    if (validateConfirm && validateName && validatePass) {
      usersDB.addUser(
        user.current.value,
        pass.current.value,
        nick.current.value
      );
      // setRegistered(!registered);
      setNav(<Navigate to="/" />);
    }
  };
  return (
    <Form onSubmit={registerUser}>
      {nav}
      <div className="Forms">
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>UserName</Form.Label>
          <Form.Control
            ref={user}
            type="username"
            placeholder="Enter User Id"
            isInvalid={!validateName}
            onChange={nameVal}
            required
          />
          <Form.Control.Feedback type="invalid">
            User already exist!
          </Form.Control.Feedback>
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicEmail2">
          <Form.Label>Password</Form.Label>
          <Form.Control
            ref={pass}
            type="password"
            placeholder="Enter password"
            isInvalid={!validatePass}
            onChange={passVal}
            required
          />
          <Form.Control.Feedback type="invalid">
            password have consist at least 4 characters!
          </Form.Control.Feedback>
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicEmail3">
          <Form.Label>Confirm Password</Form.Label>
          <Form.Control
            ref={pass2}
            type="password"
            placeholder="Confirm password"
            isInvalid={!validateConfirm}
            onChange={passConfirm}
            required
          />
          <Form.Control.Feedback type="invalid">
            password does not match!
          </Form.Control.Feedback>
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicEmail4">
          <Form.Label>Nickame</Form.Label>
          <Form.Control
            ref={nick}
            type="username"
            placeholder="Enter Nickname"
            required={true}
          />
          <Form.Control.Feedback type="invalid">
            Nickame is Invalid!
          </Form.Control.Feedback>
          <Form.Text className="text-muted"></Form.Text>
        </Form.Group>
        <Button variant="primary" type="submit">
          Register
        </Button>
        <div className="Unreg">
          <label>
            Already registered? <Link to="/">Click here</Link> to login
          </label>
        </div>
      </div>
    </Form>
  );
}

export default Register;
