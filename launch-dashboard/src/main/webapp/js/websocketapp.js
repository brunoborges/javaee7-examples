if (typeof String.prototype.startsWith !== 'function') {
    // see below for better implementation!
    String.prototype.startsWith = function(str) {
        return this.indexOf(str) === 0;
    };
}

var index = -1;
$(window).keydown(function(event) {
    if (event.which === 27) {
        // the following seems to fix the symptom but only in case the document has the focus
        // prevents websocket ESC bug
        event.preventDefault();
    }
});

function updateImage(tweet) {
    var localIndex = 0;
    if (index >= 11) {
        index = 0;
    } else {
        index++;
        localIndex = index;
    }

    $('#' + localIndex + ' a').addClass('loading');

    var imageUrl = tweet.url + ':thumb';
    $("<img />").load(function() {
        $('#' + localIndex + ' a').attr("href", tweet.url);
        $('#' + localIndex + ' a').attr("rel", "lightbox");
        $('#' + localIndex + ' a').attr("title", "@" + tweet.name + ": " + tweet.text);
        $('#' + localIndex + ' a img').attr("src", imageUrl);
        //$('#' + localIndex).html('<a title="@' + tweet.name + ': ' + tweet.text + '" href="' + tweet.url + '"><img src="' + imageUrl + '" /></a>');
        $('#' + localIndex + ' a').removeClass('loading');
    }).error(function() {
        $('#' + localIndex + ' a').removeAttr("rel", "lightbox");
        $('#' + localIndex + ' a').removeClass('loading');
    }).attr('src', imageUrl);
}

function setStats(data) {
    var statistics = jQuery.parseJSON(data);

    if (startedOn === 0) {
        startUptimeCounter(statistics.startedOn);
    }

    $('#tweetCount').text(statistics.tweetCount);
    $('#imageCount').text(statistics.imageCount);
    $("#olympic").text(statistics.keywords);
}

function gallery(data) {
    var tweet = jQuery.parseJSON(data);
    updateImage(tweet);
}

var _pathname = document.location.pathname;
var _location = 'ws://' + document.location.host + _pathname;

var appImages = {
    start: function() {
        var location = _location + "app/images";
        this._ws = new WebSocket(location);
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
    },
    _onmessage: function(m) {
        if (m.data) {
            gallery(m.data);
        }
    },
    stop: function() {
        this._ws.close();
    },
    _onclose: function(m) {
        this._ws = null;
    },
    search: function(search) {
        this._ws.send("search " + search);
    },
    sample: function() {
        this._ws.send("sample");
    }
};

var appStatistics = {
    clearStatistics: function() {
        if (this._ws) {
            this._ws.send("clear");
        }
    },
    stop: function() {
        this._ws.close();
    },
    start: function() {
        var location = _location + "app/statistics";
        this._ws = new WebSocket(location);
        this._ws.onmessage = this._onmessage;
        this._ws.onclose = this._onclose;
    },
    _onmessage: function(m) {
        if (m.data) {
            setStats(m.data);
        }
    },
    _onclose: function(m) {
        this._ws = null;
    }
};

/**
 * UPTIME
 */
var startedOn = 0;
var uptime = 0;
var intervalId = 0;

function startUptimeCounter(arg) {
    startedOn = arg;
    uptime = new Date().getTime();
    intervalId = setInterval(refreshUptime, 1000);
}

function refreshUptime() {
    uptime = uptime + 1000;
    var difference = uptime - startedOn;
    var daysDifference = Math.floor(difference / 1000 / 60 / 60 / 24);
    difference -= daysDifference * 1000 * 60 * 60 * 24;
    var hoursDifference = Math.floor(difference / 1000 / 60 / 60);
    difference -= hoursDifference * 1000 * 60 * 60;
    var minutesDifference = Math.floor(difference / 1000 / 60);
    difference -= minutesDifference * 1000 * 60;
    var secondsDifference = Math.floor(difference / 1000);

    if (hoursDifference < 10)
        hoursDifference = "0" + hoursDifference;
    if (minutesDifference < 10)
        minutesDifference = "0" + minutesDifference;
    if (secondsDifference < 10)
        secondsDifference = "0" + secondsDifference;

    $("#uptime").text(daysDifference + "d " + hoursDifference + ":" + minutesDifference + ":" + secondsDifference);
}

/**
 * Setup rows and columns for gallery
 */
$(document).ready(setupGallery);

var rows = 3;
var cols = 4;
var cells = cols * rows;

function setupGallery() {
    var picIndex = 0;
    var localRows = 0;
    while (localRows < rows) {
        var localCols = 0;
        $("#pictures").append("<div class='imageRow' id='imageRow_" + localRows + "'>");
        while (localCols < cols) {
            $("#imageRow_" + localRows).append("<div id='" + (picIndex) + "' class='single'>");
            $("#" + picIndex++).append("<a href='#'><img src='images/tdc.png'/></a>");
            localCols++;
        }
        localRows++;
    }
}

var running = true;

$(document).ready(function() {
    if (!window.WebSocket) {
        $('#pictures').text("ERROR: Your browser doesn't support websockets!");
    } else {
        appImages.start();
        appStatistics.start();
    }

    $("#pause").click(function() {
        if (running) {
            appStatistics.stop();
            appImages.stop();
            $(this).text("Resume");
            running = false;
        } else {
            appStatistics.start();
            appImages.start();
            $(this).text("Pause");
            running = true;
        }
    });
});
