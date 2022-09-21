import NetworkingManager from "./NetworkingManager.js";
import UserManager from "./UserManager.js";

const nm = new NetworkingManager();
const um = new UserManager();

nm.addListener(um);
um.addListener(nm);

nm.init();

// "main" application loop to handle process
// TODO: automatically kick out most "inactive" users
setInterval(() => {
    if (um.users.length > 0) {
        let nFinished = 0;
        um.users.forEach(user => {
            // sum of number with boolean... weird
            nFinished += user.finished;
        });

        if (nFinished === um.users.length) {
            um.users.forEach(user => {
                user.started = false;
                user.finished = false;
            });
            console.log('spawning a new scramble...');
            // TODO...
        }
    }
}, 1000 / 60);
