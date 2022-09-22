import NetworkingManager from "./NetworkingManager.js";
import UserManager from "./UserManager.js";
import G from './G.js';

const networkMng = new NetworkingManager();
const userMng = new UserManager();

networkMng.addListener(userMng);
userMng.addListener(networkMng);

networkMng.init();

// "main" application loop to handle process
// TODO: automatically kick out most "inactive" users
setInterval(() => {
    if (userMng.users.length > 0) {
        let nFinished = 0;
        userMng.users.forEach(user => {
            // sum of number with boolean... weird
            nFinished += user.finished;
        });

        if (nFinished === userMng.users.length) {
            userMng.users.forEach(user => {
                user.started = false;
                user.finished = false;
            });
            console.log('spawning a new scramble...');
            // TODO...
        }
    }
}, 1000 / 60);
