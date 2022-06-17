import User from "./User";
import Out from "./Out";

class UserData {
  constructor(server) {
    this.server = server;
  }
  async getUser(userName) {
    let response = await Out.get(
      "https://" + this.server + "/api/Users/" + userName
    );
    if (response !== null) {
      return new User(response.id, response.name, response.server);
    }
    return new User();
  }

  async addUser(userName, password, nickName) {
    Out.post("https://" + this.server + "/api/Users", {
      id: userName,
      password: password,
      name: nickName,
    });
    return true;
  }

  async login(userName, password) {
    let response = await Out.login(
      "https://" + this.server + "/api/Users/Login",
      {
        id: userName,
        password: password,
      }
    );
    if (!response) {
      return null;
    }
    let user = new User(userName, userName, this.server, response);
    return user;
  }
}

const users = new UserData("localhost:7217");

export default users;
