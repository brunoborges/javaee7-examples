var _pathname = document.location.pathname;
var _location = 'ws://' + document.location.host + _pathname;

var appImages = {
    start: function() {
        if (this._ws !== undefined) this.stop();
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
    }
};

var rows = 1;
var cols = 6;
var cells = cols * rows;

function setupGallery() {
    var picIndex = 0;
    var localRows = 0;
    while (localRows < rows) {
        var localCols = 0;
        $("#pictures").append("<div class='imageRow' id='imageRow_" + localRows + "'>");
        while (localCols < cols) {
            $("#imageRow_" + localRows).append("<div id='" + (picIndex) + "' class='single'>");
            $("#" + picIndex++).append("<a href='#'><img src='images/glassfish-ico.png'/></a>");
            localCols++;
        }
        localRows++;
    }
}

function gallery(data) {
    var tweet = jQuery.parseJSON(data);
    updateImage(tweet);
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
    if (index >= cells) {
        index = 0;
    } else {
        index++;
        localIndex = index;
    }

    $('#' + localIndex + ' a').addClass('loading');

    var imageUrl = tweet.imageUrl + ':thumb';
    $("<img />").load(function() {
        $('#' + localIndex + ' a').attr("href", tweet.tweetUrl);
        $('#' + localIndex + ' a').attr("rel", "lightbox");
        $('#' + localIndex + ' a').attr("target", "_blank");
        $('#' + localIndex + ' a img').attr("src", imageUrl);
        $('#' + localIndex + ' a').removeClass('loading');
    }).error(function() {
        $('#' + localIndex + ' a').removeAttr("rel", "lightbox");
        $('#' + localIndex + ' a').removeClass('loading');
    }).attr('src', imageUrl);
}

$(document).ready(function() {
    setupGallery();
    appImages.start();
});