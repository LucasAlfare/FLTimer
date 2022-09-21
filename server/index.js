import { Server } from 'socket.io';
import Express from 'express';
import cors from 'cors';

const PORT = process.env.PORT || 3000;
const app = Express();
app.use(cors());
const srv = app.listen(PORT, () => console.log(`Listening on port ${PORT}...`));
const io = new Server(srv, {
  cors: {
    origin: '*'
  }
});

const users = [];

io.on("connection", socket => {
  console.log("connection created to the user with id of " + socket.id);
  const user = { id: socket.id, times: [] }
  users.push(user);

  socket.broadcast.emit("UserEntered", user.id);

  socket.on("TimerFinished", d => {
    user.times.push(d);
    socket.broadcast.emit("UserTimerFinished", user);
  });
});
