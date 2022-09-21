import EventManageable from "./EventManageable.js";
import G from './G.js';


export default class UserManager extends EventManageable {

    constructor() {
        super();
        this.users = [];
    }

    addUser(user) {
        const check = this.users.filter(u => u === user)[0];
        if (!check) {
            this.users.push(user);
            const ids = this.users.map(u => u.id);
            this.notifyListeners(G.EVENT_USER_IN, ids);
        }
    }

    removeUser(id) {
        this.users = this.users.filter(u => u.id !== id);
        this.notifyListeners(G.EVENT_USER_OUT, id);
    }

    userStart(user, data) {
        user.start(data);
        this.notifyListeners(G.EVENT_USER_START, {
            id: user.id,
            started: user.started,
            finished: user.finished
        });
    }

    userStop(user, data) {
        user.stop(data);
        this.notifyListeners(G.EVENT_USER_STOP, {
            id: user.id,
            started: user.started,
            finished: user.finished,
            lastTime: user.lastTime,
            scramble: "[scramble]"
        });
    }

    userToggle(user, data) {
        if (!user.started && !user.finished) {
            this.userStart(user, data);
        } else if (user.started && !user.finished) {
            this.userStop(user, data);
        }
    }

    onEvent(event, data) {
        if (event === G.EVENT_USER_IN) {
            this.addUser(data);
        } else if (event === G.EVENT_USER_TOGGLE) {
            this.userToggle(data.user, data.data);
        } else if (event === G.EVENT_USER_OUT) {
            this.removeUser(data.id);
        }
    }
}