import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PatienceDiff {

    public static class LineInfo {
        public String line;
        public int aIndex;
        public int bIndex;
        public boolean moved;

        public LineInfo(String line, int aIndex, int bIndex) {
            this.line = line;
            this.aIndex = aIndex;
            this.bIndex = bIndex;
            this.moved = false;
        }
    }

    private static Map<String, Integer> findUnique(String[] arr, int lo, int hi) {
        Map<String, Integer> lineMap = new HashMap<>();

        for (int i = lo; i <= hi; i++) {
            String line = arr[i];
            lineMap.put(line, lineMap.getOrDefault(line, 0) + 1);
        }

        lineMap.entrySet().removeIf(entry -> entry.getValue() != 1);
        return lineMap;
    }

    private static Map<String, Integer> uniqueCommon(String[] aArray, int aLo, int aHi, String[] bArray, int bLo, int bHi) {
        Map<String, Integer> ma = findUnique(aArray, aLo, aHi);
        Map<String, Integer> mb = findUnique(bArray, bLo, bHi);

        ma.keySet().retainAll(mb.keySet());
        return ma;
    }

    private static List<LineInfo> longestCommonSubsequence(Map<String, Integer> abMap, List<LineInfo> aMove, List<LineInfo> bMove) {
        List<List<LineInfo>> ja = new ArrayList<>();

        abMap.forEach((key, val) -> {
            int i = 0;
            while (i < ja.size() && ja.get(i).get(ja.get(i).size() - 1).bIndex < val) {
                i++;
            }

            if (i == ja.size()) {
                ja.add(new ArrayList<>());
            }

            LineInfo lineInfo = new LineInfo(key, -1, val);
            if (i > 0) {
                lineInfo.aIndex = ja.get(i - 1).get(ja.get(i - 1).size() - 1).aIndex;
                lineInfo.bIndex = ja.get(i - 1).get(ja.get(i - 1).size() - 1).bIndex;
            }
            ja.get(i).add(lineInfo);
        });

        List<LineInfo> lcs = new ArrayList<>();
        if (!ja.isEmpty()) {
            int n = ja.size() - 1;
            lcs.add(ja.get(n).get(ja.get(n).size() - 1));
            while (lcs.get(lcs.size() - 1).aIndex >= 0) {
                int i = lcs.get(lcs.size() - 1).aIndex;
                int j = lcs.get(lcs.size() - 1).bIndex;
                lcs.add(aMove.stream().filter(info -> info.aIndex == i).findFirst().orElse(null));
            }
        }

        return lcs.stream().filter(lineInfo -> lineInfo != null).collect(Collectors.toList());
    }

    private static void addSubMatch(
            int aLo, int aHi, int bLo, int bHi, String[] aLines, String[] bLines,
            List<LineInfo> result, int[] deleted, int[] inserted) {
        while (aLo <= aHi && bLo <= bHi && aLines[aLo].equals(bLines[bLo])) {
            addToResult(aLo++, bLo++, aLines, bLines, result, deleted, inserted);
        }

        int aHiTemp = aHi;
        while (aLo <= aHi && bLo <= bHi && aLines[aHi].equals(bLines[bHi])) {
            aHi--;
            bHi--;
        }

        Map<String, Integer> uniqueCommonMap = uniqueCommon(aLines, aLo, aHi, bLines, bLo, bHi);
        if (uniqueCommonMap.isEmpty()) {
            while (aLo <= aHi) {
                addToResult(aLo++, -1, aLines, bLines, result, deleted, inserted);
            }

            while (bLo <= bHi) {
                addToResult(-1, bLo++, aLines, bLines, result, deleted, inserted);
            }
        } else {
            recurseLCS(aLo, aHi, bLo, bHi, aLines, bLines, result, deleted, inserted, uniqueCommonMap);
        }

        while (aHi < aHiTemp) {
            addToResult(++aHi, ++bHi, aLines, bLines, result, deleted, inserted);
        }
    }

    private static void recurseLCS(
            int aLo, int aHi, int bLo, int bHi, String[] aLines, String[] bLines,
            List<LineInfo> result, int[] deleted, int[] inserted, Map<String, Integer> uniqueCommonMap) {
        List<LineInfo> x = longestCommonSubsequence(uniqueCommonMap, null, null);

        if (x.isEmpty()) {
            addSubMatch(aLo, aHi, bLo, bHi, aLines, bLines, result, deleted, inserted);
        } else {
            if (aLo < x.get(0).aIndex || bLo < x.get(0).bIndex) {
                addSubMatch(aLo, x.get(0).aIndex - 1, bLo, x.get(0).bIndex - 1, aLines, bLines, result, deleted, inserted);
            }

            for (int i = 0; i < x.size() - 1; i++) {
                addSubMatch(
                        x.get(i).aIndex, x.get(i + 1).aIndex - 1, x.get(i).bIndex, x.get(i + 1).bIndex - 1,
                        aLines, bLines, result, deleted, inserted);
            }

            if (x.get(x.size() - 1).aIndex <= aHi || x.get(x.size() - 1).bIndex <= bHi) {
                addSubMatch(x.get(x.size() - 1).aIndex, aHi, x.get(x.size() - 1).bIndex, bHi, aLines, bLines, result, deleted, inserted);
            }
        }
    }

    private static void addToResult(
            int aIndex, int bIndex, String[] aLines, String[] bLines,
            List<LineInfo> result, int[] deleted, int[] inserted) {
        if (bIndex < 0) {
            deleted[0]++;
        } else if (aIndex < 0) {
            inserted[0]++;
        }

        LineInfo lineInfo;
        if (aIndex >= 0) {
            lineInfo = new LineInfo(aLines[aIndex], aIndex, bIndex);
        } else {
            lineInfo = new LineInfo(bLines[bIndex], aIndex, bIndex);
        }
        result.add(lineInfo);
    }

    public static List<LineInfo> patienceDiff(String[] aLines, String[] bLines, boolean diffPlusFlag) {
        List<LineInfo> result = new ArrayList<>();
        int[] deleted = {0};
        int[] inserted = {0};

        int[] aMove = new int[0];
        int[] aMoveIndex = new int[0];
        int[] bMove = new int[0];
        int[] bMoveIndex = new int[0];

        for (int i = 0; i < aLines.length; i++) {
            for (int j = 0; j < bLines.length; j++) {
                if (aLines[i].equals(bLines[j])) {
                    addToResult(i, j, aLines, bLines, result, deleted, inserted);
                }
            }
        }

        for (int i = 0; i < aLines.length; i++) {
            if (result.stream().noneMatch(lineInfo -> lineInfo.aIndex == i)) {
                aMove = resizeArray(aMove, aMove.length + 1);
                aMove[aMove.length - 1] = i;
                aMoveIndex = resizeArray(aMoveIndex, aMoveIndex.length + 1);
                aMoveIndex[aMoveIndex.length - 1] = result.size();
            }
        }

        for (int i = 0; i < bLines.length; i++) {
            if (result.stream().noneMatch(lineInfo -> lineInfo.bIndex == i)) {
                bMove = resizeArray(bMove, bMove.length + 1);
                bMove[bMove.length - 1] = i;
                bMoveIndex = resizeArray(bMoveIndex, bMoveIndex.length + 1);
                bMoveIndex[bMoveIndex.length - 1] = result.size();
            }
        }

        if (diffPlusFlag) {
            List<LineInfo> aMoveLines = new ArrayList<>();
            for (int index : aMove) {
                aMoveLines.add(new LineInfo(aLines[index], index, -1));
            }

            List<LineInfo> bMoveLines = new ArrayList<>();
            for (int index : bMove) {
                bMoveLines.add(new LineInfo(bLines[index], -1, index));
            }

            recurseLCS(0, aMove.length - 1, 0, bMove.length - 1, aLines, bLines, result, deleted, inserted, uniqueCommon(aLines, 0, aLines.length - 1, bLines, 0, bLines.length - 1));

            return result;
        } else {
            return result;
        }
    }

    private static int[] resizeArray(int[] arr, int newSize) {
        int[] newArr = new int[newSize];
        System.arraycopy(arr, 0, newArr, 0, Math.min(arr.length, newSize));
        return newArr;
    }

    public static List<LineInfo> patienceDiffPlus(String[] aLines, String[] bLines) {
        var difference = patienceDiff(aLines, bLines, true);

        List<LineInfo> aMoveNext = new ArrayList<>();
        List<Integer> aMoveIndexNext = new ArrayList<>();
        List<LineInfo> bMoveNext = new ArrayList<>();
        List<Integer> bMoveIndexNext = new ArrayList<>();

        for (int i = 0; i < difference.aMove.length; i++) {
            aMoveNext.add(new LineInfo(aLines[difference.aMove[i]], difference.aMove[i], -1));
            aMoveIndexNext.add(difference.aMoveIndex[i]);
        }

        for (int i = 0; i < difference.bMove.length; i++) {
            bMoveNext.add(new LineInfo(bLines[difference.bMove[i]], -1, difference.bMove[i]));
            bMoveIndexNext.add(difference.bMoveIndex[i]);
        }

        difference.aMove = null;
        difference.aMoveIndex = null;
        difference.bMove = null;
        difference.bMoveIndex = null;

        int lastLineCountMoved;

        do {
            List<LineInfo> aMove = aMoveNext;
            List<Integer> aMoveIndex = aMoveIndexNext;
            List<LineInfo> bMove = bMoveNext;
            List<Integer> bMoveIndex = bMoveIndexNext;

            aMoveNext = new ArrayList<>();
            aMoveIndexNext = new ArrayList<>();
            bMoveNext = new ArrayList<>();
            bMoveIndexNext = new ArrayList<>();

            PatienceDiffResult subDiff = patienceDiff(
                    aMove.stream().map(lineInfo -> lineInfo.line).toArray(String[]::new),
                    bMove.stream().map(lineInfo -> lineInfo.line).toArray(String[]::new),
                    false
            );

            lastLineCountMoved = difference.lineCountMoved;

            for (LineInfo v : subDiff.lines) {
                if (0 <= v.aIndex && 0 <= v.bIndex) {
                    difference.lines.get(aMoveIndex.get(v.aIndex)).moved = true;
                    difference.lines.get(bMoveIndex.get(v.bIndex)).aIndex = aMoveIndex.get(v.aIndex);
                    difference.lines.get(bMoveIndex.get(v.bIndex)).moved = true;
                    difference.lineCountInserted--;
                    difference.lineCountDeleted--;
                    difference.lineCountMoved++;
                } else if (v.bIndex < 0) {
                    aMoveNext.add(aMove.get(v.aIndex));
                    aMoveIndexNext.add(aMoveIndex.get(v.aIndex));
                } else {
                    bMoveNext.add(bMove.get(v.bIndex));
                    bMoveIndexNext.add(bMoveIndex.get(v.bIndex));
                }
            }
        } while (0 < difference.lineCountMoved - lastLineCountMoved);

        return difference.lines;
    }

    public static void main(String[] args) {
        String[] aLines = {
                "Attracted by new experiences and knowledge.",
                "Open to new opportunities in the field of Information Technology.",
                "Grateful for your attention."
        };

        String[] bLines = {
                "Attracted by new experiences and knowledge.",
                "Open to new opportunities in the field of Computer Science.",
                "Grateful for your attention."
        };

        List<LineInfo> differences = patienceDiff(aLines, bLines, true);
        System.out.println("Differences:");
        for (LineInfo diff : differences) {
            System.out.println("Line: " + diff.line + ", aIndex: " + diff.aIndex + ", bIndex: " + diff.bIndex +
                    ", Moved: " + diff.moved);
        }
    }
}
