var replay_area;
var can_request_JSON = true;
$(function () {
    replay_area = $("#replay-area");
    can_request_JSON = false;
    $.getJSON("/replays", create_replays);
});
function search() {
    if (!can_request_JSON) {
        return;
    }
    var contains = $("#search-contains").val();
    var version = $("#search-version").val();
    console.log("I would serach for replays with version " + version + " but REST API got a little dumber :(");
    return false;
}
function create_replays(data) {
    replay_area.empty();
    $("#search").click(search);
    for (var i in data["replays"]) {
        create_replay(data.replays[i]);
    }
    can_request_JSON = true;
}
function create_replay(replay) {
    var tr = $(document.createElement("tr"));
    var version_td = $(document.createElement("td"));
    version_td.addClass("version");
    version_td.text(replay.version);
    var arena_td = $(document.createElement("td"));
    arena_td.addClass("arena");
    arena_td.text(replay.arena);
    var date_td = $(document.createElement("td"));
    date_td.addClass("date");
    date_td.text(make_date_sensible(new Date(replay.gameDate)));
    var downloads_td = $(document.createElement("td"));
    downloads_td.addClass("downloads");
    downloads_td.text(replay.downloads);
    var download_td = $(document.createElement("td"));
    download_td.addClass("download");
    var download_button = $(document.createElement("input"));
    download_button.attr("type", "submit");
    download_button.attr("value", "Download");
    download_button.appendTo(download_td);
    tr.append(version_td);
    tr.append(arena_td);
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
