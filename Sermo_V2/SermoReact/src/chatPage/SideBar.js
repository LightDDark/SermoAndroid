import { useState, useEffect } from "react";
import SearchBar from "./SearchBar";
import ContactButton from "./ContactButton";
import { Row, Col } from "react-bootstrap";

function SideBar(props) {
  const contacts = props.contacts;
  const currentUser = props.self;
  const [activeContact, setActiveContact] = props.active;
  const [contactList, setContactList] = useState(contacts);

  useEffect(() => {
    setContactList(contacts);
  }, [contacts]);

  const doSearch = function (query) {
    setContactList(
      contacts.filter((contact) => contact.getName().includes(query))
    );
  };
  const listContacts = contactList.map((contact, index) => {
    const changeActive = function (contact) {
      setActiveContact(contact);
    };
    return (
      <li
        className={
          contact === activeContact
            ? "list-group-item active"
            : "list-group-item"
        }
        aria-current={contact === activeContact ? "true" : "false"}
        onClick={() => changeActive(contact)}
        key={index}
        type="button"
      >
        {contact.getNickName()}
      </li>
    );
  });

  return (
    <div>
      <Row>
        <Col sm={7}>
          <SearchBar doSearch={doSearch} />
        </Col>
        <Col>
          <ContactButton
            contactList={[contactList, setContactList]}
            user={currentUser}
          />
        </Col>
      </Row>
      <Row>
        <ul className="list-group">{listContacts}</ul>
      </Row>
    </div>
  );
}

export default SideBar;
