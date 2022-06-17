import "./PrintLog.css";
import React from "react";

function PrintLog(props) {
  const length = props.messages.length;
  console.log(length);
  const messages = props.messages.map((msg, index) => {
    const classer =
      msg.user === props.userName ? "Container Mine" : "Container Other";
    const mins =
      msg.date.getMinutes() > 9
        ? msg.date.getMinutes()
        : "0" + msg.date.getMinutes();
    const timer = msg.date.getHours() + ":" + mins;
    return (
      <div key={index}>
        <div className="box" key={index + length}>
          <div className="center" key={index + length + 1}>
            <div className="dialog-1" key={index + length + 2}>
              <div className={classer}>
                <div>
                  {" "}
                  {msg.type === "image" && (
                    <img
                      src={msg.content}
                      className="Mes-img"
                      key={index + length + 4}
                      alt=""
                    ></img>
                  )}
                  {msg.type === "text" && (
                    <div className="Mes-text" key={index + length + 4}>
                      {msg.content}
                    </div>
                  )}
                  {msg.type === "video" && (
                    <video
                      key={index + length + 4}
                      className="Mes-vid"
                      width="100%"
                      height={300}
                      controls
                      src={msg.content}
                    />
                  )}
                  {msg.type === "audio" && (
                    <audio
                      key={index + length + 4}
                      className="Mes-aud"
                      controls
                      src={msg.content}
                    />
                  )}
                </div>
                <div className="timer" key={index + length + 5}>
                  <span className="time-right" key={index + length + 6}>
                    {timer}
                  </span>
                </div>
                <div className="left-point" key={index + length + 3} />
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  });
  return messages;
}

export default PrintLog;
