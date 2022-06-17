import { useState } from "react";
import "./image.css";

function RecordAudio(props) {
  const activeContact = props.active;
  const user = props.user;
  const [audioFileSrc, setAudioFileSrc] = useState(null);
  const [recProgress, setRecProgress] = useState(false);
  const [toggle, setToggle] = useState(false);
  let audioIN = { audio: true };
  navigator.mediaDevices
    .getUserMedia(audioIN)

    .then(function (mediaStreamObj) {
      let start = document.getElementById("btnStart");
      let stop = document.getElementById("btnStop");

      let mediaRecorder = new MediaRecorder(mediaStreamObj);

      start.addEventListener("click", function (ev) {
        mediaRecorder.start();
        setRecProgress(true);
        ev.preventDefault();
      });

      stop.addEventListener("click", function (ev) {
        if (mediaRecorder.state !== "inactive") {
          mediaRecorder.stop();
          setRecProgress(false);
          ev.preventDefault();
        }
      });

      mediaRecorder.ondataavailable = function (ev) {
        dataArray.push(ev.data);
      };

      let dataArray = [];

      mediaRecorder.onstop = function (ev) {
        let audioData = new Blob(dataArray, { type: "audio/mp3;" });

        dataArray = [];

        let audioSrc = window.URL.createObjectURL(audioData);

        //playAudio.src = audioSrc;
        setAudioFileSrc(audioSrc);
      };
    })
    // If any error occurs then handles the error
    .catch(function (err) {
      console.log(err.name, err.message);
    });
  function newAudioMessage() {
    user.sendMessage(audioFileSrc, activeContact.getName(), "audio");
    setToggle(true);
  }
  return (
    <div id="toggle-id">
      <p>
        <button id="btnStart">START RECORDING</button>
        {recProgress && (
          <label className="Prog">recording in progress...</label>
        )}
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button id="btnStop">STOP RECORDING</button>
      </p>
      <button className="Fl" type="submit" onClick={newAudioMessage}>
        send audio
      </button>
    </div>
  );
}
export default RecordAudio;
