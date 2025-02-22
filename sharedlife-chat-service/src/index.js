const http = require('http');
const path = require('path');

const express = require('express');
// const socketio = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = require('socket.io')(server, {
    cors: {origin: '*'}
});

app.set('port', process.env.PORT || 3000);

io.on('connection', socket => {
    console.log('NEW USER CONNECTED');

    socket.on('message', (message) => {
        console.log('username => ' + message.username);
        console.log('message => ' + message.message);
        io.emit('message', `${message.username}: ${message.message}`);
    });

    socket.on('disconnect', () => {
        console.log('a user disconnected!');
    });

});

// stating the serve
app.use(express.static(path.join(__dirname, 'public')));

server.listen(app.get('port'), () => {
    console.log('CHAT SERVICE ON PORT ' + app.get('port'));
});