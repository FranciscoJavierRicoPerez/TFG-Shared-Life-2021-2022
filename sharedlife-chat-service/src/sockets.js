/* module.exports = function (io) {
    io.on('connection', socket => {
        console.log('NEW USER CONNECTED');

        socket.on('message', (message) => {
            console.log(message);
            io.emit('message', `${(socket.id.substr(0,2))}: ${message}`);
        });

        socket.on('disconnect', () => {
            console.log('a user disconnected!');
        });

    });
} */