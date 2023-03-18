package com.telkom.capex.etc

object ChartDemo {

    fun getPie(): String = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
            "    <script type=\"text/javascript\">\n" +
            "      google.charts.load(\"current\", {packages:[\"corechart\"]});\n" +
            "      google.charts.setOnLoadCallback(drawChart);\n" +
            "      function drawChart() {\n" +
            "        var data = new google.visualization.DataTable();\n" +
            "        data.addColumn('string', 'Task');\n" +
            "        data.addColumn('number', '_');\n" +
            "        data.addColumn({type: 'string', role: 'tooltip'});\n" +
            "        data.addRows([\n" +
            "          ['Preparing \\n Rp. 10000100', 10000100,'Preparing'],\n" +
            "          ['Delivery  \\n Rp. 4202000', 4202000, 'Delivery'],\n" +
            "          ['MOS \\n Rp. 2202000', 2202000, 'MOS'],\n" +
            "          ['Test Comm \\n Rp. 2000000', 2000000, 'Test Comm'],\n" +
            "          ['UT \\n Rp. 2900000', 2900000, 'UT'],\n" +
            "          ['BAST \\n Rp. 7300000', 7300000, 'BAST']\n" +
            "        ]);\n" +
            "\n" +
            "        var options = {\n" +
            "           backgroundColor: 'transparent',\n" +
            "          pieHole: 0.4,\n" +
            "          legend: {'position':'right','alignment':'center'},\n" +
            "          chartArea: {'width': '100%', 'height': '100%', left: 20},\n" +
            "          colors: ['#307672', '#C28F3C', '#144D53', '#75592A', '#C26863', '#753936'],\n" +
            "        };\n" +
            "\n" +
            "        var chart = new google.visualization.PieChart(document.getElementById('chart'));\n" +
            "        chart.draw(data, options);\n" +
            "      }\n" +
            "    </script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"chart\" style=\"width: 300px; height: 200px;\"></div>\n" +
            "</body>\n" +
            "</html>\n"

    fun getColumn(data: List<Long>?): String = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
            "    <script type=\"text/javascript\">\n" +
            "    google.charts.load(\"current\", {packages:[\"corechart\"]});\n" +
            "    google.charts.setOnLoadCallback(drawChart);\n" +
            "    function drawChart() {\n" +
            "      var data= google.visualization.arrayToDataTable([\n" +
            "        [\"Month\", \"BAST\", { role: \"style\" } ],\n" +
            "        [\"Jan\", ${data?.get(0)}, \"#018786\"],\n" +
            "        [\"Feb\", ${data?.get(1)}, \"#144D53\"],\n" +
            "        [\"Mar\", ${data?.get(2)}, \"#1A3C40\"],\n" +
            "        [\"Apr\", ${data?.get(3)}, \"#144D53\"],\n" +
            "        [\"Mei\", ${data?.get(4)}, \"#1A3C40\"],\n" +
            "        [\"Jun\", ${data?.get(5)}, \"#018786\"],\n" +
            "        [\"Jul\", ${data?.get(6)}, \"#144D53\"],\n" +
            "        [\"Aug\", ${data?.get(7)}, \"#1A3C40\"],\n" +
            "        [\"Sep\", ${data?.get(8)}, \"#018786\"],\n" +
            "        [\"Oct\", ${data?.get(9)}, \"#144D53\"],\n" +
            "        [\"Nov\", ${data?.get(10)}, \"#1A3C40\"],\n" +
            "        [\"Des\", ${data?.get(11)}, \"#018786\"]\n" +
            "      ]);\n" +
            "\n" +
            "      var options = {\n" +
            "        chartArea: {'width': '100%', 'height': '100%'},\n" +
            "        legend: \"none\",\n" +
            "        bar: {\n" +
            "            groupWidth: \"80%\"\n" +
            "        },\n" +
            "        backgroundColor: 'transparent',\n" +
            "        vAxis: {\n" +
            "            gridlines: {\n" +
            "                color: 'transparent'\n" +
            "            }\n" +
            "        }\n" +
            "      };\n" +
            "      var chart = new google.visualization.ColumnChart(document.getElementById(\"chart\"));\n" +
            "      chart.draw(data, options);\n" +
            "      google.visualization.events.addListener(chart, 'click', function(e) {\n" +
            "          if (e.targetID.startsWith('bar#')) {\n" +
            "              var columnIndex = parseInt(e.targetID.substring(4));\n" +
            "               Android.onClick(columnIndex);\n" +
            "       }\n" +
            "      });\n" +
            "       \n" +
            "  // Add error handling\n" +
            "  google.visualization.events.addListener(chart, 'error', function(e) {\n" +
            "    console.error('Google Chart error: ' + e.message);\n" +
            "  });"+
            "\n" +
            "  google.visualization.events.addListener(chart, 'click', function(e) {\n" +
            "    if (e.targetID && e.targetID.startsWith('bar#')) {\n" +
            "      var columnIndex = parseInt(e.targetID.substring(6));\n" +
            "      Android.onClick(columnIndex);\n" +
            "\n" +
            "          console.log('Column index:', columnIndex);" +
            "    }\n" +
            "  });\n" +
            "\n" +
            "  // Add error handling\n" +
            "  google.visualization.events.addListener(chart, 'error', function(e) {\n" +
            "    console.error('Google Chart error: ' + e.message);\n" +
            "  });"+
            "  }\n" +
            "  </script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"chart\" style=\"height: 200px;\"></div>\n" +
            "</body>\n" +
            "</html>\n"
}