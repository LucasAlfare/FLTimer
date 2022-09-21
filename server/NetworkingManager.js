import { Server } from 'socket.io';
import Express from 'express';
import cors from 'cors';

class NetworkingManager {

    constructor() {
        const PORT = process.env.PORT || 3000;
        const app = Express();
        app.use(cors());
        const srv = app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));
        this.io = new Server(srv, {
            cors: {
                origin: '*'
            }
        });
    }

    broadcastEmit(event, data) {
        this.io.broadcast.emit(event, data);
    }
}