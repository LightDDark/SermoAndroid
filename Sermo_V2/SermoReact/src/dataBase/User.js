import Out from "./Out";

class User {
  constructor(userName, nickName, server, token = "") {
    this.userName = userName;
    this.nickName = nickName;
    this.server = server;
    this.token = token;
  }

  async addContact(contact) {
    if (!contact) {
      console.log("contact is undefined");
    } else {
      Out.post(
        "https://" + this.server + "/api/contacts",
        {
          id: contact.getName(),
          name: contact.getNickName(),
          server: contact.getServer(),
        },
        { Authorization: "Bearer " + this.token }
      );

      if (this.server !== contact.server) {
        try {
          Out.post(
            "https://" + contact.server + "/api/invitations",
            {
              id: this.getName(),
              name: contact.getName(),
              server: this.getServer(),
            },
            { Authorization: "Bearer " + this.token }
          );
        } catch (e) {
          console.log(e);
        }
      }
    }
    return this;
  }

  getName() {
    return this.userName;
  }

  getNickName() {
    return this.nickName;
  }

  getServer() {
    return this.server;
  }

  async getContacts() {
    let response = await Out.get("https://" + this.server + "/api/contacts", {
      Authorization: "Bearer " + this.token,
    });
    let contacts = [];
    if (response !== null && response !== undefined && response.length !== 0) {
      response.forEach((x) => {
        contacts.push(new User(x.id, x.name, x.server));
      });
    }
    return contacts;
  }

  async getMessages(contactName) {
    let messages = [];
    let response = await Out.get(
      "https://" +
        this.getServer() +
        "/api/contacts/" +
        contactName +
        "/messages",
      { Authorization: "Bearer " + this.token }
    );
    if (response === null || response === undefined || response.length === 0) {
      return [];
    }
    response.forEach((msg) => {
      messages.push({
        type: "text",
        content: msg.content,
        user: msg.sent ? this.userName : contactName,
        date: new Date(msg.created),
      });
    });
    return messages;
  }

  async sendMessage(content, contact, type = "text") {
    Out.post(
      "https://" +
        this.getServer() +
        "/api/contacts/" +
        contact.getName() +
        "/messages",
      {
        content: content,
      },
      { Authorization: "Bearer " + this.token }
    );
    if (this.server !== contact.server) {
      try {
        Out.post(
          "https://" + contact.getServer() + "/api/transfer",
          {
            from: this.getName(),
            to: contact.getName(),
            content: content,
          },
          { Authorization: "Bearer " + this.token }
        );
      } catch (e) {
        console.log(e);
      }
    }
  }
}

export default User;
