package util;

import java.util.*;


public class DirectedGraph<T> {

    public static class NodeNotReachableException extends Exception {
        public NodeNotReachableException(String message) {
            super(message);
        }
    }


    private interface DFSCondition {

        boolean evaluate(DirectedGraph.GraphPath path);
    }


    private class GraphNode implements Comparable<GraphNode> {
        private T data;
        private Integer distance;
        private GraphNode previous;
        protected Map<T, Integer> neighbours;

        public GraphNode(T data) {
            this.data = data;
            this.neighbours = new HashMap<T, Integer>();
        }

        public T getData() {
            return data;
        }

        public Map<T, Integer> getNeighbours() {
            return neighbours;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public GraphNode getPrevious() {
            return previous;
        }

        public void setPrevious(GraphNode previous) {
            this.previous = previous;
        }

        public GraphPath path(GraphNode startNode) {
            List<T> nodes = new LinkedList<T>();
            int distance = 0;
            GraphNode currentNode = this;

            if (currentNode.getPrevious() == null) {
                return new GraphPath(nodes, distance);
            } else {
                nodes.add(currentNode.getData());

                while (!currentNode.equals(currentNode.getPrevious())) {
                    distance += weightForEdge(currentNode.getPrevious().getData(), currentNode.getData());

                    currentNode = currentNode.getPrevious();
                    nodes.add(0, currentNode.getData());

                    if (currentNode.equals(startNode)) {
                        break;
                    }
                }

                return new GraphPath(nodes, distance);
            }
        }

        public int compareTo(GraphNode other) {
            return distance.compareTo(other.getDistance());
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }


    public class GraphPath {
        private final String PATH_SEPARATOR = "->";
        private List<T> nodes;
        private int distance;


        public GraphPath() {
            this.nodes = new ArrayList<T>();
            this.distance = 0;
        }


        public GraphPath(List<T> nodes, int length) {
            this.nodes = nodes;
            this.distance = length;
        }


        public boolean removeLastNode(int weight) {
            if (!this.nodes.isEmpty()) {
                distance -= weight;
                this.nodes.remove(this.nodes.size() - 1);
                return true;
            } else {
                return false;
            }
        }


        public boolean appendNode(T node, int weight) {
            distance += weight;
            return this.nodes.add(node);
        }


        public List<T> getNodes() {
            return nodes;
        }


        public T last() {
            if (nodes.isEmpty()) {
                return null;
            } else {
                return nodes.get(nodes.size() - 1);
            }
        }


        public int distance() {
            return distance;
        }


        public int hopCount() {
            return Math.max(0, this.nodes.size() - 1);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodes.size() - 1; i++) {
                sb.append(nodes.get(i) + PATH_SEPARATOR);
            }
            sb.append(nodes.get(nodes.size() - 1));
            return sb.toString();
        }
    }


    private Map<T, GraphNode> graph;

    public DirectedGraph() {
        graph = new HashMap<T, GraphNode>();
    }


    public void addNode(T node) {
        GraphNode graphNode = new GraphNode(node);
        if (!graph.containsKey(node)) {
            graph.put(node, graphNode);
        }
    }


    public boolean contains(T node) {
        return graph.containsKey(node);
    }


    public void addEdge(T start, T destination, int weight) {
        validateInputNodes(start, destination);

        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be >= 0");
        }

        graph.get(start).getNeighbours().put(destination, weight);
    }


    public void removeEdge(T start, T destination) {
        validateInputNodes(start, destination);

        graph.get(start).getNeighbours().remove(destination);
    }


    public Set<T> getNeighbours(T node) {
        validateInputNodes(node);

        return graph.get(node).getNeighbours().keySet();
    }


    public boolean edgeExists(T start, T destination) {
        validateInputNodes(start, destination);

        return graph.get(start).getNeighbours().containsKey(destination);
    }


    private int weightForEdge(T start, T destination) {
        return graph.get(start).getNeighbours().get(destination);
    }


    public Set<T> getNodes() {
        return graph.keySet();
    }



