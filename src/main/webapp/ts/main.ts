/// <reference path="jquery.d.ts" />

var replay_area

class Replay {
  date : Date;
  constructor(public name: string, public version: string, public downloads: number, public download_url: string, date_millis: number) {
    this.date = new Date(date_millis);
  }
}

$(function() {
  replay_area = $("#replay-area")
  $.getJSON(window.document.URL + "/replays", create_replays)
})

function create_replays(data) {
  replay_area.empty()
  for (var i in data["replays"]) {
    var replay_data = data.replays[i];
    var replay = new Replay(replay_data["name"], replay_data["version"], replay_data["downloads"], "", replay_data.gameDate);
    create_replay(replay);
  }
}

function create_replay(replay: Replay) {
  var tr = $(document.createElement("tr"));
  var name_td = $(document.createElement("td"));
  name_td.addClass("name")
  name_td.text(replay.name)
  var version_td = $(document.createElement("td"));
  version_td.addClass("version")
  version_td.text(replay.version)
  var date_td = $(document.createElement("td"));
  date_td.addClass("date")
  date_td.text(make_date_sensible(replay.date))
  var downloads_td = $(document.createElement("td"));
  downloads_td.addClass("downloads")
  downloads_td.text(replay.downloads)
  var download_td = $(document.createElement("td"));
  var download_button = $(document.createElement("input"));
  download_button.attr("type", "submit");
  download_button.attr("value", "Download");
  download_button.appendTo(download_td)

  tr.append(name_td);
  tr.append(version_td);
  tr.append(date_td);
  tr.append(downloads_td);
  tr.append(download_td);
  tr.appendTo(replay_area)
}

function make_date_sensible(date: Date) {
  var y = date.getUTCFullYear();
  var m = date.getUTCMonth() + 1;
  var d = date.getUTCDate() + 1;
  return d + "." + m + "." + y
}
