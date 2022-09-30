import express from 'express';
import { Server } from 'socket.io';
import cors from 'cors';


// main piece of data inside server
class User {
    constructor(id) {
        this.id = id;
        this.started = false;
        this.finished = true;
        this.lastStartTime = 0;
        this.solves = [];

        this.onStart = undefined;
        this.onStop = undefined;
    }

    start(time) {
        this.started = true;
        this.finished = false;
        this.lastStartTime = time;
        console.log(`User ${this.id} started.`);
        if (this.onStart)
            this.onStart();
    }

    stop(time) {
        this.finished = true;
        this.solves.push(time - this.lastStartTime);
        console.log(`User ${this.id} stoped.`);
        if (this.onStop)
            this.onStop();
    }

    toggle(data) {
        if (this.started === false && this.finished === true) {
            this.start(data);
        } else if (this.started === true && this.finished === false) {
            this.stop(data);
        }
    }
}


const PORT = process.env.PORT || 3000;
const app = express();
const srv = app.listen(PORT, console.log(`Server running and listening on port ${PORT}...`));
const io = new Server(srv, { cors: { origin: '*' } });
app.use(cors());


let users = [];

io.on('connection', (socket) => {
    const user = new User(socket.id);
    console.log(`User of ID ${user.id} connected.`);
    users.push(user);
    io.emit('NetworkingUsersUpdate', users);

    socket.on('TimerToggle', data => { user.toggle(data) });

    socket.on('disconnect', () => {
        console.log(`User of ID ${user.id} was disconnected.`);
        users = users.filter(u => u.id !== user.id);
        io.emit('NetworkingUsersUpdate', users);
    });
});


// main server loop, aims to loop on 60ups rate
setInterval(() => {
    if (users.length > 0) {
        const finishedUsers = users.filter(u => u.started && u.finished);
        if (finishedUsers.length === users.length) {
            console.log(`ALL USERS HAS FINISHED!`);

            // only resets users state after detecting finishing from all of them
            finishedUsers.forEach(u => { u.started = false });
            io.emit('NetworkingAllUsersFinished', finishedUsers.length === users.length);
        }
    }
}, 1000 / 60);