    private void dijkstra(T start, T destination) {
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();


        Set<T> nodes = getNodes();
        for (T node : nodes) {
            GraphNode graphNode = graph.get(node);
            graphNode.setDistance(Integer.MAX_VALUE);
            if (start.equals(node)) {
                graphNode.setDistance(0);
                graphNode.setPrevious(graphNode);
            }

            queue.add(graphNode);
        }

        while (!queue.isEmpty()) {
            GraphNode currentGraphNode = queue.poll();

            if (currentGraphNode.getData().equals(destination) && !currentGraphNode.equals(currentGraphNode.getPrevious())) {
                break;
            }

            for (T neighbour : getNeighbours(currentGraphNode.getData())) {
                GraphNode neighbourGraphNode = graph.get(neighbour);

                final int alternateDist = currentGraphNode.getDistance() + weightForEdge(currentGraphNode.getData(), neighbour);
                if (alternateDist < neighbourGraphNode.getDistance() || neighbourGraphNode.equals(neighbourGraphNode.getPrevious())) { // shorter path to neighbour found
                    queue.remove(neighbourGraphNode);
                    neighbourGraphNode.setDistance(alternateDist);
                    neighbourGraphNode.setPrevious(currentGraphNode);
                    queue.add(neighbourGraphNode);
                }
            }
        }
    }



    private int countPaths(T start, DFSCondition stop, DFSCondition filter) {
        int total = 0;

        for (T neighbour : getNeighbours(start)) {
            GraphPath path = new GraphPath();
            path.appendNode(start, 0);
            path.appendNode(neighbour, weightForEdge(start, neighbour));
            total += dfs(neighbour, stop, filter, path);
        }

        return total;
    }


    private int dfs(T currentNode, DFSCondition stop, DFSCondition filter, GraphPath path) {
        int total = 0;

        if (filter.evaluate(path)) {
            // Count path as valid
            total++;
        }

        for (T neighbour : getNeighbours(currentNode)) {

            path.appendNode(neighbour, weightForEdge(currentNode, neighbour));

            if (stop.evaluate(path)) {
                path.removeLastNode(weightForEdge(currentNode, neighbour));
                continue;
            } else {
                total += dfs(neighbour, stop, filter, path);
            }

            path.removeLastNode(weightForEdge(currentNode, neighbour));
        }
        return total;
    }


    private void validateInputNodes(T... nodes) {
        for (T node : nodes) {
            if (!contains(node)) {
                throw new NoSuchElementException("No such element");
            }
        }
    }


    public GraphPath shortestPathBetween(T start, T destination) throws NodeNotReachableException {
        validateInputNodes(start, destination);

        dijkstra(start, destination);

        GraphNode destinationNode = graph.get(destination);
        GraphPath path = destinationNode.path(graph.get(start));

        if (path.getNodes().isEmpty()) {
            throw new NodeNotReachableException("Node not Reachable");
        } else {
            return path;
        }
    }


    public int distance(List<T> nodes) throws NodeNotReachableException {
        validateInputNodes((T[]) nodes.toArray());

        int distance = 0;
        for (int i = 0; i < nodes.size() - 1; i++) {
            T start = nodes.get(i);
            T destination = nodes.get(i + 1);

            if (edgeExists(start, destination)) {
                distance += weightForEdge(start, destination);
            } else {
                throw new NodeNotReachableException("invalid edge");
            }

        }

        return distance;
    }


    public int countRoutesWithMaxHops(T start, T destination, int maxHops) {
        validateInputNodes(start, destination);

        return countPaths(start, (p) -> {
            return p.hopCount() > maxHops;
        }, (p) -> {
            return destination.equals(p.last());
        });
    }


    public int countRoutesWithHops(T start, T destination, int hops) {
        validateInputNodes(start, destination);

        return countPaths(start, (p) -> {
            return p.hopCount() > hops;
        }, (p) -> {
            return destination.equals(p.last()) && p.hopCount() == hops;
        });
    }

    public int countRoutesWithMaxDistance(T start, T destination, int distance) {
        validateInputNodes(start, destination);

        return countPaths(start, (p) -> {
            return p.distance() > distance;
        }, (p) -> {
            return destination.equals(p.last()) && p.distance() <= distance;
        });
    }

}
