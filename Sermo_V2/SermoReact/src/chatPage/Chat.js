import ContactCard from "./ContactCard";
import SubChat from "./SubChat";
import MessageForm from "./MessageForm";
import Col from "react-bootstrap/Col";
import { useEffect, useState } from "react";
import { HubConnectionBuilder, HubConnectionState } from "@microsoft/signalr";

function Chat(props) {
  const [activeContact, setActiveContact] = props.active;
  const [msgs, setMsgs] = useState(null);
  const user = props.user;
  const [connection, setConnection] = useState(null);

  useEffect(() => {
    const newConnection = new HubConnectionBuilder()
      .withUrl("https://localhost:7217/MsgHub")
      .withAutomaticReconnect()
      .build();

    setConnection(newConnection);
  }, []);

  useEffect(() => {
    if (connection && connection.state === HubConnectionState.Disconnected) {
      connection
        .start()
        .then((result) => {
          console.log("Connected!");

          connection.on("ReceiveMessage", (from, message) => {
            const update = async () => {
              const newMsgs = await user.getMessages(activeContact.getName());
              setMsgs(newMsgs);
            };
            update();
            update();
            update();
            // if (activeContact.getName() === from) {
            //   const msg = {
            //     type: "text",
            //     content: message.content,
            //     user: activeContact.getName(),
            //     date: new Date(message.created),
            //   };
            //   const newMsgs = msgs.slice();
            //   newMsgs.push(msg);
            //   setMsgs(newMsgs);
            // }
          });
        })
        .catch((e) => console.log("Connection failed: ", e));
    }
  }, [connection, activeContact, msgs, user]);

  const sendMessage = (to, message) => {
    if (connection.state === HubConnectionState.Connected) {
      try {
        connection.invoke("SendMessage", to, message);
      } catch (e) {
        console.log(e);
      }
    } else {
      alert("No connection to server yet.");
    }
  };

  useEffect(() => {
    async function getMsgs() {
      const msgs = await user.getMessages(activeContact.getName());
      setMsgs(msgs);
    }
    getMsgs();
  }, [activeContact, user]);
  return (
    <Col>
      <script src="~/lib/dist/browser/signalr.js"></script>
      <script src="~/js/ws.js"></script>
      <ContactCard contact={activeContact} />
      <SubChat messages={msgs} userName={user.getName()} />
      <MessageForm
        id="inside-card"
        active={[activeContact, setActiveContact]}
        user={user}
        messages={[msgs, setMsgs]}
        sendMsg={sendMessage}
      />
    </Col>
  );
}

export default Chat;
