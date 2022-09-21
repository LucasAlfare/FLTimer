export default class User {

    constructor(id) {
        this.id = id;
        this.startTime = 0;
        this.endTime = 0;
        this.started = false;
        this.finished = false;
        this.lastTime = -1;
        this.times = []; //useful for future stats
        this.lastActiveTime = 0;
        console.log(`[USER] ${this.id} created.`);
    }

    start(t) {
        this.lastActiveTime = 0;
        this.started = true;
        this.startTime = t;
        console.log(`[USER] ${this.id} started.`);
    }

    stop(t) {
        this.finished = true;
        this.endTime = t;
        this.lastTime = this.endTime - this.startTime;
        this.times.push(this.lastTime);
        this.lastActiveTime = Date.now();
        console.log(`[USER] ${this.id} stopped.`);
    }
}