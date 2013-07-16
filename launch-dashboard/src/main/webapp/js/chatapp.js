/**
 * 
 */
var _pathname = document.location.pathname;
var _location = 'ws://' + document.location.host + _pathname;

var chatroom = {
    send: function(text) {
        this._ws.send(text);
    },
    exit: function() {
        this._ws.close();
    },
    join: function() {
        var location = _location + "chatroom";
        this._ws = new WebSocket(location);
        this._ws.onopen = this._onopen;
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
    },
    _onopen: function(m) {
    },
    _onmessage: function(m) {
        if (m.data) {
            processChatMessage(m.data);
        }
    },
    _onclose: function(m) {
        this._ws = null;
    }
};

function fireSendMessage() {
    var _message = $("#message").val();
    chatroom.send(_message);
    $("#message").val("");
    $("#message").focus();
}

$(document).ready(function() {
    $("#sendMessage").click(fireSendMessage);

    $("#message").keypress(function(event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode === 13) {
            fireSendMessage();
        }
    });

    chatroom.join();
});

function processChatMessage(data) {
    if (data.startsWith("chat ")) {
        appendChatMessage(data.substring(5));
    } else if (data.startsWith("add ")) {
        appendUser(data.substring(4));
    } else if (data.startsWith("del ")) {
        removeUser(data.substring(4));
    } else if (data === "loggedin") {
        loggedIn();
    }
}

function loggedIn() {
    $("#signin").hide();
    $("#signout").show();
}

function appendChatMessage(message) {
    var txt = $("#chatroom");
    txt.val(txt.val() + "\n" + message);
    txt.scrollTop(txt[0].scrollHeight - txt.height());
}

var _users = new Array();

function appendUser(user) {
    _users.push(user);
    var _list = _users.join("\n");
    $("#users").val(_list);
}

function removeUser(user) {
    var position = $.inArray(user, _users);

    if (~position)
        _users.splice(position, 1);
}

if (typeof String.prototype.startsWith !== 'function') {
    // see below for better implementation!
    String.prototype.startsWith = function(str) {
        return this.indexOf(str) === 0;
    };
}
