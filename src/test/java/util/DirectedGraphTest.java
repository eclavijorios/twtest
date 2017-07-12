package util;

import model.Village;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DirectedGraphTest {

    private DirectedGraph<Village> graph;
    private static Map<String, Village> villages;

    @BeforeClass

    public static void initTowns() {
        villages = new HashMap<String, Village>();

        villages.put("A", new Village("A"));
        villages.put("B", new Village("B"));
        villages.put("C", new Village("C"));
        villages.put("D", new Village("D"));
        villages.put("E", new Village("E"));
    }

    @Before
    public void initObjects() {
        graph = new DirectedGraph<>();

        Village villageA = villages.get("A");
        Village villageB = villages.get("B");
        Village villageC = villages.get("C");
        Village villageD = villages.get("D");
        Village villageE = villages.get("E");

        graph.addNode(villageA);
        graph.addNode(villageB);
        graph.addNode(villageC);
        graph.addNode(villageD);
        graph.addNode(villageE);

        graph.addEdge(villageA, villageB, 5);
        graph.addEdge(villageB, villageC, 4);
        graph.addEdge(villageC, villageD, 8);
        graph.addEdge(villageD, villageC, 8);
        graph.addEdge(villageD, villageE, 6);
        graph.addEdge(villageA, villageD, 5);
        graph.addEdge(villageC, villageE, 2);
        graph.addEdge(villageE, villageB, 3);
        graph.addEdge(villageA, villageE, 7);
    }


    @Test
    public void testAddNode1() {
        DirectedGraph<Village> g = new DirectedGraph<>();
        Village villageA = new Village("A");
        g.addNode(villageA);

        assertTrue(g.contains(villageA));
    }

    @Test
    public void testAddEdge1() {
        DirectedGraph<Village> g = new DirectedGraph<>();
        Village villageA = new Village("A");
        g.addNode(villageA);

        Village villageB = new Village("B");
        g.addNode(villageB);

        g.addEdge(villageA, villageB, 5);
        assertTrue(g.edgeExists(villageA, villageB));
    }

    @Test
    public void testGetNeighBours1() {
        DirectedGraph<Village> g = new DirectedGraph<>();
        Village villageA = new Village("A");
        g.addNode(villageA);

        Village villageB = new Village("B");
        g.addNode(villageB);

        Village villageC = new Village("C");
        g.addNode(villageC);

        g.addEdge(villageA, villageB, 5);
        g.addEdge(villageA, villageC, 2);

        Set<Village> neighbours = g.getNeighbours(villageA);
        assertEquals(2, neighbours.size());
    }

    @Test
    public void testGetNeighBours2() {
        DirectedGraph<Village> g = new DirectedGraph<>();
        Village villageA = new Village("A");
        g.addNode(villageA);

        Village villageB = new Village("B");
        g.addNode(villageB);

        g.addEdge(villageA, villageB, 5);

        Set<Village> neighbours = g.getNeighbours(villageA);
        assertTrue(neighbours.contains(villageB));
    }

    @Test
    public void testShortestPathBetween1() throws DirectedGraph.NodeNotReachableException {
        DirectedGraph.GraphPath path;

        path = graph.shortestPathBetween(villages.get("A"), villages.get("B"));
        assertEquals("A->B", path.toString());

        path = graph.shortestPathBetween(villages.get("A"), villages.get("D"));
        assertEquals("A->D", path.toString());

        path = graph.shortestPathBetween(villages.get("A"), villages.get("E"));
        assertEquals("A->E", path.toString());

        path = graph.shortestPathBetween(villages.get("D"), villages.get("E"));
        assertEquals("D->E", path.toString());

        path = graph.shortestPathBetween(villages.get("E"), villages.get("B"));
        assertEquals("E->B", path.toString());

        path = graph.shortestPathBetween(villages.get("B"), villages.get("C"));
        assertEquals("B->C", path.toString());

        path = graph.shortestPathBetween(villages.get("C"), villages.get("E"));
        assertEquals("C->E", path.toString());

        path = graph.shortestPathBetween(villages.get("C"), villages.get("D"));
        assertEquals("C->D", path.toString());

        path = graph.shortestPathBetween(villages.get("D"), villages.get("C"));
        assertEquals("D->C", path.toString());

    }

    @Test
    public void testShortestPathBetween2() throws DirectedGraph.NodeNotReachableException {
        DirectedGraph.GraphPath path;

        path = graph.shortestPathBetween(villages.get("B"), villages.get("E"));
        assertEquals("B->C->E", path.toString());
        assertEquals(6, path.distance());
        assertEquals(2, path.jumpCount());

        path = graph.shortestPathBetween(villages.get("E"), villages.get("C"));
        assertEquals("E->B->C", path.toString());
        assertEquals(7, path.distance());
        assertEquals(2, path.jumpCount());

        path = graph.shortestPathBetween(villages.get("D"), villages.get("B"));
        assertEquals("D->E->B", path.toString());
        assertEquals(9, path.distance());
        assertEquals(2, path.jumpCount());
    }

    @Test(expected = DirectedGraph.NodeNotReachableException.class)
    public void testShortestPathBetween3() throws DirectedGraph.NodeNotReachableException {
        graph.shortestPathBetween(villages.get("E"), villages.get("A"));
    }

    @Test(expected = DirectedGraph.NodeNotReachableException.class)
    public void testShortestPathBetween4() throws DirectedGraph.NodeNotReachableException {
        graph.shortestPathBetween(villages.get("C"), villages.get("A"));
    }


    @Test
    public void testDistance1() throws DirectedGraph.NodeNotReachableException {
        List<Village> route = new ArrayList<Village>();
        route.add(villages.get("A"));
        route.add(villages.get("B"));
        route.add(villages.get("C"));

        int ans = graph.distance(route);
        assertEquals(9, ans);
    }

    @Test
    public void testDistance2() throws DirectedGraph.NodeNotReachableException {
        List<Village> route = new ArrayList<Village>();
        route.add(villages.get("A"));
        route.add(villages.get("D"));
        route.add(villages.get("E"));

        int ans = graph.distance(route);
        assertEquals(11, ans);
    }

    @Test
    public void testDistance3() throws DirectedGraph.NodeNotReachableException {
        List<Village> route = new ArrayList<Village>();
        route.add(villages.get("B"));
        route.add(villages.get("C"));
        route.add(villages.get("D"));
        route.add(villages.get("E"));
        route.add(villages.get("B"));

        int ans = graph.distance(route);
        assertEquals(21, ans);
    }

    @Test
    public void testDistance4() throws DirectedGraph.NodeNotReachableException {
        List<Village> route = new ArrayList<Village>();
        route.add(villages.get("B"));
        route.add(villages.get("C"));
        route.add(villages.get("D"));
        route.add(villages.get("E"));
        route.add(villages.get("B"));
        route.add(villages.get("C"));
        route.add(villages.get("E"));
        route.add(villages.get("B"));

        int ans = graph.distance(route);
        assertEquals(30, ans);
    }

    @Test(expected = DirectedGraph.NodeNotReachableException.class)
    public void testDistanceException1() throws DirectedGraph.NodeNotReachableException {
        List<Village> route = new ArrayList<Village>();
        route.add(villages.get("B"));
        route.add(villages.get("B"));

        int ans = graph.distance(route);
    }


    @Test
    public void testCountRoutesWithMaxHops1() {
        int ans = graph.countRoutesWithMaxHops(villages.get("C"), villages.get("C"), 3);
        assertEquals(2, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops2() {
        int ans = graph.countRoutesWithMaxHops(villages.get("A"), villages.get("B"), 3);
        assertEquals(3, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops3() {
        int ans = graph.countRoutesWithMaxHops(villages.get("B"), villages.get("C"), 3);
        assertEquals(2, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops4() {
        int ans = graph.countRoutesWithMaxHops(villages.get("B"), villages.get("C"), 2);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxHops5() {
        int ans = graph.countRoutesWithMaxHops(villages.get("B"), villages.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithMaxHopsException() {
        graph.countRoutesWithMaxHops(villages.get("B"), villages.get("X"), 1);
    }

    @Test
    public void testCountRoutesWithHops1() {
        int ans = graph.countRoutesWithHops(villages.get("B"), villages.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test
    public void testCountRoutesWithHops2() {
        int ans = graph.countRoutesWithHops(villages.get("B"), villages.get("D"), 2);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithHops3() {
        int ans = graph.countRoutesWithHops(villages.get("B"), villages.get("C"), 1);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithHops4() {
        int ans = graph.countRoutesWithHops(villages.get("A"), villages.get("C"), 1);
        assertEquals(0, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithHopsException() {
        graph.countRoutesWithHops(villages.get("B"), villages.get("X"), 1);
    }

    @Test
    public void testCountRoutesWithMaxDistance1() {
        int ans = graph.countRoutesWithMaxDistance(villages.get("B"), villages.get("D"), 1);
        assertEquals(0, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance2() {
        int ans = graph.countRoutesWithMaxDistance(villages.get("B"), villages.get("E"), 6);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance3() {
        int ans = graph.countRoutesWithMaxDistance(villages.get("B"), villages.get("E"), 18);
        assertEquals(3, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance4() {
        int ans = graph.countRoutesWithMaxDistance(villages.get("A"), villages.get("E"), 7);
        assertEquals(1, ans);
    }

    @Test
    public void testCountRoutesWithMaxDistance5() {
        int ans = graph.countRoutesWithMaxDistance(villages.get("C"), villages.get("D"), 32);
        assertEquals(5, ans);
    }

    @Test(expected = NoSuchElementException.class)
    public void testCountRoutesWithMaxDistanceExcpetion() {
        graph.countRoutesWithMaxDistance(villages.get("C"), villages.get("X"), 2);
    }
}
