import {
  Button,
  Form,
  Col,
  Popover,
  OverlayTrigger,
  Stack,
} from "react-bootstrap";
import { useRef, useState } from "react";
import UploadImage from "./UploadImage";
import UploadVideo from "./UploadVideo";
import "./image.css";
import RecordAudio from "./RecordAudio";

function MessageForm(props) {
  const [activeContact, setActiveContact] = props.active;
  const user = props.user;
  const sendMsg = props.sendMsg;
  //const [msgs, setMsgs] = props.messages;
  const [type, setType] = useState(null);

  // const imageM = useRef();
  // const videoM = useRef();
  // const audioM = useRef();

  // const [shouldUploadImage, setShouldUploadImage] = useState(false);
  // const itsImageTime = function () {
  //   setShouldUploadImage(true);
  // };
  // const stopImageTime = function () {
  //   setShouldUploadImage(false);
  // };
  // const [shouldUploadVideo, setShouldUploadVideo] = useState(false);
  // const itsVideoTime = function () {
  //   setShouldUploadVideo(true);
  // };
  // const [shouldUploadRecord, setShouldUploadRecord] = useState(false);
  // const itsRecordTime = function () {
  //   setShouldUploadRecord(true);
  // };

  // const popover = (
  //   <Popover id="popover-basic">
  //     <Popover.Body>
  //       <Stack direction="horizontal" gap={2}>
  //         <label
  //           ref={imageM}
  //           className="imBut"
  //           htmlFor="actual-btn-im"
  //           onClick={itsImageTime}
  //           onChange={() => setType("image")}
  //         >
  //           Image
  //           {shouldUploadImage && (
  //             <UploadImage user={user} active={activeContact} />
  //           )}
  //           {stopImageTime}
  //         </label>
  //         <label
  //           ref={videoM}
  //           className="vidBut"
  //           htmlFor="actual-btn-vid"
  //           onClick={itsVideoTime}
  //           onChange={() => setType("video")}
  //         >
  //           Video
  //           {shouldUploadVideo && (
  //             <UploadVideo height={300} user={user} active={activeContact} />
  //           )}
  //         </label>
  //         <label
  //           data-toggle="collapse"
  //           ref={audioM}
  //           className="audBut"
  //           htmlFor="actual-btn-vid"
  //           onClick={itsRecordTime}
  //           onChange={() => setType("audio")}
  //         >
  //           Audio
  //           {shouldUploadRecord && (
  //             <RecordAudio user={user} active={activeContact} />
  //           )}
  //         </label>
  //       </Stack>
  //     </Popover.Body>
  //   </Popover>
  // );

  const textM = useRef(null);

  const sendTextMessage = function () {
    if (user) {
      const msg = textM.current.value;
      textM.current.value = "";
      // const msgsCopy = msgs.slice();
      // msgsCopy.push({
      //   type: "text",
      //   content: msg,
      //   user: user.getName(),
      //   date: new Date(),
      // });
      // setMsgs(msgsCopy);
      user.sendMessage(msg, activeContact);
      sendMsg(activeContact.getName(), msg);
    }
  };

  const handleSubmit = function (event) {
    event.preventDefault();
    if (!textM.current.value) {
      return;
    }
    sendTextMessage();
  };

  return (
    <Form autoComplete="off" noValidate onSubmit={handleSubmit}>
      <Stack direction="horizontal">
        <Form.Group
          className="col-xxl-10"
          as={Col}
          controlId="validationCustom01"
        >
          <Form.Control
            className="MsgInput"
            ref={textM}
            type="text"
            placeholder="Message"
            onChange={() => setType("text")}
          />
        </Form.Group>

        <Button type="submit" variant="success">
          Send
        </Button>
      </Stack>
    </Form>
  );
}

export default MessageForm;

// <OverlayTrigger trigger="click" placement="top" overlay={popover}>
//           <Button variant="secondary">Options</Button>
//         </OverlayTrigger>
