package com.telkom.capex.etc

object ChartDemo {

    fun getPie(sum: List<Long>?): String {
        val div = mutableListOf("Preparing", "Delivery", "MOS", "Test Comm", "UT", "BAST")
        val map = mutableMapOf<String, Long>()
        div.forEachIndexed { index, item ->
            map[item] = sum?.get(index) ?: 0
        }

        var html = ""
        map.forEach {
            val format = String.format("%,d", it.value)
            val substringed = format.substring(0,5)
            val tag =
                when {
                    it.value >= 1_000_000_000_000L -> " T"
                    it.value >= 1_000_000_000L -> " B"
                    it.value >= 1_000_000L -> " M"
                    it.value >= 1_000L -> " K"
                    else -> ""
                }
            val displayed = "$substringed$tag"
            if (html != "") html += ",\n"
            html += "['${it.key}\\nRp.$displayed', ${it.value},'${it.key}\\nRp.${format}']"
        }
        return "<!DOCTYPE html>\n" +
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
                "        data.addRows([\n" + html +
                "        ]);\n" +
                "\n" +
                "        var options = {\n" +
                "          backgroundColor: 'transparent',\n" +
                "          pieHole: 0.4,\n" +
                "          tooltip: { trigger: 'none'},\n" +
                "          legend: { position: 'right', alignment: 'center' },\n" +
                "          chartArea: { width: '100%', height: '100%', left: 20 },\n" +
                "          colors: ['#307672', '#C28F3C', '#144D53', '#75592A', '#C26863', '#753936']\n" +
                "        };\n" +
                "\n" +
                "        var chart = new google.visualization.PieChart(document.getElementById('chart'));\n" +
                "\n" +
                "        google.visualization.events.addListener(chart, 'select', function () {\n" +
                "          var selectedItem = chart.getSelection()[0];\n" +
                "          if (selectedItem) {\n" +
                "            var tooltip = data.getValue(selectedItem.row, 2);\n" +
                "            var value = data.getValue(selectedItem.row, 1);\n" +
                "            var total = getTotalValue(data);\n" +
                "            console.log(total);\n" +
                "\n" +
                "            var percentage = (value / total) * 100;\n" +
                "            Android.showDialog(tooltip, percentage.toFixed(2));" +
                "          }\n" +
                "        });\n" +
                "\n" +
                "        // Helper function to calculate the total value of all slices\n" +
                "        function getTotalValue(data) {\n" +
                "           var total = 0;\n" +
                "           for (var i = 0; i < data.getNumberOfRows(); i++) {\n" +
                "               total += data.getValue(i, 1);\n" +
                "           }\n" +
                "           return total;\n" +
                "        }" +
                "        chart.draw(data, options);\n" +
                "      }\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"chart\" style=\"width: 300px; height: 200px;\"></div>\n" +
                "</body>\n" +
                "</html>\n"
    }

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