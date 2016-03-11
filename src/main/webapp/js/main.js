/// <reference path="jquery.d.ts" />
var replay_area;
var Replay = (function () {
    function Replay(name, version, downloads, download_url, date_millis) {
        this.name = name;
        this.version = version;
        this.downloads = downloads;
        this.download_url = download_url;
        this.date = new Date(date_millis);
    }
    return Replay;
}());
$(function () {
    replay_area = $("#replay-area");
    $.getJSON(window.document.URL + "/replays", create_replays);
});
function create_replays(data) {
    replay_area.empty();
    for (var i in data["replays"]) {
        var replay_data = data.replays[i];
        var replay = new Replay(replay_data["name"], replay_data["version"], replay_data["downloads"], "", replay_data.gameDate);
        create_replay(replay);
    }
}
function create_replay(replay) {
    var tr = $(document.createElement("tr"));
    var name_td = $(document.createElement("td"));
    name_td.addClass("name");
    name_td.text(replay.name);
    var version_td = $(document.createElement("td"));
    version_td.addClass("version");
    version_td.text(replay.version);
    var date_td = $(document.createElement("td"));
    date_td.addClass("date");
    date_td.text(make_date_sensible(replay.date));
    var downloads_td = $(document.createElement("td"));
    downloads_td.addClass("downloads");
    downloads_td.text(replay.downloads);
    var download_td = $(document.createElement("td"));
    var download_button = $(document.createElement("input"));
    download_button.attr("type", "submit");
    download_button.attr("value", "Download");
    download_button.appendTo(download_td);
    tr.append(name_td);
    tr.append(version_td);
    tr.append(date_td);
    tr.append(downloads_td);
    tr.append(download_td);
    tr.appendTo(replay_area);
}
function make_date_sensible(date) {
    var y = date.getUTCFullYear();
    var m = date.getUTCMonth() + 1;
    var d = date.getUTCDate() + 1;
    return d + "." + m + "." + y;
}
