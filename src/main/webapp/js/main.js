/// <reference path="jquery.d.ts" />
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
    var version = $("#search-version").val();
    var versionoperator = $("#search-version-cond").val();
    var player = $("#player").val();
    $.getJSON("/searchreplays?version=" + version + "&versionoperator=" + versionoperator + "&player=" + player, create_replays);
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
    var players_td = $(document.createElement("td"));
    players_td.addClass("players");
    for (var i in replay.players) {
        players_td.append(replay.players[i].name + " ");
    }
    date_td.text(make_date_sensible(new Date(replay.gameDate)));
    var downloads_td = $(document.createElement("td"));
    downloads_td.addClass("downloads");
    downloads_td.text(replay.downloads);
    var download_td = $(document.createElement("td"));
    download_td.addClass("download");
    var download_form = $(document.createElement("form"));
    download_form.attr("method", "POST");
    download_form.attr("action", "/" + replay.id + "/download");
    download_form.appendTo(download_td);
    var download_button = $(document.createElement("input"));
    download_button.attr("type", "submit");
    download_button.attr("value", "Download");
    download_button.appendTo(download_form);
    tr.append(version_td);
    tr.append(arena_td);
    tr.append(date_td);
    tr.append(players_td);
    tr.append(downloads_td);
    tr.append(download_td);
    tr.appendTo(replay_area);
}
function make_date_sensible(date) {
    var y = date.getUTCFullYear();
    var monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"];
    var m = monthNames[date.getUTCMonth()];
    var d = date.getUTCDate() + 1;
    var h = date.getUTCHours();
    var min = date.getUTCMinutes();
    return d + " " + m + " " + y + " " + h + ":" + min;
}
