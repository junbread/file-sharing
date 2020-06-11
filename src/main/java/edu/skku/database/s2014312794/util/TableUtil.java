package edu.skku.database.s2014312794.util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.ArrayList;
import java.util.List;

public class TableUtil {

    public static AsciiTable createIndexedTable(List<String> header, List<List<?>> contents) {
        AsciiTable table = new AsciiTable();
        table.getRenderer().setCWC(new CWC_LongestLine());

        table.addRule();

        List<String> indexedHeader = new ArrayList<>();
        indexedHeader.add("No.");
        indexedHeader.addAll(header);

        table.addRow(indexedHeader);

        for (int i = 0; i < contents.size(); i++) {
            List<Object> indexedContent = new ArrayList<>();
            indexedContent.add(i + 1);
            indexedContent.addAll(contents.get(i));

            table.addRow(indexedContent);
            table.addRule();
        }

        return table;
    }

    public static AsciiTable createTable(List<String> header, List<List<?>> contents) {
        AsciiTable table = new AsciiTable();
        table.getRenderer().setCWC(new CWC_LongestLine());

        table.addRule();
        table.addRow(header);

        for (int i = 0; i < contents.size(); i++) {
            table.addRow(contents.get(i));
            table.addRule();
        }

        return table;
    }
}
