package ru.qnocks.rockpaperscissors;

import com.inamik.text.tables.GridTable;
import com.inamik.text.tables.SimpleTable;
import com.inamik.text.tables.grid.Border;
import com.inamik.text.tables.grid.Util;

import java.util.Map;
import java.util.function.BinaryOperator;

public class RpsAsciiTable {

    private final Map<Integer, String> values;

    private final BinaryOperator<Integer> algorithm;

    public RpsAsciiTable(Map<Integer, String> values, BinaryOperator<Integer> algorithm) {
        this.values = values;
        this.algorithm = algorithm;
    }

    void render() {
        SimpleTable table = SimpleTable.of()
                .nextRow()
                .nextCell()
                .addLine("PC \\ USER");

        // header
        for (int i = 0; i < values.size(); i++) {
            table.nextCell().addLine(values.get(i));
        }

        // logic
        for (int i = 0; i < values.size(); i++) {
            table.nextRow().nextCell(values.get(i));

            for (int j = 0; j < values.size(); j++) {
                if (values.get(i).equals(values.get(j))) {
                    table.nextCell().addLine("DRAW");
                } else if (algorithm.apply(j, i) > 0) {
                    table.nextCell().addLine("WIN");
                } else {
                    table.nextCell().addLine("LOSE");
                }
            }
        }

        GridTable gridTable = addBorder(table);
        print(gridTable);
    }

    private GridTable addBorder(SimpleTable table) {
        GridTable gridTable = table.toGrid();
        gridTable = Border.of(Border.Chars.of('+', '-', '|')).apply(gridTable);
        return gridTable;
    }

    private void print(GridTable gridTable) {
        Util.print(gridTable);
    }
}