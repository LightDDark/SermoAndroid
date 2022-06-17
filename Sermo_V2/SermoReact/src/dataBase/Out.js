class Out {
  static async post(url = "", data = {}, headers = {}) {
    // let head = '"Content-Type": "application/json"';
    headers["Content-Type"] = "application/json";
    for (const [key, value] of Object.entries(headers)) {
      headers.key = value;
    }
    // Default options are marked with *
    const response = await fetch(url, {
      method: "POST", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      headers,
      body: JSON.stringify(data), // body data type must match "Content-Type" header
    });
    return response.ok;
  }

  static async login(url = "", data = {}, headers = {}) {
    // let head = '"Content-Type": "application/json"';
    headers["Content-Type"] = "application/json";
    for (const [key, value] of Object.entries(headers)) {
      headers.key = value;
    }
    // Default options are marked with *
    const response = await fetch(url, {
      method: "POST", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      headers,
      body: JSON.stringify(data), // body data type must match "Content-Type" header
    });
    if (response.body === null) {
      return response.ok;
    }
    return response.text();
  }

  static async get(url = "", headers = {}) {
    //let head = 'accept: "text/plain"';
    headers.accept = "text/plain";
    for (const [key, value] of Object.entries(headers)) {
      headers.key = value;
    }
    // Default options are marked with *
    const response = await fetch(url, {
      method: "GET", // *GET, POST, PUT, DELETE, etc.
      mode: "cors", // no-cors, *cors, same-origin
      headers,
    });
    if (response.body === null || !response.ok) {
      return null;
    }
    return response.json();
  }
}

export default Out;
