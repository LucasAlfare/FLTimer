export default class EventManageable {

    constructor() {
        this.listeners = [];
    }

    addListener(listener) {
        const check = this.listeners.filter(l => l === listener)[0];
        if (!check) {
            this.listeners.push(listener);
        }
    }

    removeListener(listener) {
        this.listeners = this.listeners.filter(l => l !== listener);
    }

    notifyListeners(event, data) {
        console.log(`Emitting ${event}...`);
        for (const l of this.listeners) {
            l.onEvent(event, data);
        }
    }
}