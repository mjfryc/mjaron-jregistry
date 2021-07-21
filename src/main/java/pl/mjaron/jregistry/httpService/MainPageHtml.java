package pl.mjaron.jregistry.httpService;

public class MainPageHtml {

    static String generate() {
        return "<!doctype html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <title>Registry</title>\n" +
                "  <link rel=\"stylesheet\" href=\"pure.css\"\n" +
                "    integrity=\"sha384-Uu6IeWbM+gzNVXJcM9XV3SohHtmWE+3VGi496jvgX1jyvDTXfdK+rfZc8C1Aehk5\" crossorigin=\"anonymous\">\n" +
                "  <link rel=\"stylesheet\" href=\"grids-responsive-min.css\">\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<style>\n" +
                "  .mjaron-panel {\n" +
                "    background: #1f8dd6;\n" +
                "    border-radius: 3px;\n" +
                "    color: #fff;\n" +
                "  }\n" +
                "\n" +
                "  .mjaron-menu-button {\n" +
                "    display: table-cell;\n" +
                "    vertical-align: middle;\n" +
                "    text-align: center;\n" +
                "    margin: auto;\n" +
                "    margin-top: 1em;\n" +
                "    margin-bottom: 1em;\n" +
                "  }\n" +
                "\n" +
                "  .mjaron-blocks {\n" +
                "    margin: auto;\n" +
                "\n" +
                "  }\n" +
                "\n" +
                "  body,\n" +
                "  html {\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "  }\n" +
                "\n" +
                "  .mjaron-all-window {\n" +
                "    /* width: 100vw;\n" +
                "    height: 100vh; */\n" +
                "    border-radius: 10px;\n" +
                "    top: 3%;\n" +
                "    bottom: 3%;\n" +
                "    left: 3%;\n" +
                "    right: 3%;\n" +
                "    margin: auto;\n" +
                "    z-index: 999;\n" +
                "    position: fixed;\n" +
                "    /* width: 96%;\n" +
                "    height: 96%; */\n" +
                "    background: #436634;\n" +
                "  }\n" +
                "</style>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "  <div class=\"pure-g\">\n" +
                "    <div class=\"pure-u-1-1\">\n" +
                "      <p style=\"text-align: center;\">JRegistry</p>\n" +
                "    </div>\n" +
                "    <div class=\"pure-u-1-5 mjaron-panel\">\n" +
                "\n" +
                "      <div class=\"pure-g\">\n" +
                "        <button class=\"pure-button mjaron-menu-button\" onclick=\"updateTable()\"><i class=\"fa fa-cog\"></i>Refresh</button>\n" +
                "      </div>\n" +
                "      <div class=\"pure-g\">\n" +
                "        <button class=\"pure-button mjaron-menu-button\"><i class=\"fa fa-cog\"></i>Not used yet</button>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "    <div class=\"pure-u-4-5\">\n" +
                "      <table class=\"pure-table\" style=\"width: 97%; margin: auto;\" id=\"proptb\">\n" +
                "        <thead>\n" +
                "          <tr>\n" +
                "            <th># Path</th>\n" +
                "            <th>Is node?</th>\n" +
                "            <th>Value</th>\n" +
                "            <th>Default value</th>\n" +
                "            <th>Enumeration</th>\n" +
                "            <th>Actions</th>\n" +
                "          </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "          <tr>\n" +
                "            <!-- <td>1</td>\n" +
                "            <td>Honda</td>\n" +
                "            <td>Accord</td>\n" +
                "            <td>2009</td> -->\n" +
                "          </tr>\n" +
                "        </tbody>\n" +
                "      </table>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <script>\n" +
                "    var allOptions = {};\n" +
                "\n" +
                "    function updateProperty(path, value) {\n" +
                "      var toUpdate = {};\n" +
                "      toUpdate[\"path\"] = path;\n" +
                "      toUpdate[\"value\"] = value;\n" +
                "      httpPost(\"/u?path=\" + path + \"&value=\" + value, JSON.stringify(toUpdate));\n" +
                "    }\n" +
                "\n" +
                "    var popupHide = function () {\n" +
                "      try {\n" +
                "        document.getElementById(\"popup\").remove();\n" +
                "        popupShown = false;\n" +
                "      } catch (e) { console.log(e); };\n" +
                "    };\n" +
                "\n" +
                "    document.addEventListener('keydown', function (e) {\n" +
                "      if (e.keyCode == 27) {\n" +
                "        popupHide();\n" +
                "      }\n" +
                "    });\n" +
                "\n" +
                "    var popupShown = false;\n" +
                "    var editOption = function (path) {\n" +
                "      if (popupShown) {\n" +
                "        return;\n" +
                "      }\n" +
                "      popupShown = true;\n" +
                "      console.log(\"About to edit option: \" + path);\n" +
                "      var entry = allOptions[path];\n" +
                "      if (entry === undefined) {\n" +
                "        console.error(\"Bad option path!\");\n" +
                "        popupShown = false;\n" +
                "        return;\n" +
                "      }//document.body.innerHTML\n" +
                "      var editForm =\n" +
                "        '<div id=\"popup\" class=\"mjaron-all-window\">' +\n" +
                "        '<div class=\"pure-u-1-2\">' +\n" +
                "        `<form class=\"pure-form pure-form-aligned\" style=\"margin:auto;display:flex;\">\n" +
                "            <fieldset>\n" +
                "                <div class=\"pure-control-group\">\n" +
                "                    <label for=\"e-name\">Name</label>\n" +
                "                    <input type=\"text\" id=\"e-name\" placeholder=\"Name\" value=\"${entry.name}\" readonly=\"\"/>\n" +
                "                </div>\n" +
                "                <div class=\"pure-control-group\">\n" +
                "                    <label for=\"e-path\">Path</label>\n" +
                "                    <input type=\"text\" id=\"e-path\" placeholder=\"Path\" value=\"${entry.path}\" readonly=\"\"/>\n" +
                "                </div>\n" +
                "                <div class=\"pure-control-group\">\n" +
                "                    <label for=\"is-node\">Is node</label>\n" +
                "                    <input type=\"text\" id=\"is-node\" placeholder=\"isNode\" value=\"${entry.isNode}\" readonly=\"\"/>\n" +
                "                </div>\n" +
                "                <div class=\"pure-control-group\">\n" +
                "                    <label for=\"e-value\">Value</label>\n" +
                "                    <input type=\"text\" id=\"e-value\" value=\"${entry.value}\" placeholder=\"Value\"/>\n" +
                "                </div>\n" +
                "                <div class=\"pure-control-group\">\n" +
                "                  <label for=\"v-enums\">Enum values</label>\n" +
                "                  <select id=\"v-enums\" class=\"pure-input-1-2\">`;\n" +
                "      for (var enumEntryIdx in entry.enums) {\n" +
                "        var enumEntry = entry.enums[enumEntryIdx];\n" +
                "        editForm += `<option>${enumEntry}</option>`;\n" +
                "      }\n" +
                "      editForm += `</select>\n" +
                "                </div>\n" +
                "                <div class=\"pure-controls\">\n" +
                "\n" +
                "            <button id=\"updateBtn\" class=\"pure-button pure-button-primary\">Submit</button>\n" +
                "            <button onclick=\"popupHide();\" class=\"pure-button button-warning\">Cancel</button>\n" +
                "        </div>\n" +
                "            </fieldset>\n" +
                "        </form>`\n" +
                "        + '</div>'\n" +
                "\n" +
                "        + '<div class=\"pure-u-1-2\">'\n" +
                "        + '<form class=\"pure-form pure-form-aligned\" style=\"margin:auto;display:flex;height:100%;justify-content: center; \">'\n" +
                "        + '<fieldset style=\"height:100%;\">'\n" +
                "        + '<div class=\"pure-control-group\" style=\"height:100%;\">'\n" +
                "        + '<textarea style=\"height:100%;\" readonly=\"\">Desc..</textarea>'\n" +
                "        + '</div>'\n" +
                "        + '</fieldset>'\n" +
                "        + '</form>'\n" +
                "        + '</div>'\n" +
                "\n" +
                "        + '</div>';\n" +
                "\n" +
                "      document.body.innerHTML += editForm;\n" +
                "\n" +
                "      document.getElementById(\"v-enums\").addEventListener('click', function () {\n" +
                "        var newValue = document.getElementById(\"v-enums\").value;\n" +
                "        if (newValue != null && newValue != \"\") {\n" +
                "          document.getElementById(\"e-value\").value = newValue;\n" +
                "        }\n" +
                "      });\n" +
                "\n" +
                "      document.getElementById(\"updateBtn\").addEventListener('click', function () {\n" +
                "        var newValue = document.getElementById(\"e-value\").value;\n" +
                "        if (newValue != null && newValue != \"\") {\n" +
                "          updateProperty(entry.path, newValue);\n" +
                "          popupHide();\n" +
                "        }\n" +
                "      });\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    var jp = [{ \"path\": \"\", \"name\": \"root\", \"isNode\": true }, { \"path\": \"fish\", \"name\": \"fish\", \"isNode\": true }, { \"path\": \"fish.age\", \"enums\": [], \"default\": null, \"name\": \"age\", \"isNode\": false, \"hasValue\": true, \"value\": \"28\", \"enumOnly\": false }, { \"path\": \"fish.what\", \"enums\": [], \"default\": \"Fish\", \"name\": \"what\", \"isNode\": false, \"hasValue\": false, \"value\": null, \"enumOnly\": false }, { \"path\": \"fish.name\", \"enums\": [], \"default\": null, \"name\": \"name\", \"isNode\": false, \"hasValue\": true, \"value\": \"Jessica\", \"enumOnly\": false }, { \"path\": \"fish.enum\", \"enums\": [\"a\", \"b\", \"c\"], \"default\": null, \"name\": \"enum\", \"isNode\": false, \"hasValue\": true, \"value\": \"b\", \"enumOnly\": true }, { \"path\": \"fish.enum-d\", \"enums\": [\"4\", \"6\", \"8\"], \"default\": null, \"name\": \"enum-d\", \"isNode\": false, \"hasValue\": true, \"value\": \"4\", \"enumOnly\": true }, { \"path\": \"fish.should-i-swim?\", \"enums\": [], \"default\": \"true\", \"name\": \"should-i-swim?\", \"isNode\": false, \"hasValue\": false, \"value\": null, \"enumOnly\": false }];\n" +
                "\n" +
                "    var insertIntoTable = function (input) {\n" +
                "      if (typeof input === 'string' || input instanceof String) {\n" +
                "        var input = JSON.parse(input);\n" +
                "      }\n" +
                "\n" +
                "\n" +
                "      var table = document.getElementById(\"proptb\");\n" +
                "      console.log(\"Parsing input...\");\n" +
                "      var tableRowIdx = 0;\n" +
                "      for (var idx in input) {\n" +
                "        var entry = input[idx];\n" +
                "        if (entry.path == \"\") {\n" +
                "          continue;\n" +
                "        }\n" +
                "        var row = table.insertRow();\n" +
                "        row.id = entry.path;\n" +
                "        if (tableRowIdx % 2 == 0) {\n" +
                "          row.className = \"pure-table-odd\";\n" +
                "        }\n" +
                "        ++tableRowIdx;\n" +
                "        var c0 = row.insertCell(0);\n" +
                "        c0.innerHTML = entry.path;\n" +
                "        row.insertCell(1).innerHTML = entry.isNode\n" +
                "        var c2 = row.insertCell(2);\n" +
                "        if (entry.hasValue == true) {\n" +
                "          c2.innerHTML = entry.value;\n" +
                "        }\n" +
                "        var c3 = row.insertCell(3);\n" +
                "        if (entry.default !== undefined) {\n" +
                "          c3.innerHTML = entry.default;\n" +
                "        }\n" +
                "        var c4 = row.insertCell(4);\n" +
                "        if (entry.enumOnly == true) { c4.innerHTML = \"Only enums: \" }\n" +
                "        if (entry.enums !== undefined && entry.enums.length != 0) {\n" +
                "          c4.innerHTML += entry.enums;\n" +
                "        }\n" +
                "        var c5 = row.insertCell(5);\n" +
                "        allOptions[entry.path] = entry;\n" +
                "        c5.innerHTML = '<button id=\"btn_' + entry.path + '\" class=\"pure-button\" onclick=\\'editOption(\\\"' + entry.path + '\\\");\\'><i class=\"fa fa-cog\"></i>Settings</button>';\n" +
                "\n" +
                "        document.getElementById(\"btn_\" + entry.path).addEventListener(\"click\", function () {\n" +
                "          editOption(entry);\n" +
                "        });\n" +
                "\n" +
                "      }\n" +
                "\n" +
                "    };\n" +
                "\n" +
                "    function httpPost(theUrl, additionalData) {\n" +
                "      var xmlHttp = new XMLHttpRequest();\n" +
                "      xmlHttp.open(\"POST\", theUrl, true); // false for synchronous request\n" +
                "      xmlHttp.send(additionalData);\n" +
                "      return xmlHttp.responseText;\n" +
                "    }\n" +
                "\n" +
                "    function httpGet(theUrl) {\n" +
                "      var xmlHttp = new XMLHttpRequest();\n" +
                "      xmlHttp.open(\"GET\", theUrl, false); // false for synchronous request\n" +
                "      xmlHttp.send(null);\n" +
                "      return xmlHttp.responseText;\n" +
                "    }\n" +
                "\n" +
                "    var clearTable = function () {\n" +
                "      var table = document.getElementById(\"proptb\");\n" +
                "      for (var i = 1; i < table.rows.length;) {\n" +
                "        table.deleteRow(i);\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    var updateTable = function () {\n" +
                "      clearTable();\n" +
                "      //var content = jp; //httpGet(\"/r\");\n" +
                "      var content = httpGet(\"/r\");\n" +
                "      console.log(\"data received: \" + content);\n" +
                "      insertIntoTable(content);\n" +
                "    }\n" +
                "\n" +
                "//alert('hello!');\n" +
                "  </script>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
    }

}
