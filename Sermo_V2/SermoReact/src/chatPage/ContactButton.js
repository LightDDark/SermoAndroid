import { Button, Modal, Form } from "react-bootstrap";
import { useState, useRef } from "react";
import users from "../dataBase/UserData";
import User from "../dataBase/User";

function ContactButton(props) {
  const [show, setShow] = useState(false);
  const [validated, setValidated] = useState(true);
  const contactName = useRef(null);
  const contactNick = useRef(null);
  const contactServer = useRef(null);
  const [contactList, setContactList] = props.contactList;
  const user = props.user;

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const handleAdd = async function (event) {
    event.preventDefault();
    const contactToAdd = new User(
      contactName.current.value,
      contactNick.current.value,
      contactServer.current.value
    );
    if (contactToAdd) {
      setValidated(true);
      user.addContact(contactToAdd);
      const newList = contactList.slice();
      newList.push(contactToAdd);
      setContactList(newList);
      setShow(false);
    } else {
      setValidated(false);
    }
  };

  return (
    <div>
      <Button variant="primary" onClick={handleShow}>
        Add contact
      </Button>

      <Modal
        show={show}
        onHide={handleClose}
        onExited={() => setValidated(true)}
      >
        <Form onSubmit={handleAdd}>
          <Modal.Header closeButton>
            <Modal.Title>Contact Details</Modal.Title>
          </Modal.Header>

          <Modal.Body>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>UserName</Form.Label>
              <Form.Control
                ref={contactName}
                type="username"
                placeholder="Enter User Id"
                isInvalid={!validated}
                required
              />
              <Form.Control.Feedback type="invalid">
                User not found!
              </Form.Control.Feedback>
              <Form.Text className="text-muted"></Form.Text>
              <Form.Label>Nickname</Form.Label>
              <Form.Control
                ref={contactNick}
                type="username"
                placeholder="Enter User Nick"
                isInvalid={!validated}
                required
              />
              <Form.Text className="text-muted"></Form.Text>
              <Form.Label>Server</Form.Label>
              <Form.Control
                ref={contactServer}
                type="username"
                placeholder="Enter User Server"
                isInvalid={!validated}
                required
              />
              <Form.Text className="text-muted"></Form.Text>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Cancel
            </Button>
            <Button variant="primary" type="submit">
              Add
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
}

export default ContactButton;
