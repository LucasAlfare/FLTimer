import express from 'express';
import { Server } from 'socket.io';
import cors from 'cors';
import User from './User.js';
import Listenable from "./EventManageable.js";
import G from './G.js';


export default class NetworkingManager extends Listenable {

    constructor() {
        super();
    }

    init() {
        const PORT = process.env.PORT || 3000;
        const app = express();
        app.use(cors());
        const srv = app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));
        this.io = new Server(srv, {
            cors: {
                origin: '*'
            }
        });

        this.io.on('connection', socket => {
            const user = new User(socket.id);
            this.notifyListeners(G.EVENT_USER_IN, user);

            socket.on(G.EVENT_USER_TOGGLE, data => {
                this.notifyListeners(G.EVENT_USER_TOGGLE, { user: user, data: data });
            });

            socket.on('disconnect', () => {
                this.notifyListeners(G.EVENT_USER_OUT, user);
            });
        });
    }

    onEvent(event, data) {
        this.io.emit(event, data);
    }
}
