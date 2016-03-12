/// <reference path="jquery.d.ts" />
var replay_area;
$(function () {
    replay_area = $("#replay-area");
    $.getJSON(window.document.URL + "/replays", create_replays);
});
function search() {
    var contains = $("#search-contains").val();
    var version = $("#search-version").val();
    $.getJSON(window.document.URL + "searchreplays?name=" + contains + "&version=" + version, create_replays);
    return false;
}
function create_replays(data) {
    replay_area.empty();
    $("#search").click(search);
    for (var i in data["replays"]) {
        create_replay(data.replays[i]);
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
    date_td.text(make_date_sensible(new Date(replay.gameDate)));
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
