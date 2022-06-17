import "./subChat.css";
import PrintLog from "./PrintLog";
import { Row } from "react-bootstrap";
import { useEffect, useState } from "react";

function SubChat(props) {
  const msgs = props.messages;
  // useEffect(() => {
  //   setMsgs(msgs);
  // }, [msgs, setMsgs]);
  //let msgArr = [];
  //props.messages.then(function (blurb) {
  //    for (let i = 0; i < blurb.length; i++) {
  //        msgArr.push({ type: blurb[i].type, content: blurb[i].content, user: blurb[i].user, date: blurb[i].date });
  //        setmsgArr(msgArr);
  //    }
  //}
  //);
  const userName = props.userName;
  const description = function () {
    if (msgs) {
      return <PrintLog messages={msgs} userName={userName} />;
    }
    return <h6 className="message">no messages</h6>;
  };

  return <Row className="chat">{description()}</Row>;
}
export default SubChat;
