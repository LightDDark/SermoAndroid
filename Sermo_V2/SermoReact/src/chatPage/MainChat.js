import SideBar from "./SideBar";
import Chat from "./Chat";

import { useState, useEffect } from "react";
import "./subChat.css";
import { Container, Row, Col } from "react-bootstrap";

function MainChat(props) {
  const user = props.user;
  const [activeContact, setActiveContact] = useState(null);
  const [contacts, setContacts] = useState([]);

  useEffect(() => {
    async function getContacts() {
      let c = await user.getContacts();
      setContacts(c);
    }
    getContacts();
  }, [user]);

  return (
    <Container fluid>
      <Row>
        <Col sm={2}>
          <SideBar
            contacts={contacts}
            self={user}
            active={[activeContact, setActiveContact]}
          />
        </Col>
        {activeContact ? (
          <Chat active={[activeContact, setActiveContact]} user={user} />
        ) : (
          <Col className="p-3 mb-2 bg-secondary text-white"></Col>
        )}
      </Row>
    </Container>
  );
}
export default MainChat;
