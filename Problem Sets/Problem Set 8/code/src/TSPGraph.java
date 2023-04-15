import java.util.HashSet;
import java.util.ArrayList;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        TreeMapPriorityQueue<Double, Integer> priorityQueue = new TreeMapPriorityQueue<>();
        priorityQueue.add(0, 0.0);
        for (int i = 1; i < map.getCount(); i++) {
            priorityQueue.add(i, Double.POSITIVE_INFINITY);
        }
        HashSet<Integer> set = new HashSet<>();

        while (!priorityQueue.isEmpty()) {
            int currKey = priorityQueue.extractMin();
            set.add(currKey);
            for (int key = 0; key < map.getCount(); key++) {
                double weight = map.pointDistance(key, currKey);
                if (!(key == currKey || set.contains(key)) && weight < priorityQueue.lookup(key)) {
                    priorityQueue.decreasePriority(key, weight);
                    map.setLink(key, currKey, false);
                }
            }
        }
        map.redraw();

    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(0);

        class DFS {
            public void dfs(TSPMap map, int index) {
                for (int i = 0; i < map.getCount(); i++) {
                    if (map.getLink(i) == index) {
                        arr.add(i);
                        dfs(map, i);
                    }
                }
            }
        }

        for (int i = 0; i < map.getCount() - 1; i++) {
            if (map.getLink(i) == 0) {
                arr.add(i);
                new DFS().dfs(map, i);
            }
        }

        for (int i = 0; i < arr.size() - 1; i++) {
            map.setLink(arr.get(i), arr.get(i + 1), false);
        }
        map.setLink(arr.get(arr.size() - 1), 0, false);
        map.redraw();
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (map != null) {
            if (map.getCount() == 1) {
                return true;
            }
            int currPoint = 0;
            boolean validTour = true;
            boolean[] visited = new boolean[map.getCount()];
            for (int i = 0; i < map.getCount(); i++) {
                currPoint = map.getPoint(currPoint).getLink();
                if (currPoint == -1 || visited[currPoint]) {
                    validTour = false;
                    break;
                }
                visited[currPoint] = true;
            }
            return validTour && currPoint == 0;
        } else {
            return false;
        }
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (isValidTour(map)) {
            double distance = 0;
            int currPoint = 0;
            int nextPoint;
            for (int i = 0; i < map.getCount(); i++) {
                nextPoint = map.getPoint(currPoint).getLink();
                distance = distance + map.pointDistance(currPoint, nextPoint);
                currPoint = nextPoint;
            }
            return distance;
        }
        return -1;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
        // graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        // System.out.println(graph.tourDistance(map));
    }
}
