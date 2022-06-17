import { Card, Row } from "react-bootstrap";

function ContactCard(props) {
  const contact = props.contact;

  return (
    <Card as={Row}>
      <Card.Body>
        <Card.Title>{contact.getNickName()}</Card.Title>
      </Card.Body>
    </Card>
  );
}

export default ContactCard;
